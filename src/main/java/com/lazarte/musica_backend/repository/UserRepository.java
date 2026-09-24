package com.lazarte.musica_backend.repository;

import com.lazarte.musica_backend.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final Map<Long, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.getAndIncrement());
        }
        storage.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(Long id){
        return Optional.ofNullable(storage.get(id));
    }

    public List<User> findAll(){
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById(Long id){
        return storage.remove(id) != null;
    }

}
