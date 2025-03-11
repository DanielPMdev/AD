package com.dpm.services.report;

import com.dpm.entities.Report;
import com.dpm.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return reportRepository.existsById(id);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public Optional<Report> getReportByDate(LocalDate reportDate) {
        return reportRepository.findByReportDate(reportDate);
    }

    @Override
    public Report createOrUpdateReport(Report Report) {
        reportRepository.save(Report);
        return Report;
    }

    @Override
    public void deleteReportById(Long id) {
        reportRepository.deleteById(id);
    }

    @Override
    public void deleteAllReports() {
        reportRepository.deleteAll();
    }
}
