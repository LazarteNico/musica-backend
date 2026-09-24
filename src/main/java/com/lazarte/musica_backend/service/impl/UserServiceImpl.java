package com.lazarte.musica_backend.service.impl;

import com.lazarte.musica_backend.exception.ResourceNotFoundException;
import com.lazarte.musica_backend.model.dto.request.UserRequestDTO;
import com.lazarte.musica_backend.model.dto.response.UserResponseDTO;
import com.lazarte.musica_backend.model.entity.User;
import com.lazarte.musica_backend.repository.UserRepository;
import com.lazarte.musica_backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapToDTO(user);
    }

    @Override
    public List<UserResponseDTO> findAll(String sort) {
        List<User> users = userRepository.findAll();
        if ("name,desc".equalsIgnoreCase(sort)) {
            users.sort(Comparator.comparing(User::getName).reversed());
        } else if ("name,asc".equalsIgnoreCase(sort)) {
            users.sort(Comparator.comparing(User::getName));
        }
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> searchByCountry(String country) {
        return userRepository.findAll().stream()
                .filter(u -> u.getCountry().equalsIgnoreCase(country))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCountry(dto.getCountry());
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.deleteById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    private UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getCountry());
    }
}