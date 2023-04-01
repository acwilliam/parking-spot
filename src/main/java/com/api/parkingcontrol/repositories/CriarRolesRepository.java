package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.enums.RoleEnum;
import com.api.parkingcontrol.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CriarRolesRepository extends JpaRepository<RoleModel, UUID> {

    boolean existsByRoleName(RoleEnum roleName);
}
