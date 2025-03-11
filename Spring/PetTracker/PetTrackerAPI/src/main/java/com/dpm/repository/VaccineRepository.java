package com.dpm.repository;

import com.dpm.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    Optional<Vaccine> findByVaccineName(String vaccineName);
}
