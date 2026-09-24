package com.lazarte.musica_backend.model.dto.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongResponseDTO {
    private Long id;
    private String title;
    private Integer durationSeconds;
    private Long artistId;
    private String genre;
    private String artistName;

}
