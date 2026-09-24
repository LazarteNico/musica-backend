package com.lazarte.musica_backend.repository.query;

import com.lazarte.musica_backend.model.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepositoryQuery extends JpaRepository<Artist, Long> {

    //Busqueda por nombre
    Optional<Artist> findByNameIgnoreCase(String name);

    List<Artist> findByNameContainingIgnoreCase(String name);

}
