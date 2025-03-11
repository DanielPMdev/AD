package com.dpm.dto.vaccine;

import com.dpm.entities.Vaccine;

import java.time.LocalDate;

/**
 * @author danielpm.dev
 */
public class FullVaccineDTO {

    private Long id;
    private String vaccineName;
    private LocalDate vaccineDate;

    private Long petId;

    // Constructor vacío para deserialización
    public FullVaccineDTO() {}

    // Constructor para mapear desde la entidad Vaccine
    public FullVaccineDTO(Vaccine vaccine) {
        this.id = vaccine.getId();
        this.vaccineName = vaccine.getVaccineName();
        this.vaccineDate = vaccine.getVaccineDate();
        this.petId = vaccine.getPet().getId();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public LocalDate getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(LocalDate vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}
