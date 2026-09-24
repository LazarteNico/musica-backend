package com.lazarte.musica_backend.service;

import com.lazarte.musica_backend.model.dto.request.PlaybackRequestDTO;
import com.lazarte.musica_backend.model.dto.response.PlaybackResponseDTO;

import java.util.List;

public interface PlaybackService {
    PlaybackResponseDTO recordPlayback(PlaybackRequestDTO dto);
    PlaybackResponseDTO findById(Long id);
    List<PlaybackResponseDTO> findAll();
    void delete(Long id);
}