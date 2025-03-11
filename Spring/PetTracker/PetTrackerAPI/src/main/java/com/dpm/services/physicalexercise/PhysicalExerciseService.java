package com.dpm.services.physicalexercise;

import com.dpm.entities.PhysicalExercise;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface PhysicalExerciseService {

    boolean existsById(Long id);

    //Methods retrive
    List<PhysicalExercise> getAllPhysicalExercises();

    Optional<PhysicalExercise> getPhysicalExerciseById(Long id);
    Optional<PhysicalExercise> getPhysicalExerciseByDate(LocalDate date);

    //Methods create / update
    PhysicalExercise createOrUpdatePhysicalExercise(PhysicalExercise PhysicalExercise);

    //Methods delete
    void deletePhysicalExerciseById(Long id);
    void deleteAllPhysicalExercises();
}
