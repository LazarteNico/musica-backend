package com.lazarte.musica_backend.service;

import com.lazarte.musica_backend.model.dto.request.SongRequestDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongService {
    Page<SongResponseDTO> findAll(Pageable pageable);
    SongResponseDTO findById(Long id);
    List<SongResponseDTO> findByTitle(String title);
    List<SongResponseDTO> findByArtistId(Long artistId);
    List<SongResponseDTO> findByGenreWithArtist(String genre);
    SongResponseDTO create(SongRequestDTO requestDto);
    SongResponseDTO update(Long id, SongRequestDTO requestDto);
    void delete(Long id);
}
