package com.lazarte.musica_backend.repository;

import com.lazarte.musica_backend.model.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SongRepository {
    private final Map<Long, Song> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Song save(Song song) {
        if(song.getId() == null) {
            song.setId(idCounter.getAndIncrement());
        }
        storage.put(song.getId(), song);
        return song;
    }

    public Optional<Song> findById (Long id){
        return Optional.ofNullable(storage.get(id));
    }

    public List<Song> findAll(){
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById(Long id){
        return storage.remove(id) != null;
    }

}
