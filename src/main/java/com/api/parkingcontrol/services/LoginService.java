package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.UsuarioDto;
import com.api.parkingcontrol.models.Usuario;
import com.api.parkingcontrol.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    final UsuarioRepository usuarioRepository;


    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Optional<Usuario> login(UsuarioDto usuarioDto) throws Exception {
        return usuarioRepository.findByEmail(usuarioDto.getEmail());

    }
}
