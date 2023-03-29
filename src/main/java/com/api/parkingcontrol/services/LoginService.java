package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.Usuario;
import com.api.parkingcontrol.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    final UsuarioRepository usuarioRepository;


    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado para o email: "+ username));

        return new User(usuario.getEmail(), usuario.getPassword(), usuario.isEnabled(),
                true, true, true, usuario.getAuthorities());
    }
}
