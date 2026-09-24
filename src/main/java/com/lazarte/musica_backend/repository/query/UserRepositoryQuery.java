package com.lazarte.musica_backend.repository.query;

import com.lazarte.musica_backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryQuery extends JpaRepository<User, Long> {

    //Comprueba si existe el email para validar los registros
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
