package com.lazarte.musica_backend.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArtistRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Main genre is required")
    private String mainGenre;

    @NotNull(message = "Monthly listeners count is required")
    @Min(value = 0, message = "Monthlu listeners must be greater than or equial to 0")
    private Integer monthlyListeners;

    private String nationality;
}
