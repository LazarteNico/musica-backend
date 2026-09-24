package com.lazarte.musica_backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaybackResponseDTO {
    private Long id;
    private Long userId;
    private Long songId;
    private LocalDateTime playedAt;
}
