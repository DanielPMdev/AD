package com.dpm.model;

import java.time.LocalDate;

/**
 * @author danielpm.dev
 */
public class PhysicalExercise {
    private Long id;
    private Integer duration;
    private LocalDate exerciseDate;
    private Pet pet;

    public PhysicalExercise() {
    }

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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
