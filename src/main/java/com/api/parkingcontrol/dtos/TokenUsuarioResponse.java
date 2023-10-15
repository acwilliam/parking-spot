package com.api.parkingcontrol.dtos;

public class TokenUsuarioResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenUsuarioResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
