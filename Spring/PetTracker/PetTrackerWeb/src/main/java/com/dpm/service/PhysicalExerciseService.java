package com.dpm.service;

import com.dpm.model.PhysicalExercise;
import com.dpm.model.Vaccine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author danielpm.dev
 */
@Service
public class PhysicalExerciseService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    public PhysicalExerciseService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper; // Inyectamos ObjectMapper para parsear JSON
    }

    public List<PhysicalExercise> getExercises(String token, Long petId) {
        String url = apiBaseUrl + "/api/pet/" + petId + "/physicalexercise";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<PhysicalExercise>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<PhysicalExercise>>() {});
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener las vacunas: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al obtener las vacunas: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public void saveExercise(String token, PhysicalExercise exercise, Long petId) {
        String url = apiBaseUrl + "/api/physicalexercise";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construir el cuerpo JSON manualmente para que coincida con el formato del ejemplo
        StringBuilder jsonBody = new StringBuilder("{");
        jsonBody.append("\"petId\": ").append(petId).append(",");
        jsonBody.append("\"duration\": \"").append(exercise.getDuration()).append("\",");
        jsonBody.append("\"exerciseDate\": \"").append(exercise.getExerciseDate().toString()).append("\"");
        jsonBody.append("}");

        HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), headers);

        try {
            ResponseEntity<PhysicalExercise> response = restTemplate.exchange(url, HttpMethod.POST, entity, PhysicalExercise.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al añadir el ejercicio: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al añadir el ejercicio: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public void deleteExerciseById(String token, Long exerciseId) {
        String url = apiBaseUrl + "/api/physicalexercise/" + exerciseId;

        // Configurar los encabezados con el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());

        // Crear la entidad HTTP con los encabezados
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

            if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
                throw new RuntimeException("Error al eliminar el ejercicio: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al eliminar el ejercicio: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public Vaccine getExercisesById(String token, Long vaccineId) {
        String url = apiBaseUrl + "/api/physicalexercise/" + vaccineId; // URL ajustada para obtener una vacuna por ID

        // Configurar los encabezados con el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear la entidad HTTP con los encabezados
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Hacer la solicitud GET con RestTemplate
            ResponseEntity<Vaccine> response = restTemplate.exchange(url, HttpMethod.GET, entity, Vaccine.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener la vacuna: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al obtener la vacuna: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

}
