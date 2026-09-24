package com.lazarte.musica_backend.controller;

import com.lazarte.musica_backend.model.dto.request.PlaybackRequestDTO;
import com.lazarte.musica_backend.model.dto.response.PlaybackResponseDTO;
import com.lazarte.musica_backend.service.PlaybackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playbacks")
public class PlaybackController {

    private final PlaybackService playbackService;

    public PlaybackController(PlaybackService playbackService) {
        this.playbackService = playbackService;
    }

    @PostMapping
    public ResponseEntity<PlaybackResponseDTO> record(@Valid @RequestBody PlaybackRequestDTO dto) {
        return new ResponseEntity<>(playbackService.recordPlayback(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaybackResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(playbackService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlaybackResponseDTO>> getAll() {
        return ResponseEntity.ok(playbackService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playbackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}