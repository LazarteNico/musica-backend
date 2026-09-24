package com.lazarte.musica_backend.controller;

import com.lazarte.musica_backend.model.dto.request.PlaylistRequestDTO;
import com.lazarte.musica_backend.model.dto.response.PlaylistResponseDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import com.lazarte.musica_backend.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<PlaylistResponseDTO> create(@Valid @RequestBody PlaylistRequestDTO dto) {
        return new ResponseEntity<>(playlistService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponseDTO>> getAll() {
        return ResponseEntity.ok(playlistService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PlaylistRequestDTO dto) {
        return ResponseEntity.ok(playlistService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Relationship Endpoint: Get all songs in a playlist
    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongResponseDTO>> getSongsInPlaylist(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.findById(id).getSonIds());
    }
}
