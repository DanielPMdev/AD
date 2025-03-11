package com.dpm.repository;

import com.dpm.entities.PhysicalExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface PhysicalExerciseRepository extends JpaRepository<PhysicalExercise, Long> {

    Optional<PhysicalExercise> findByExerciseDate(LocalDate date);
}
