package com.lazarte.musica_backend.repository;

import com.lazarte.musica_backend.model.entity.Playback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PlaybackRepository {
    private final Map<Long, Playback> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Playback save(Playback playback){
        if(playback.getId() == null) {
            playback.setId(idCounter.getAndIncrement());
        }
        storage.put(playback.getId(), playback);
        return playback;
    }

    public Optional<Playback> findById(Long id){
        return Optional.ofNullable(storage.get(id));
    }

    public List<Playback> findAll (){
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById (Long id) {
        return storage.remove(id) != null;
    }
}
