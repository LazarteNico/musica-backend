package com.lazarte.musica_backend.service;


import com.lazarte.musica_backend.model.dto.response.ArtistResponseDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import com.lazarte.musica_backend.model.dto.request.ArtistRequestDTO;

import java.util.List;

public interface ArtistService {
    ArtistResponseDTO create(ArtistRequestDTO dto);
    ArtistResponseDTO findById(Long id);
    List<ArtistResponseDTO> findAll(String sort);
    ArtistResponseDTO update(Long id, ArtistRequestDTO dto);
    void delete(Long id);
    List<SongResponseDTO> findSongsByArtistId(Long artistId);
}
