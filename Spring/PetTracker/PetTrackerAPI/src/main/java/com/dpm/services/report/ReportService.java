package com.dpm.services.report;

import com.dpm.entities.Report;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface ReportService {

    boolean existsById(Long id);

    //Methods retrive
    List<Report> getAllReports();

    Optional<Report> getReportById(Long id);
    Optional<Report> getReportByDate(LocalDate reportDate);

    //Methods create / update
    Report createOrUpdateReport(Report Report);

    //Methods delete
    void deleteReportById(Long id);
    void deleteAllReports();
}
