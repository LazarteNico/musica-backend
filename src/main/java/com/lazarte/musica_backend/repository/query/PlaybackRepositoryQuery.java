package com.lazarte.musica_backend.repository.query;

import com.lazarte.musica_backend.model.entity.Playback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaybackRepositoryQuery extends JpaRepository<Playback, Long> {

    //Trae el historial de reproducciones ordenado cronologicamente
    List<Playback> findByUserIdOrderByPlayerAtDesc(Long userId);

    //Las canciones mas escuchadas
    @Query("SELECT p.song.id, count(p) FROM Playback p GROUP BY p.song.id ORDER BY count(p) DESC")
    List<Object[]> findTopPlayedSongs();

}
