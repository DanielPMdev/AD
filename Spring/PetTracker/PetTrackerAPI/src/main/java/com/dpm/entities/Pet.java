package com.dpm.entities;

import com.dpm.util.DiseaseListConverter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author danielpm.dev
 */
@Entity
public class Pet {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long Id;

    private String name;
    private String description;
    private Integer age;
    private String species;
    private String breed;
    private char sex;
    private Double weight;

    @Value("${pet.default.image}")
    private String urlImage;

    //LISTA DE IMAGENES - GALERIA

    //LISTA DE ENFERMEDADES (STRING)
    @Convert(converter = DiseaseListConverter.class)
    private List<String> diseaseList;

    //LISTA DE VACUNAS
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    //@JsonIgnoreProperties("pet")
    private List<Vaccine> vaccineList;

    //LISTA DE INFORMES
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    //@JsonIgnoreProperties("pet")
    private List<Report> reportList;

    @OneToMany (mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    //@JsonIgnoreProperties("pet")
    private List<PhysicalExercise> physicalExerciseList;

    //LISTA DE RECETAS
//    @OneToMany(mappedBy = "pet")
//    private List<Prescription> prescriptionList;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonManagedReference
    //@JsonIgnoreProperties("petList")
    private Owner owner;

    public Pet() {
    }

    public Pet(String name, String description, Integer age, String species, String breed, char sex, Double weight, String urlImage, List<String> diseaseList, List<Vaccine> vaccineList, List<Report> reportList, Owner owner) {
        this.name = name;
        this.description = description;
        this.age = age;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
        this.weight = weight;
        this.urlImage = urlImage;
        this.diseaseList = diseaseList;
        this.vaccineList = vaccineList;
        this.reportList = reportList;
        this.owner = owner;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
