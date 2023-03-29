package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.FuncionarioDto;
import com.api.parkingcontrol.dtos.UsuarioDto;
import com.api.parkingcontrol.models.Funcionario;
import com.api.parkingcontrol.models.Usuario;
import com.api.parkingcontrol.services.CriarUsuarioService;
import com.api.parkingcontrol.services.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/api")
public class CriarUsuarioController {

    final CriarUsuarioService criarUsuarioService;
    final LoginService loginService;

    public CriarUsuarioController(CriarUsuarioService criarUsuarioService, LoginService loginService) {
        this.criarUsuarioService = criarUsuarioService;
        this.loginService = loginService;
    }

    @PostMapping("/users/")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UsuarioDto userDto) throws Exception {
        var usuarioModel = new Usuario();
        BeanUtils.copyProperties(userDto, usuarioModel);


        try {
            criarUsuarioService.criarUsuario(usuarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com Sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UsuarioDto usuarioDto) {

        UserDetails userDetails= loginService.loadUserByUsername(usuarioDto.getEmail());

        if(new BCryptPasswordEncoder().matches(usuarioDto.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.ok("Login Realizado com Sucesso!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou email inválido");
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Object> cadastrarFuncionario(@RequestBody @Valid FuncionarioDto funcionarioDto) throws Exception {
        var funcionarioModel = new Funcionario();

        funcionarioModel.setName(funcionarioDto.getName());
        funcionarioModel.setSobreNome(funcionarioDto.getSobreName());
        funcionarioModel.setEmail(funcionarioDto.getEmail());
        funcionarioModel.setDataNascimento(funcionarioDto.getDataNascimento());
        funcionarioModel.setTelefone(funcionarioDto.getTelefone());
        funcionarioModel.setDataInicioContrato(LocalDate.now());

        try {
            criarUsuarioService.save(funcionarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Funcionario cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
