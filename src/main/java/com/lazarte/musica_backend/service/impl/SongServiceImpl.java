package com.lazarte.musica_backend.service.impl;

import com.lazarte.musica_backend.repository.query.ArtistRepositoryQuery;
import com.lazarte.musica_backend.repository.query.SongRepositoryQuery;
import lombok.RequiredArgsConstructor;
import com.lazarte.musica_backend.exception.ResourceNotFoundException;
import com.lazarte.musica_backend.model.dto.request.SongRequestDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import com.lazarte.musica_backend.model.entity.Artist;
import com.lazarte.musica_backend.model.entity.Song;
import com.lazarte.musica_backend.repository.ArtistRepository;
import com.lazarte.musica_backend.repository.SongRepository;
import com.lazarte.musica_backend.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Optimiza lecturas por defecto
public class SongServiceImpl implements SongService {

    private final SongRepositoryQuery songRepositoryQuery;
    private final ArtistRepositoryQuery artistRepositoryQuery;

    @Override
    public Page<SongResponseDTO> findAll(Pageable pageable) {
        return songRepositoryQuery.findAll(pageable)
                .map(this::mapToResponseDto);
    }

    @Override
    public SongResponseDTO findById(Long id) {
        Song song = songRepositoryQuery.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + id));
        return mapToResponseDto(song);
    }

    @Override
    public List<SongResponseDTO> findByTitle(String title) {
        return songRepositoryQuery.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<SongResponseDTO> findByArtistId(Long artistId) {
        return songRepositoryQuery.findByArtistId(artistId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<SongResponseDTO> findByGenreWithArtist(String genre) {
        return songRepositoryQuery.findSongsWithArtistByGente(genre)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional // Escritura: desactiva readOnly y asegura commit/rollback
    public SongResponseDTO create(SongRequestDTO requestDTO) {
        Artist artist = artistRepositoryQuery.findById(requestDTO.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + requestDTO.getArtistId()));

        Song song = Song.builder()
                .title(requestDTO.getTitle())
                .durationSeconds(requestDTO.getDurationSeconds())
                .genre(requestDTO.getGenre())
                .artist(artist)
                .build();

        return mapToResponseDto(songRepositoryQuery.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO update(Long id, SongRequestDTO requestDto) {
        Song song = songRepositoryQuery.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + id));

        if (!song.getArtist().getId().equals(requestDto.getArtistId())) {
            Artist newArtist = artistRepositoryQuery.findById(requestDto.getArtistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + requestDto.getArtistId()));
            song.setArtist(newArtist);
        }


        song.setTitle(requestDto.getTitle());
        song.setDurationSeconds(requestDto.getDurationSeconds());
        song.setGenre(requestDto.getGenre());

        return mapToResponseDto(songRepositoryQuery.save(song));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!songRepositoryQuery.existsById(id)) {
            throw new ResourceNotFoundException("Canción no encontrada con id: " + id);
        }
        songRepositoryQuery.deleteById(id);
    }

    private SongResponseDTO mapToResponseDto(Song song) {
        return SongResponseDTO.builder()
                .id(song.getId())
                .title(song.getTitle())
                .durationSeconds(song.getDurationSeconds())
                .genre(song.getGenre())
                .artistId(song.getArtist().getId())
                .artistName(song.getArtist().getName())
                .build();
    }
}