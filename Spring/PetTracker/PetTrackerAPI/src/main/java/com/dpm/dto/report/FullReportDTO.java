package com.dpm.dto.report;

/**
 * @author danielpm.dev
 */
import com.dpm.dto.prescription.FullPrescriptionDTO;
import com.dpm.entities.Report;

import java.time.LocalDate;
import java.util.List;

public class FullReportDTO {
    private Long id;
    private LocalDate reportDate;
    private String clinic;
    private String reason;
    private List<FullPrescriptionDTO> prescriptionList;

    private Long petId;

    // Constructor vacío para deserialización
    public FullReportDTO() {}

    // Constructor para mapear desde la entidad Report
    public FullReportDTO(Report report) {
        this.id = report.getId();
        this.reportDate = report.getReportDate();
        this.clinic = report.getClinic();
        this.reason = report.getReason();
        this.petId = report.getPet().getId();

        this.prescriptionList = report.getPrescriptionList() != null
                ? report.getPrescriptionList().stream().map(FullPrescriptionDTO::new).toList()
                : null;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<FullPrescriptionDTO> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<FullPrescriptionDTO> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}
