package com.lazarte.musica_backend.repository.query;

import com.lazarte.musica_backend.model.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepositoryQuery extends JpaRepository<Song, Long> {

    //Query Method 1: Busca canciones por titulo ignorando mayusculas o minusculas
    List<Song> findByTitleContainingIgnoreCase(String title);

    //Query Method 2: Trae canciones de un artista
    List<Song> findByArtistId(Long artistId);

    //Query 1: Trae canciones con el artista por genero
    @Query("SELECT s FROM Song s JOIN FETCH s.artist WHERE LOWER(s.genre) = LOWER(:genre)")
    List<Song> findSongsWithArtistByGente(@Param("genre") String genre);

    //Consulta con paginacion para canciones de algun genero
    Page<Song> findByGenreIgnoreCase(String genre, Pageable pageable);

}
