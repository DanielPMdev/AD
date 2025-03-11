package com.dpm.controller;

import com.dpm.dto.prescription.FullPrescriptionDTO;
import com.dpm.dto.report.ChangeReportDTO;
import com.dpm.dto.report.FullReportDTO;
import com.dpm.entities.Pet;
import com.dpm.entities.Prescription;
import com.dpm.entities.Report;
import com.dpm.services.pet.PetService;
import com.dpm.services.report.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author danielpm.dev
 */
@RestController
@RequestMapping("/api")
public class ReportController {

    private final ReportService reportService;
    private final PetService petService;

    public ReportController(ReportService reportService, PetService petService) {
        this.reportService = reportService;
        this.petService = petService;
    }

    /*
            GET http://localhost:8080/api/reports
    */
    @GetMapping("/reports")
    public ResponseEntity<List<Report>> findAll() {
        List<Report> reportList = reportService.getAllReports();
        if (reportList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportList);
    }

    /*
        GET http://localhost:8080/api/report/7
     */
    @GetMapping("/report/{id}")
    public ResponseEntity<Report> findAllById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        POST http://localhost:8080/api/report
     */
    @PostMapping("/report")
    public ResponseEntity<Report> createReport(@RequestBody FullReportDTO fullReportDTO) {
        // Si ya tiene un ID asignado, no permitimos crearlo (esto es correcto para un POST)
        if (fullReportDTO.getId() != null)
            return ResponseEntity.badRequest().build();

        // Buscar la mascota por ID
        Pet pet = petService.getPetById(fullReportDTO.getPetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));

        Report reportCreated = new Report();

        reportCreated.setId(fullReportDTO.getId());
        reportCreated.setReportDate(fullReportDTO.getReportDate());
        reportCreated.setClinic(fullReportDTO.getClinic());
        reportCreated.setReason(fullReportDTO.getReason());

        if (fullReportDTO.getPrescriptionList() != null && !fullReportDTO.getPrescriptionList().isEmpty()) {
            List<Prescription> prescriptions = fullReportDTO.getPrescriptionList()
                    .stream()
                    .map(this::convertToPrescription)
                    .collect(Collectors.toList());

            prescriptions.forEach(prescription -> prescription.setReport(reportCreated));

            reportCreated.setPrescriptionList(prescriptions);
        }
        reportCreated.setPet(pet);

        reportService.createOrUpdateReport(reportCreated);
        return ResponseEntity.ok(reportCreated);
    }

    /*
        PUT http://localhost:8080/api/report/3
     */
    @PutMapping("/report/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody ChangeReportDTO report) {
        if (!reportService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener el Report existente desde el servicio para el ID y la Mascota
        Report reportToUpdate = reportService.getReportById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        reportToUpdate.setReportDate(report.getReportDate());
        reportToUpdate.setClinic(report.getClinic());
        reportToUpdate.setReason(report.getReason());

        Report updatedReport = reportService.createOrUpdateReport(reportToUpdate);
        return ResponseEntity.ok(updatedReport);
    }

    /*
        DELETE http://localhost:8080/api/report/{identifier}
     */
    @DeleteMapping("/report/{identifier}")
    public ResponseEntity<Void> deleteReportById(@PathVariable("identifier") Long id) {
        if (!reportService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reportService.deleteReportById(id);
        return ResponseEntity.noContent().build();
    }


    //METHODS AUXILIARES
    private Prescription convertToPrescription(FullPrescriptionDTO dto) {
        Prescription prescription = new Prescription();
        prescription.setId(dto.getId());
        prescription.setMedName(dto.getMedName());
        prescription.setStartDate(dto.getStartDate());
        prescription.setEndDate(dto.getEndDate());
        prescription.setFrequency(dto.getFrequency());

        return prescription;
    }
}
