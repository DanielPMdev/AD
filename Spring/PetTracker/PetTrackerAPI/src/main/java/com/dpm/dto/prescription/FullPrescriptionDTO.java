package com.dpm.dto.prescription;

/**
 * @author danielpm.dev
 */
import com.dpm.entities.Prescription;

import java.time.LocalDate;

public class FullPrescriptionDTO {
    private Long id;
    private String medName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String frequency;

    private Long reportId;

    // Constructor vacío para deserialización
    public FullPrescriptionDTO() {}

    // Constructor para mapear desde la entidad Prescription
    public FullPrescriptionDTO(Prescription prescription) {
        this.id = prescription.getId();
        this.medName = prescription.getMedName();
        this.startDate = prescription.getStartDate();
        this.endDate = prescription.getEndDate();
        this.frequency = prescription.getFrequency();
        this.reportId = prescription.getReport().getId();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
}
