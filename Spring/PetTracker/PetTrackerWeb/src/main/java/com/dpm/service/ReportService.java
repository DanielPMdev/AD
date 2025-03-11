package com.dpm.service;

import com.dpm.model.PhysicalExercise;
import com.dpm.model.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author danielpm.dev
 */
@Service
public class ReportService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    public ReportService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper; // Inyectamos ObjectMapper para parsear JSON
    }

    public List<Report> getReports(String token, Long petId) {
        String url = apiBaseUrl + "/api/pet/" + petId + "/reports";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<Report>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Report>>() {
            });
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener los informes médicos: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al obtener los informes médicos: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }

    public void saveReport(String token, Report report, Long petId) {
        String url = apiBaseUrl + "/api/report";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);

        StringBuilder jsonBody = new StringBuilder("{");
        jsonBody.append("\"petId\": ").append(petId).append(",");
        jsonBody.append("\"reportDate\": \"").append(report.getReportDate()).append("\",");
        jsonBody.append("\"clinic\": \"").append(report.getClinic()).append("\",");
        jsonBody.append("\"reason\": \"").append(report.getReason()).append("\",");
        jsonBody.append("\"prescriptionList\": [");
        if (report.getPrescriptionList() != null && !report.getPrescriptionList().isEmpty()) {
            jsonBody.append(report.getPrescriptionList().stream()
                    .map(p -> String.format("{\"medName\": \"%s\", \"startDate\": \"%s\", \"endDate\": \"%s\", \"frequency\": \"%s\"}",
                            p.getMedName(), p.getStartDate(), p.getEndDate(), p.getFrequency()))
                    .collect(Collectors.joining(",")));
        }
        jsonBody.append("]");
        jsonBody.append("}");

        HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), headers);
        ResponseEntity<Report> response = restTemplate.exchange(url, HttpMethod.POST, entity, Report.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error al guardar el informe: " + response.getStatusCode());
        }
    }

    public void deleteReportById(String token, Long reportId) {
        String url = apiBaseUrl + "/api/report/" + reportId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new RuntimeException("Error al eliminar el informe: " + response.getStatusCode());
        }
    }

    public void deletePrescriptionById(String token, Long prescriptionId) {
        String url = apiBaseUrl + "/api/prescription/" + prescriptionId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.trim());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new RuntimeException("Error al eliminar la prescripción: " + response.getStatusCode());
        }
    }
}

