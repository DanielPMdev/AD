package com.dpm.controller;

import com.dpm.dto.pet.ChangePetDTO;
import com.dpm.dto.pet.CreatePetDTO;
import com.dpm.dto.image.ImageUpdateRequest;
import com.dpm.entities.*;
import com.dpm.services.owner.OwnerService;
import com.dpm.services.pet.PetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author danielpm.dev
 */
@RestController
@RequestMapping("/api")
public class PetController {

    @Value("${pet.default.image}")
    private String defaultImage;

    private final PetService petService;
    private final OwnerService ownerService;

    public PetController(PetService petService, OwnerService ownerService) {
        this.petService = petService;
        this.ownerService = ownerService;
    }

    /*
        GET http://localhost:8080/api/pets
     */
    @GetMapping("/pets")
    public ResponseEntity<List<Pet>> findAll() {
        List<Pet> petList = petService.getAllPets();
        if (petList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(petList);
    }

    /*
        GET http://localhost:8080/api/pet/7
     */
    @GetMapping("/pet/{id}")
    public ResponseEntity<Pet> findAllById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        GET http://localhost:8080/api/pet/7/vaccines
     */
    @GetMapping("/pet/{id}/vaccines")
    public ResponseEntity<List<Vaccine>> findAllVaccines(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return petService.getPetById(id)
                .map(pet -> ResponseEntity.ok(pet.getVaccineList()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        GET http://localhost:8080/api/pet/7/physicalexercise
     */
    @GetMapping("/pet/{id}/physicalexercise")
    public ResponseEntity<List<PhysicalExercise>> findAllPhysicalExercise(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return petService.getPetById(id)
                .map(pet -> ResponseEntity.ok(pet.getPhysicalExerciseList()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        GET http://localhost:8080/api/pet/7/reports
     */
    @GetMapping("/pet/{id}/reports")
    public ResponseEntity<List<Report>> findAllReports(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return petService.getPetById(id)
                .map(pet -> ResponseEntity.ok(pet.getReportList()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        POST http://localhost:8080/api/pet
     */
    @PostMapping("/pet")
    public ResponseEntity<Pet> createPet(@RequestBody CreatePetDTO petDTO) {
        if (petDTO.getId() != null) //Si ya tiene un id asignado
            return ResponseEntity.badRequest().build();

        //Buscar el Owner mediante el ID
        Owner owner = ownerService.getOwnerById(petDTO.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Pet petCreated = new Pet();

        petCreated.setId(petDTO.getId());
        petCreated.setName(petDTO.getName());
        petCreated.setDescription(petDTO.getDescription());
        petCreated.setAge(petDTO.getAge());
        petCreated.setSpecies(petDTO.getSpecies());
        petCreated.setBreed(petDTO.getBreed());
        petCreated.setSex(petDTO.getSex());
        petCreated.setWeight(petDTO.getWeight());
        petCreated.setUrlImage(petDTO.getUrlImage() == null ? defaultImage : petDTO.getUrlImage());
        petCreated.setDiseaseList(petDTO.getDiseaseList());

        petCreated.setOwner(owner);

        petService.createOrUpdatePet(petCreated);
        return ResponseEntity.ok(petCreated);
    }

    /*
        PUT http://localhost:8080/api/pet/3
     */
    @PutMapping("/pet/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody ChangePetDTO petDTO) {
        if (!petService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        //Obtener la Mascota existente mediante el ID
        Pet petToUpdate = petService.getPetById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        petToUpdate.setName(petDTO.getName());
        petToUpdate.setDescription(petDTO.getDescription());
        petToUpdate.setAge(petDTO.getAge());
        petToUpdate.setSpecies(petDTO.getSpecies());
        petToUpdate.setBreed(petDTO.getBreed());
        petToUpdate.setSex(petDTO.getSex());
        petToUpdate.setWeight(petDTO.getWeight());
        petToUpdate.setUrlImage(petDTO.getImageUrl());
        petToUpdate.setDiseaseList(petDTO.getDiseaseList());

        Pet updatedPet = petService.createOrUpdatePet(petToUpdate);
        return ResponseEntity.ok(updatedPet);
    }

    /*
        PUT http://localhost:8080/api/pet/image/3
     */
    @PutMapping("/pet/image/{id}")
    public ResponseEntity<Pet> updateImagePet(@PathVariable Long id, @RequestBody ImageUpdateRequest imgRequest) {
        if (!petService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        //Obtener la Mascota existente mediante el ID
        Pet petToUpdate = petService.getPetById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        if(imgRequest.getImageUrl() != null && !imgRequest.getImageUrl().isEmpty()) {
            petToUpdate.setUrlImage(imgRequest.getImageUrl());

            Pet updatedPet = petService.createOrUpdatePet(petToUpdate);
            return ResponseEntity.ok(updatedPet);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
        DELETE http://localhost:8080/api/pet/{identifier}
     */
    @DeleteMapping("/pet/{identifier}")
    public ResponseEntity<Void> deletePetById(@PathVariable("identifier") Long id) {
        if (!petService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

}
