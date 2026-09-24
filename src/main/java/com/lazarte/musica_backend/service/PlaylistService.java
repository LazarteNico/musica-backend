package com.lazarte.musica_backend.service;

import com.lazarte.musica_backend.model.dto.request.PlaylistRequestDTO;
import com.lazarte.musica_backend.model.dto.response.PlaylistResponseDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;

import java.util.List;

public interface PlaylistService {
    List<PlaylistResponseDTO> findAll();
    PlaylistResponseDTO findById(Long id);
    List<PlaylistResponseDTO> findByUserId(Long userId);
    PlaylistResponseDTO create(PlaylistRequestDTO requestDto);
    PlaylistResponseDTO addSongToPlaylist(Long playlistId, Long songId);
    PlaylistResponseDTO removeSongFromPlaylist(Long playlistId, Long songId);
    void delete(Long id);
    PlaylistResponseDTO update(Long id, PlaylistRequestDTO requestDto);
}