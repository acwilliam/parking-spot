package com.api.parkingcontrol.services;

import com.api.parkingcontrol.config.security.SpringSecurityConfiguration;
import com.api.parkingcontrol.models.Funcionario;
import com.api.parkingcontrol.models.Usuario;
import com.api.parkingcontrol.repositories.CriarFuncionarioRepository;
import com.api.parkingcontrol.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class CriarUsuarioService {

    final UsuarioRepository usuarioRepository;
    final CriarFuncionarioRepository criarFuncionarioRepository;
    private final SpringSecurityConfiguration passwordEncoder;


    public CriarUsuarioService(UsuarioRepository usuarioRepository, CriarFuncionarioRepository criarFuncionarioRepository, SpringSecurityConfiguration passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.criarFuncionarioRepository = criarFuncionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criarUsuario(Usuario user) throws Exception {

        String senhaCriptografada = passwordEncoder.passwordEncoder().encode(user.getPassword());
        user.setPassword(senhaCriptografada);
        if(usuarioRepository.existsByName(user.getName())) {
            throw new Exception("Conflito: Este nome esta em uso");
        }else if (usuarioRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Conflito: Email esta em uso");
        }
       return this.usuarioRepository.save(user);
    }

    public Funcionario save(Funcionario funcionario) throws Exception {
        if(criarFuncionarioRepository.existsByEmail(funcionario.getEmail())){
            throw new Exception("Conflito: Funcionario já esta cadastrado");
        }
        return this.criarFuncionarioRepository.save(funcionario);
    }
}
