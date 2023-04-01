package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.RoleModel;
import com.api.parkingcontrol.repositories.CriarRolesRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class CriaRolesService {
    private static final Log log = LogFactory.getLog(CriaRolesService.class);
    final CriarRolesRepository criarRolesRepository;

    public CriaRolesService(CriarRolesRepository criarRolesRepository) {
        this.criarRolesRepository = criarRolesRepository;
    }

    public void cadastrarRoles(RoleModel roleModel) throws Exception {

        if (criarRolesRepository.existsByRoleName(roleModel.getRoleName())) {
            throw new Exception("Conflito: Role já esta cadastrada");
        }
        log.info("service "+ roleModel);
        criarRolesRepository.save(roleModel);
    }
}
