package com.dpm.service;

import com.dpm.dto.LoginRequest;
import com.dpm.dto.RegisterRequest;
import com.dpm.model.Owner;
import com.dpm.model.Pet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.dpm.service.OwnerService.getOwnerIdFromToken;

/**
 * @author danielpm.dev
 */
@Service
public class PetService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    public PetService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper; // Inyectamos ObjectMapper para parsear JSON
    }

    public List<Pet> getPets(String token) {
        Long userId = getOwnerIdFromToken(token);
        String url = apiBaseUrl + "/api/owner/" + userId + "/pets";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<Pet>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Pet>>() {});
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener las mascotas: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al obtener las mascotas: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public void addPet(String token, Pet pet, Long ownerId) {
        String url = apiBaseUrl + "/api/pet";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construir el cuerpo JSON manualmente para que coincida con el formato
        StringBuilder jsonBody = new StringBuilder("{");
        jsonBody.append("\"name\": \"").append(pet.getName()).append("\",");
        if (pet.getDescription() != null) {
            jsonBody.append("\"description\": \"").append(pet.getDescription()).append("\",");
        }
        if (pet.getAge() != null) {
            jsonBody.append("\"age\": ").append(pet.getAge()).append(",");
        }
        jsonBody.append("\"species\": \"").append(pet.getSpecies()).append("\",");
        if (pet.getBreed() != null) {
            jsonBody.append("\"breed\": \"").append(pet.getBreed()).append("\",");
        }
        jsonBody.append("\"sex\": \"").append(pet.getSex()).append("\",");
        if (pet.getWeight() != null) {
            jsonBody.append("\"weight\": ").append(pet.getWeight()).append(",");
        }
        if (pet.getUrlImage() != null) {
            jsonBody.append("\"urlImage\": \"").append(pet.getUrlImage()).append("\",");
        }
        jsonBody.append("\"diseaseList\": [");
        if (pet.getDiseaseList() != null && !pet.getDiseaseList().isEmpty()) {
            jsonBody.append(pet.getDiseaseList().stream()
                    .map(disease -> "\"" + disease + "\"")
                    .collect(Collectors.joining(",")));
        }
        jsonBody.append("],");
        jsonBody.append("\"ownerId\": ").append(ownerId);
        jsonBody.append("}");

        HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), headers);

        try {
            ResponseEntity<Pet> response = restTemplate.exchange(url, HttpMethod.POST, entity, Pet.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al añadir la mascota: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al añadir la mascota: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public void updatePet(String token, Pet pet, Long ownerId) {
        String url = apiBaseUrl + "/api/pet/" + pet.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construir el cuerpo JSON manualmente para que coincida con el formato
        StringBuilder jsonBody = new StringBuilder("{");
        jsonBody.append("\"name\": \"").append(pet.getName()).append("\",");
        if (pet.getDescription() != null) {
            jsonBody.append("\"description\": \"").append(pet.getDescription()).append("\",");
        }
        if (pet.getAge() != null) {
            jsonBody.append("\"age\": ").append(pet.getAge()).append(",");
        }
        jsonBody.append("\"species\": \"").append(pet.getSpecies()).append("\",");
        if (pet.getBreed() != null) {
            jsonBody.append("\"breed\": \"").append(pet.getBreed()).append("\",");
        }
        jsonBody.append("\"sex\": \"").append(pet.getSex()).append("\",");
        if (pet.getWeight() != null) {
            jsonBody.append("\"weight\": ").append(pet.getWeight()).append(",");
        }
        if (pet.getUrlImage() != null) {
            jsonBody.append("\"imageUrl\": \"").append(pet.getUrlImage()).append("\",");
        }
        jsonBody.append("\"diseaseList\": [");
        if (pet.getDiseaseList() != null && !pet.getDiseaseList().isEmpty()) {
            jsonBody.append(pet.getDiseaseList().stream()
                    .map(disease -> "\"" + disease + "\"")
                    .collect(Collectors.joining(",")));
        }
        jsonBody.append("],");
        jsonBody.append("\"ownerId\": ").append(ownerId);
        jsonBody.append("}");

        HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), headers);

        try {
            ResponseEntity<Pet> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Pet.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al actualizar la mascota: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al actualizar la mascota: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public Pet getPetById(String token, Long id) {
        String url = apiBaseUrl + "/api/pet/" + id; // Ajusté la URL para que sea consistente con la API

        // Configurar los encabezados con el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear la entidad HTTP con los encabezados
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Hacer la solicitud GET con RestTemplate
            ResponseEntity<Pet> response = restTemplate.exchange(url, HttpMethod.GET, entity, Pet.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener la mascota: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al obtener la mascota: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public void deletePetById(String token, Long petId) {
        String url = apiBaseUrl + "/api/pet/" + petId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
            System.out.println("Respuesta de la API: " + response.getBody()); // Depuración
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                // Éxito, no hay cuerpo esperado
            } else {
                throw new RuntimeException("Error al eliminar la mascota: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error de la API: " + e.getResponseBodyAsString()); // Depuración
            throw new RuntimeException("Error al eliminar la mascota: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }
}
