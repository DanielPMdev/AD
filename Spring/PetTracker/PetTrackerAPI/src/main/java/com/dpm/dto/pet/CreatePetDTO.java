package com.dpm.dto.pet;

import com.dpm.entities.Pet;

import java.util.List;

/**
 * @author danielpm.dev
 */
public class CreatePetDTO {
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
    private Long ownerId;

    // Default constructor
    public CreatePetDTO() {}

    public CreatePetDTO(Pet pet){
        this.id = pet.getId();
        this.name = pet.getName();
        this.age = pet.getAge();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.sex = pet.getSex();
        this.weight = pet.getWeight();
        this.diseaseList = pet.getDiseaseList();

        this.ownerId = pet.getOwner().getId();
    }

    // Getters and Setters
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
