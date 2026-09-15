package com.pfe.pfeapp.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Clé secrète utilisée pour signer les tokens (générée une fois au démarrage)
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Durée de validité du token : 24h (en millisecondes)
    private final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    // Génère un token à partir du username et du rôle
    public String generateToken(String usernameTelegram, String role) {
        return Jwts.builder()
                .setSubject(usernameTelegram)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    // Extrait le username depuis un token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extrait le rôle depuis un token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Vérifie si le token est expiré
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Valide le token (signature correcte + non expiré)
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}