package com.lazarte.musica_backend.repository;

import com.lazarte.musica_backend.model.entity.Artist;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArtistRepository {
    private final Map<Long, Artist> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Artist save(Artist artist) {
        if (artist.getId() == null) {
            artist.setId(idCounter.getAndIncrement());
        }
        storage.put(artist.getId(), artist);
        return artist;
    }

    public Optional<Artist> findById(Long id){
        return Optional.ofNullable(storage.get(id));
    }

    public List<Artist> findAll(){
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById(Long id){
        return storage.remove(id) != null;
    }

}
