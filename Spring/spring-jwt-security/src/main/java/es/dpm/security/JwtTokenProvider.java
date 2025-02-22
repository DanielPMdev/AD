package es.dpm.security;

import es.dpm.models.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
Se encarga de generar tokens JWT cuando un usuario inicia sesión satisfactoriamente
 */
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    @Value("${app.security.jwt.expiration}")
    private Long jwtDurationSeconds;

    private static final String USERNAME_CLAIM = "username";
    private static final String EMAIL_CLAIM = "email";
    private static final String SUBJECT_CLAIM = "sub";
    private static final String ISSUED_AT_CLAIM = "iat";
    private static final String EXPIRATION_CLAIM = "exp";

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofSeconds(jwtDurationSeconds));

        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put(SUBJECT_CLAIM, String.valueOf(user.getId()));
            claims.put(ISSUED_AT_CLAIM, now.getEpochSecond());
            claims.put(EXPIRATION_CLAIM, expiration.getEpochSecond());
            claims.put(USERNAME_CLAIM, user.getUsername());
            claims.put(EMAIL_CLAIM, user.getEmail());

            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(expiration))
                    .signWith(signingKey, SignatureAlgorithm.HS256)
                    .compact();
        } catch (IllegalArgumentException e) {
            log.error("Error generating JWT token: ", e);
            throw new JwtException("Could not generate JWT token", e);
        }
    }


    public boolean isValidToken(String token) {
        if (!StringUtils.hasLength(token)) {
            return false;
        }

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token);
            return claimsJws != null;
        } catch (SignatureException e) {
            log.info("Error en la firma del token", e);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            log.info("Token incorrecto", e);
        } catch (ExpiredJwtException e) {
            log.info("Token expirado", e);
        } catch (JwtException e) {
            log.info("Error procesando el token", e);
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            return claims.get(USERNAME_CLAIM, String.class);
        } catch (JwtException e) {
            log.error("Error retrieving username from token: ", e);
            return null;
        }
    }
}