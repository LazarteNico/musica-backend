package com.lazarte.musica_backend.service.impl;

import com.lazarte.musica_backend.exception.ResourceNotFoundException;
import com.lazarte.musica_backend.model.dto.request.PlaylistRequestDTO;
import com.lazarte.musica_backend.model.dto.response.PlaylistResponseDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import com.lazarte.musica_backend.model.entity.Playlist;
import com.lazarte.musica_backend.model.entity.Song;
import com.lazarte.musica_backend.model.entity.User;
import com.lazarte.musica_backend.repository.PlaylistRepository;
import com.lazarte.musica_backend.repository.SongRepository;
import com.lazarte.musica_backend.repository.UserRepository;
import com.lazarte.musica_backend.repository.query.PlaylistRepositoryQuery;
import com.lazarte.musica_backend.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final PlaylistRepositoryQuery playlistRepositoryQuery;

    @Override
    public List<PlaylistResponseDTO> findAll() {
        return playlistRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public PlaylistResponseDTO findById(Long id) {
        Playlist playlist = playlistRepositoryQuery.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + id));
        return mapToResponseDto(playlist);
    }

    @Override
    public List<PlaylistResponseDTO> findByUserId(Long userId) {
        return playlistRepositoryQuery.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public PlaylistResponseDTO create(PlaylistRequestDTO requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + requestDto.getUserId()));

        Playlist playlist = Playlist.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .user(user)
                .build();

        return mapToResponseDto(playlistRepository.save(playlist));
    }

    @Override
    @Transactional
    public PlaylistResponseDTO addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + songId));

        if (!playlist.getSongs().contains(song)) {
            playlist.addSong(song);
        }

        return mapToResponseDto(playlistRepository.save(playlist));
    }

    @Override
    @Transactional
    public PlaylistResponseDTO removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + songId));

        playlist.removeSong(song);
        return mapToResponseDto(playlistRepository.save(playlist));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Playlist no encontrada con id: " + id);
        }
        playlistRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PlaylistResponseDTO update(Long id, PlaylistRequestDTO requestDto) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + id));

        playlist.setName(requestDto.getName());
        if (requestDto.getDescription() != null) {
            playlist.setDescription(requestDto.getDescription());
        }
        return mapToResponseDto(playlistRepository.save(playlist));
    }

    private PlaylistResponseDTO mapToResponseDto(Playlist playlist) {
        List<SongResponseDTO> songsDto = playlist.getSongs() != null
                ? playlist.getSongs().stream()
                .map(song -> SongResponseDTO.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .durationSeconds(song.getDurationSeconds())
                        .genre(song.getGenre())
                        .artistId(song.getArtist() != null ? song.getArtist().getId() : null)
                        .artistName(song.getArtist() != null ? song.getArtist().getName() : null)
                        .build())
                .toList()
                : List.of();

        return PlaylistResponseDTO.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .userId(playlist.getUser() != null ? playlist.getUser().getId() : null)
                .name(playlist.getUser() != null ? playlist.getUser().getName() : null)
                .sonIds(playlist.getUser())
                .build();
    }


}