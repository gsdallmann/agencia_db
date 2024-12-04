package com.agencia.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // Adicionando @Component para que o Spring possa gerenciar esta classe como um bean
public class JwtTokenProvider {

    private static final String SECRET_KEY = "sua-chave-secreta"; // Substitua por uma chave segura
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    // Gera um token JWT
    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
