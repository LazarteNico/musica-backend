package com.lazarte.musica_backend.model.dto.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class SongRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 second")
    private Integer durationSeconds;

    @NotNull(message = "Artist ID is required")
    private Long artistId;

    @NotBlank(message = "Genre is required")
    private String genre;
}
