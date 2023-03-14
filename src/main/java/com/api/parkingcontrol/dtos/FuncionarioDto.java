package com.api.parkingcontrol.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class FuncionarioDto {

    @NotNull(message = "Data de nascimento não pode ser nula")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicioContrato;

    @NotBlank
    private String name;

    @NotBlank
    private String sobreName;

    @NotBlank
    @Email(message = "Endereço de email inválido")
    private String email;

    @NotBlank
    private String telefone;

    public String getSobreName() {
        return sobreName;
    }

    public void setSobreName(String sobreName) {
        this.sobreName = sobreName;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataInicioContrato() {
        return dataInicioContrato;
    }

    public void setDataInicioContrato(LocalDate dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
