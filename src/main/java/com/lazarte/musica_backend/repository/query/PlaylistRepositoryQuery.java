package com.lazarte.musica_backend.repository.query;

import com.lazarte.musica_backend.model.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepositoryQuery extends JpaRepository<Playlist, Long> {

    //Query Method para Listar las playlist creadas por un usuario con su id
    List<Playlist> findByUserId(Long userId);

    //Query con JPQL Trae la playlist con sus canciones y el usuario
    @Query("SELECT DISTINCT p FROM Playlist p" +
            "JOIN FETCH p.user" +
            "LEFT JOIN FETCH p.songs" +
            "WHERE p.id = :id")
    Optional<Playlist> findByIdWithDetails(@Param("id") Long id);


}
