package com.dpm.dto.pet;

import com.dpm.dto.owner.FullOwnerDTO;
import com.dpm.dto.physicalexercise.FullPhysicalExerciseDTO;
import com.dpm.dto.report.FullReportDTO;
import com.dpm.dto.vaccine.FullVaccineDTO;
import com.dpm.entities.Pet;

import java.util.List;

/**
 * @author danielpm.dev
 */
public class FullPetDTO {
    private Long id;
    private String name;
    private String description;
    private Integer age;
    private String species;
    private String breed;
    private char sex;
    private Double weight;

    // Lista de enfermedades como Strings (no necesita DTO propio, es un tipo simple)
    private List<String> diseaseList;

    // Listas de entidades relacionadas, cada una con su DTO
    private List<FullVaccineDTO> vaccineList;
    private List<FullReportDTO> reportList;
    private List<FullPhysicalExerciseDTO> physicalExerciseList;

    // Relación con el Owner, usando su DTO
    private FullOwnerDTO owner;

    // Constructor vacío para deserialización
    public FullPetDTO() {}

    // Constructor para mapear desde la entidad Pet
    public FullPetDTO(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.description = pet.getDescription();
        this.age = pet.getAge();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.sex = pet.getSex();
        this.weight = pet.getWeight();
        this.diseaseList = pet.getDiseaseList();

        // Mapeo de listas
        this.vaccineList = pet.getVaccineList() != null
                ? pet.getVaccineList().stream().map(FullVaccineDTO::new).toList()
                : null;
        this.reportList = pet.getReportList() != null
                ? pet.getReportList().stream().map(FullReportDTO::new).toList()
                : null;
        this.physicalExerciseList = pet.getPhysicalExerciseList() != null
                ? pet.getPhysicalExerciseList().stream().map(FullPhysicalExerciseDTO::new).toList()
                : null;

        // Mapeo del Owner
        this.owner = pet.getOwner() != null ? new FullOwnerDTO(pet.getOwner()) : null;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public char getSex() { return sex; }
    public void setSex(char sex) { this.sex = sex; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    public List<String> getDiseaseList() { return diseaseList; }
    public void setDiseaseList(List<String> diseaseList) { this.diseaseList = diseaseList; }
    public List<FullVaccineDTO> getVaccineList() { return vaccineList; }
    public void setVaccineList(List<FullVaccineDTO> vaccineList) { this.vaccineList = vaccineList; }
    public List<FullReportDTO> getReportList() { return reportList; }
    public void setReportList(List<FullReportDTO> reportList) { this.reportList = reportList; }
    public List<FullPhysicalExerciseDTO> getPhysicalExerciseList() { return physicalExerciseList; }
    public void setPhysicalExerciseList(List<FullPhysicalExerciseDTO> physicalExerciseList) { this.physicalExerciseList = physicalExerciseList; }
    public FullOwnerDTO getOwner() { return owner; }
    public void setOwner(FullOwnerDTO owner) { this.owner = owner; }
}