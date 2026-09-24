package com.lazarte.musica_backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResponseDTO {
    private Long id;
    private String name;
    private String mainGenre;
    private Integer monthlyListeners;
    private String nationality;
}
