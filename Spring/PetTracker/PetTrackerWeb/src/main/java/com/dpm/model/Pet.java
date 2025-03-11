package com.dpm.model;

import java.util.List;

/**
 * @author danielpm.dev
 */
public class Pet {
    private Long id;
    private String name;
    private String description;
    private Integer age;
    private String species;
    private String breed;
    private char sex;
    private Double weight;
    private String urlImage;
    private List<String> diseaseList;
    private List<Vaccine> vaccineList;
    private List<Report> reportList;
    private List<PhysicalExercise> physicalExerciseList;
    private Owner owner;

    public Pet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<String> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(List<String> diseaseList) {
        this.diseaseList = diseaseList;
    }

    public List<Vaccine> getVaccineList() {
        return vaccineList;
    }

    public void setVaccineList(List<Vaccine> vaccineList) {
        this.vaccineList = vaccineList;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public List<PhysicalExercise> getPhysicalExerciseList() {
        return physicalExerciseList;
    }

    public void setPhysicalExerciseList(List<PhysicalExercise> physicalExerciseList) {
        this.physicalExerciseList = physicalExerciseList;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
