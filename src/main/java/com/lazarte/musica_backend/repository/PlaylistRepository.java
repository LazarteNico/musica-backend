package com.lazarte.musica_backend.repository;

import com.lazarte.musica_backend.model.entity.Playlist;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PlaylistRepository {
    private final Map<Long, Playlist> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Playlist save(Playlist playlist) {
        if(playlist.getId() ==  null){
            playlist.setId(idCounter.getAndIncrement());
        }
        storage.put(playlist.getId(), playlist);
        return playlist;
    }

    public Optional<Playlist> findById(Long id){
        return Optional.ofNullable(storage.get(id));
    }

    public List<Playlist> findAll(){
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById(Long id){
        return storage.remove(id) != null;
    }
}
