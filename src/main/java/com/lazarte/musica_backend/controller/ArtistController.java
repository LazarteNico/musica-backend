package com.lazarte.musica_backend.controller;

import com.lazarte.musica_backend.model.dto.request.ArtistRequestDTO;
import com.lazarte.musica_backend.model.dto.response.ArtistResponseDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import com.lazarte.musica_backend.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping
    public ResponseEntity<ArtistResponseDTO> create(@Valid @RequestBody ArtistRequestDTO dto) {
        return new ResponseEntity<>(artistService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> getAll(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(artistService.findAll(sort));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ArtistRequestDTO dto) {
        return ResponseEntity.ok(artistService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Relationship Endpoint: Get all songs belonging to an artist
    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongResponseDTO>> getSongsByArtist(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.findSongsByArtistId(id));
    }
}