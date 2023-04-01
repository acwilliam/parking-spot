package com.api.parkingcontrol.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final Log log = LogFactory.getLog(JwtUtil.class);
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //Gera um token JWT com o assunto 'subject' e tempo de expiração
    public static String generateToken(String subject, long expirationMillis) {

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    //verifica se o token fornecido é valido
    public static boolean isValidToken(String token) {
        try {
            log.info("passou aqui 11");
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.info("passou aqui 12");
            throw new JwtException("Token invalido");
        }
    }
    //retorna o assunto 'subject' do token fornecido
    public static String getSubjectFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
