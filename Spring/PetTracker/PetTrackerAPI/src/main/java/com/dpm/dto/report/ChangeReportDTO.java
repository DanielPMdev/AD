package com.dpm.dto.report;

import com.dpm.entities.Report;

import java.time.LocalDate;

/**
 * @author danielpm.dev
 */
public class ChangeReportDTO {

    private Long id;
    private LocalDate reportDate;
    private String clinic;
    private String reason;

    private Long petId;

    public ChangeReportDTO() {
    }

    // Constructor para mapear desde la entidad Report
    public ChangeReportDTO(Report report) {
        this.id = report.getId();
        this.reportDate = report.getReportDate();
        this.clinic = report.getClinic();
        this.reason = report.getReason();
        this.petId = report.getPet().getId();
    }

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

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}
