package com.lazarte.musica_backend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaylistRequestDTO {
    @NotBlank(message = "Playlist name cannot be blank")
    private String name;

    @NotNull(message = "User ID is required")
    private Long userId;

    private List<Long> songIds = new ArrayList<>();

    private String description;

}
