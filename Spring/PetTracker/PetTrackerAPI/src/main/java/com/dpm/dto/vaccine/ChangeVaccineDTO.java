package com.dpm.dto.vaccine;

import com.dpm.entities.Vaccine;

import java.time.LocalDate;

/**
 * @author danielpm.dev
 */
public class ChangeVaccineDTO {


    private Long id;
    private String vaccineName;
    private LocalDate vaccineDate;

    // Constructor vacío para deserialización
    public ChangeVaccineDTO() {}

    // Constructor para mapear desde la entidad Vaccine
    public ChangeVaccineDTO(Vaccine vaccine) {
        this.id = vaccine.getId();
        this.vaccineName = vaccine.getVaccineName();
        this.vaccineDate = vaccine.getVaccineDate();
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
}
