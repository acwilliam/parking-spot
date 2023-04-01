package com.api.parkingcontrol.config.security;

import com.api.parkingcontrol.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter implements Filter {
    private static final Log log = LogFactory.getLog(JwtAuthenticationFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // nome do cabeçalho de autorização
        String HEADER_STRING = "Authorization";
        String authorizationHeader = httpRequest.getHeader(HEADER_STRING);

        // prefixo do token de autorização
        String TOKEN_PREFIX = "Bearer ";
        // Verifica se o cabeçalho de autorização está presente e contém o prefixo correto
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            String token = authorizationHeader.replace(TOKEN_PREFIX, "");
            try {
                String email;
                // Verifica se o token é válido
                if(JwtUtil.isValidToken(token)) {
                    email = JwtUtil.getSubjectFromToken(token);
                    httpRequest.setAttribute("email", email);
                    log.info("email " + email);
                    log.info("httpRequest " + httpRequest.getRequestedSessionId());
                }
            } catch (JwtException e) {
                // Se o token for inválido, retorna um erro 401 (Não autorizado)
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        chain.doFilter(request, response);
    }
}

