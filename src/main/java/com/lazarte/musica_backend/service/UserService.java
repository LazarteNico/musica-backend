package com.lazarte.musica_backend.service;

import com.lazarte.musica_backend.model.dto.request.UserRequestDTO;
import com.lazarte.musica_backend.model.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO dto);
    UserResponseDTO findById(Long id);
    List<UserResponseDTO> findAll(String sort);
    List<UserResponseDTO> searchByCountry(String country);
    UserResponseDTO update(Long id, UserRequestDTO dto);
    void delete(Long id);
}
