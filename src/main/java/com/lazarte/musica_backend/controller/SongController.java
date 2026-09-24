package com.lazarte.musica_backend.controller;

import com.lazarte.musica_backend.model.dto.request.SongRequestDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import com.lazarte.musica_backend.service.SongService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<SongResponseDTO> create(@Valid @RequestBody SongRequestDTO dto) {
        return new ResponseEntity<>(songService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SongResponseDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "title")Pageable pageable) {
        return ResponseEntity.ok(songService.findAll(pageable));
    }

    // Additional Endpoint: Search by genre
    @GetMapping("/search")
    public ResponseEntity<List<SongResponseDTO>> searchByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(songService.searchByGenre(genre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SongRequestDTO dto) {
        return ResponseEntity.ok(songService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }
}