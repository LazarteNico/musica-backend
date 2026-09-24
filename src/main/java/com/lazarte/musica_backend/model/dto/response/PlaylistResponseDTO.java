package com.lazarte.musica_backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private Long userId;
    private List<Long> sonIds;
    private String description;
}
