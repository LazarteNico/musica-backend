package com.lazarte.musica_backend.service.impl;

import com.lazarte.musica_backend.exception.ResourceNotFoundException;
import com.lazarte.musica_backend.model.dto.request.PlaybackRequestDTO;
import com.lazarte.musica_backend.model.dto.response.PlaybackResponseDTO;
import com.lazarte.musica_backend.model.entity.Playback;
import com.lazarte.musica_backend.model.entity.User;
import com.lazarte.musica_backend.repository.PlaybackRepository;
import com.lazarte.musica_backend.repository.SongRepository;
import com.lazarte.musica_backend.repository.UserRepository;
import com.lazarte.musica_backend.service.PlaybackService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaybackServiceImpl implements PlaybackService {

    private final PlaybackRepository playbackRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public PlaybackServiceImpl(PlaybackRepository playbackRepository, UserRepository userRepository, SongRepository songRepository) {
        this.playbackRepository = playbackRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Override
    public PlaybackResponseDTO recordPlayback(PlaybackRequestDTO dto) {
        if (userRepository.findById(dto.getUserId()).isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + dto.getUserId());
        }
        if (songRepository.findById(dto.getSongId()).isEmpty()) {
            throw new ResourceNotFoundException("Song not found with ID: " + dto.getSongId());
        }
        Playback playback = Playback.builder()
                .userId(dto.getUserId())
                .songId(dto.getSongId())
                .playedAt(LocalDateTime.now())
                .build();
        return mapToDTO(playbackRepository.save(playback));
    }

    @Override
    public PlaybackResponseDTO findById(Long id) {
        Playback playback = playbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playback record not found with ID: " + id));
        return mapToDTO(playback);
    }

    @Override
    public List<PlaybackResponseDTO> findAll() {
        return playbackRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!playbackRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Playback record not found with ID: " + id);
        }
    }

    private PlaybackResponseDTO mapToDTO(Playback playback) {
        return new PlaybackResponseDTO(playback.getId(), playback.getUserId(), playback.getSongId(), playback.getPlayedAt());
    }
}