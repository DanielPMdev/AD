package com.dpm.service;

import com.dpm.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author danielpm.dev
 */
@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    // Autenticación para obtener el JWT
//    public String authenticate(String username, String password) {
//        String url = apiBaseUrl + "/auth/login"; // Ajusta el endpoint de login
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
//        HttpEntity<String> entity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//        // Extrae el token del JSON de respuesta (ajusta según tu API)
//        return extractToken(response.getBody());
//    }

    // Ejemplo: Obtener lista de mascotas
    public Pet[] getPets() {
        String url = apiBaseUrl + "/pets"; // Ajusta el endpoint
        return restTemplate.getForObject(url, Pet[].class);
    }

    // Ejemplo: Obtener detalles de una mascota
    public Pet getPetById(Long id) {
        String url = apiBaseUrl + "/pets/" + id;
        return restTemplate.getForObject(url, Pet.class);
    }

    private String extractToken(String responseBody) {
        // Parsea el JSON para obtener el token (usa Jackson o similar si es necesario)
        return "token-extraído"; // Implementa según el formato de tu API
    }
}
