package com.dpm.dto.physicalexercise;

/**
 * @author danielpm.dev
 */
import com.dpm.entities.PhysicalExercise;

import java.time.LocalDate;

public class FullPhysicalExerciseDTO {
    private Long id;
    private Integer duration;
    private LocalDate exerciseDate;
    private Long petId;

    // Constructor vacío para deserialización
    public FullPhysicalExerciseDTO() {}

    // Constructor para mapear desde la entidad PhysicalExercise
    public FullPhysicalExerciseDTO(PhysicalExercise exercise) {
        this.id = exercise.getId();
        this.duration = exercise.getDuration();
        this.exerciseDate = exercise.getExerciseDate();
        this.petId = exercise.getPet().getId();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(LocalDate exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}
