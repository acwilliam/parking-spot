package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CriarFuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    boolean existsByEmail(String name);
}
