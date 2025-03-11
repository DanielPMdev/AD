package com.dpm.service;

import com.dpm.model.Owner;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author danielpm.dev
 */
@Service
public class OwnerService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    @Value("${jwt.secret.file}")
    private String secretFilePath;

    private String SECRET_KEY;
    private static SecretKey signingKey;

    @PostConstruct
    public void init() {
        try {
            SECRET_KEY = new String(Files.readAllBytes(Paths.get(secretFilePath))).trim();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo encontrar el archivo en la ruta: " + secretFilePath, e);
        }
        this.signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public OwnerService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Owner getAuthenticatedUser(String token) {
        Long userId = getOwnerIdFromToken(token);

        String url = apiBaseUrl + "/api/owner/" + userId;

        HttpHeaders headers = new HttpHeaders();
        String authHeader = "Bearer " + token.trim(); // Aseguro que no haya espacios extra
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Owner> response = restTemplate.exchange(url, HttpMethod.GET, entity, Owner.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener los datos del usuario: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al obtener los datos del usuario: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public static Long getOwnerIdFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            String subject = claims.get(Claims.SUBJECT, String.class);
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El ID en el token no es un número válido: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al extraer el ID del token: " + e.getMessage());
        }
    }

    // Actualizar el email mediante llamada a la API
    public void updateEmail(String token, String email) {
        Long userId = getOwnerIdFromToken(token);
        String url = apiBaseUrl + "/api/owner/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        // Crear el cuerpo de la solicitud con el nuevo email
        String requestBody = "{\"email\": \"" + email + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Owner> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Owner.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al actualizar el email: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al actualizar el email: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    // Actualizar la imagen mediante llamada a la API
    public void updateImage(String token, String imageUrl) {
        Long userId = getOwnerIdFromToken(token);
        String url = apiBaseUrl + "/api/owner/image/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear el cuerpo de la solicitud con la nueva URL de la imagen
        String requestBody = "{\"imageUrl\": \"" + imageUrl + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Owner> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Owner.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al actualizar la imagen: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al actualizar la imagen: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    // Actualizar la contraseña mediante llamada a la API
    public void updatePassword(String token, String password) {
        Long userId = getOwnerIdFromToken(token);
        String url = apiBaseUrl + "/api/owner/changePassword/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear el cuerpo de la solicitud con la nueva URL de la imagen
        String requestBody = "{\"password\": \"" + password + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Owner> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Owner.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al actualizar la contraseña: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al actualizar la contraseña: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    // Eliminar el Owner mediante llamada a la API
    public void deleteOwner(String token) {
        Long userId = getOwnerIdFromToken(token);
        String url = apiBaseUrl + "/api/owner/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
            if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
                throw new RuntimeException("Error al eliminar el usuario: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }
}