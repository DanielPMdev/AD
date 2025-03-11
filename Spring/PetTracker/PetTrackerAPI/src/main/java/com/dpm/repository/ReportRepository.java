package com.dpm.repository;

import com.dpm.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByReportDate(LocalDate reportDate);
}
