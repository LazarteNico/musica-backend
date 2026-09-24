package com.lazarte.musica_backend.service.impl;

import com.lazarte.musica_backend.exception.ResourceNotFoundException;
import com.lazarte.musica_backend.model.dto.request.ArtistRequestDTO;
import com.lazarte.musica_backend.model.dto.response.ArtistResponseDTO;
import com.lazarte.musica_backend.model.dto.response.SongResponseDTO;
import com.lazarte.musica_backend.model.entity.Artist;
import com.lazarte.musica_backend.repository.ArtistRepository;
import com.lazarte.musica_backend.repository.SongRepository;
import com.lazarte.musica_backend.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public ArtistResponseDTO create(ArtistRequestDTO dto) {
//        Artist artist = new Artist(null, dto.getName(), dto.getMainGenre(), dto.getMonthlyListeners());

        Artist artist = Artist.builder()
                .name(dto.getName())
                .mainGenre(dto.getMainGenre())
                .nationality(dto.getNationality())
                .build();

        return mapToDTO(artistRepository.save(artist));
    }

    @Override
    public ArtistResponseDTO findById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with ID: " + id));
        return mapToDTO(artist);
    }

    @Override
    public List<ArtistResponseDTO> findAll(String sort) {
        List<Artist> artists = artistRepository.findAll();
        if ("listeners,desc".equalsIgnoreCase(sort)) {
            artists.sort(Comparator.comparing(Artist::getMonthlyListeners).reversed());
        } else if ("listeners,asc".equalsIgnoreCase(sort)) {
            artists.sort(Comparator.comparing(Artist::getMonthlyListeners));
        }
        return artists.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ArtistResponseDTO update(Long id, ArtistRequestDTO dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with ID: " + id));

        artist.setName(dto.getName());
        artist.setMainGenre(dto.getMainGenre());
        artist.setMonthlyListeners(dto.getMonthlyListeners());
        return mapToDTO(artistRepository.save(artist));
    }

    @Override
    public void delete(Long id) {
        if (!artistRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Artist not found with ID: " + id);
        }
    }

    @Override
    public List<SongResponseDTO> findSongsByArtistId(Long artistId) {
        if (artistRepository.findById(artistId).isEmpty()) {
            throw new ResourceNotFoundException("Artist not found with ID: " + artistId);
        }
        return songRepository.findAll().stream()
                .filter(s -> s.getArtist() != null && s.getArtist().getId().equals(artistId))
                .map(s -> new SongResponseDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getDurationSeconds(),
                        s.getArtist().getId(),
                        s.getGenre(),
                        s.getArtist().getName()
                ))
                .toList();
    }

    private ArtistResponseDTO mapToDTO(Artist artist) {
        return new ArtistResponseDTO(
                artist.getId(),
                artist.getName(),
                artist.getMainGenre(),
                artist.getMonthlyListeners(),
                artist.getNationality());
    }
}