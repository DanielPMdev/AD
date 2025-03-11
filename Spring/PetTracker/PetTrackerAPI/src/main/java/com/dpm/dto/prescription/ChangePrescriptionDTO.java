package com.dpm.dto.prescription;

import com.dpm.entities.Prescription;

import java.time.LocalDate;

/**
 * @author danielpm.dev
 */
public class ChangePrescriptionDTO {

    private Long id;
    private String medName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String frequency;

    // Constructor vacío para deserialización
    public ChangePrescriptionDTO() {}

    // Constructor para mapear desde la entidad Prescription
    public ChangePrescriptionDTO(Prescription prescription) {
        this.id = prescription.getId();
        this.medName = prescription.getMedName();
        this.startDate = prescription.getStartDate();
        this.endDate = prescription.getEndDate();
        this.frequency = prescription.getFrequency();
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
}
