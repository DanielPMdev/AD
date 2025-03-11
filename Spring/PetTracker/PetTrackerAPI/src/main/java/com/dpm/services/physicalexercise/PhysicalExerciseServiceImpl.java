package com.dpm.services.physicalexercise;

import com.dpm.entities.PhysicalExercise;
import com.dpm.repository.PhysicalExerciseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class PhysicalExerciseServiceImpl implements PhysicalExerciseService {

    private final PhysicalExerciseRepository physicalExerciseRepository;

    public PhysicalExerciseServiceImpl(PhysicalExerciseRepository physicalExerciseRepository) {
        this.physicalExerciseRepository = physicalExerciseRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return physicalExerciseRepository.existsById(id);
    }

    @Override
    public List<PhysicalExercise> getAllPhysicalExercises() {
        return physicalExerciseRepository.findAll();
    }

    @Override
    public Optional<PhysicalExercise> getPhysicalExerciseById(Long id) {
        return physicalExerciseRepository.findById(id);
    }

    @Override
    public Optional<PhysicalExercise> getPhysicalExerciseByDate(LocalDate date) {
        return physicalExerciseRepository.findByExerciseDate(date);
    }

    @Override
    public PhysicalExercise createOrUpdatePhysicalExercise(PhysicalExercise PhysicalExercise) {
        physicalExerciseRepository.save(PhysicalExercise);
        return PhysicalExercise;
    }

    @Override
    public void deletePhysicalExerciseById(Long id) {
        physicalExerciseRepository.deleteById(id);
    }

    @Override
    public void deleteAllPhysicalExercises() {
        physicalExerciseRepository.deleteAll();
    }
}
