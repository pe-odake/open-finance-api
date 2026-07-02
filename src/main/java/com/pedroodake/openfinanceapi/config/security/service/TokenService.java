package com.pedroodake.openfinanceapi.config.security.service;

import com.pedroodake.openfinanceapi.application.core.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    private final String issuer = "OpenFinance";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(Usuario usuario) {
        try {
            return Jwts.builder()
                    .issuer(issuer)
                    .subject(usuario.getLogin())
                    .expiration(dataExpiracao())
                    .signWith(getSigningKey())
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Erro ao gerar Token JWT!", e);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(tokenJWT)
                    .getPayload();

            if (issuer.equals(claims.getIssuer())) {
                return claims.getSubject();
            }
            throw new RuntimeException("Token inválido: Issuer incorreto!");
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido ou expirado!", e);
        }
    }

    private Date dataExpiracao() {
        return new Date(System.currentTimeMillis() + 30 * 60 * 1000);
    }
}