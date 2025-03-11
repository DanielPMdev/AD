package com.dpm.dto.physicalexercise;

import com.dpm.entities.PhysicalExercise;

import java.time.LocalDate;

/**
 * @author danielpm.dev
 */
public class ChangePhysicalExerciseDTO {

    private Long id;
    private Integer duration;
    private LocalDate exerciseDate;

    // Constructor vacío para deserialización
    public ChangePhysicalExerciseDTO() {}

    // Constructor para mapear desde la entidad PhysicalExercise
    public ChangePhysicalExerciseDTO(PhysicalExercise exercise) {
        this.id = exercise.getId();
        this.duration = exercise.getDuration();
        this.exerciseDate = exercise.getExerciseDate();
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
}
