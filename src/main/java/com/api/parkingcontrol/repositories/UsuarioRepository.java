package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
