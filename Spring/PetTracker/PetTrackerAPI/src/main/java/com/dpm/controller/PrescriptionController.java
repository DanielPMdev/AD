package com.dpm.controller;

import com.dpm.dto.prescription.ChangePrescriptionDTO;
import com.dpm.dto.prescription.FullPrescriptionDTO;
import com.dpm.entities.Prescription;
import com.dpm.entities.Report;
import com.dpm.services.prescription.PrescriptionService;
import com.dpm.services.report.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author danielpm.dev
 */
@RestController
@RequestMapping("/api")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final ReportService reportService;

    public PrescriptionController(PrescriptionService prescriptionService, ReportService reportService) {
        this.prescriptionService = prescriptionService;
        this.reportService = reportService;
    }

    /*
                GET http://localhost:8080/api/prescriptions
        */
    @GetMapping("/prescriptions")
    public ResponseEntity<List<Prescription>> findAll() {
        List<Prescription> prescriptionList = prescriptionService.getAllPrescriptions();
        if (prescriptionList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prescriptionList);
    }

    /*
        GET http://localhost:8080/api/prescription/7
     */
    @GetMapping("/prescription/{id}")
    public ResponseEntity<Prescription> findAllById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return prescriptionService.getPrescriptionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        POST http://localhost:8080/api/prescription
     */
    @PostMapping("/prescription")
    public ResponseEntity<Prescription> createPrescription(@RequestBody FullPrescriptionDTO fullPrescriptionDTO) {
        // Si ya tiene un ID asignado, no permitimos crearlo (esto es correcto para un POST)
        if (fullPrescriptionDTO.getId() != null)
            return ResponseEntity.badRequest().build();

        //Buscar el report por la ID
        Report report = reportService.getReportById(fullPrescriptionDTO.getReportId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found"));

        Prescription prescriptionCreated = new Prescription();

        prescriptionCreated.setId(fullPrescriptionDTO.getId());
        prescriptionCreated.setMedName(fullPrescriptionDTO.getMedName());
        prescriptionCreated.setStartDate(fullPrescriptionDTO.getStartDate());
        prescriptionCreated.setEndDate(fullPrescriptionDTO.getEndDate());
        prescriptionCreated.setFrequency(fullPrescriptionDTO.getFrequency());

        prescriptionCreated.setReport(report);

        prescriptionService.createOrUpdatePrescription(prescriptionCreated);
        return ResponseEntity.ok(prescriptionCreated);
    }

    /*
        PUT http://localhost:8080/api/prescription/3
     */
    @PutMapping("/prescription/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody ChangePrescriptionDTO prescription) {
        if (!prescriptionService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener la Prescription existente desde el servicio para el ID y el Report
        Prescription prescriptionToUpdate = prescriptionService.getPrescriptionById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        prescriptionToUpdate.setMedName(prescription.getMedName());
        prescriptionToUpdate.setStartDate(prescription.getStartDate());
        prescriptionToUpdate.setEndDate(prescription.getEndDate());
        prescriptionToUpdate.setFrequency(prescription.getFrequency());

        Prescription updatedPrescription = prescriptionService.createOrUpdatePrescription(prescriptionToUpdate);
        return ResponseEntity.ok(updatedPrescription);
    }

    /*
        DELETE http://localhost:8080/api/prescription/{identifier}
     */
    @DeleteMapping("/prescription/{identifier}")
    public ResponseEntity<Void> deletePrescriptionById(@PathVariable("identifier") Long id) {
        if (!prescriptionService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        prescriptionService.deletePrescriptionById(id);
        return ResponseEntity.noContent().build();
    }
}
