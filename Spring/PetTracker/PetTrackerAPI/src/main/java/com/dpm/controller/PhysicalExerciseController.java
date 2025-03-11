package com.dpm.controller;

import com.dpm.dto.physicalexercise.ChangePhysicalExerciseDTO;
import com.dpm.dto.physicalexercise.FullPhysicalExerciseDTO;
import com.dpm.entities.Pet;
import com.dpm.entities.PhysicalExercise;
import com.dpm.services.pet.PetService;
import com.dpm.services.physicalexercise.PhysicalExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author danielpm.dev
 */
@RestController
@RequestMapping("/api")
public class PhysicalExerciseController {

    private final PhysicalExerciseService physicalExerciseService;
    private final PetService petService;

    public PhysicalExerciseController(PhysicalExerciseService physicalExerciseService, PetService petService) {
        this.physicalExerciseService = physicalExerciseService;
        this.petService = petService;
    }

    /*
            GET http://localhost:8080/api/physicalexercises
    */
    @GetMapping("/physicalexercises")
    public ResponseEntity<List<PhysicalExercise>> findAll() {
        List<PhysicalExercise> physicalexerciseList = physicalExerciseService.getAllPhysicalExercises();
        if (physicalexerciseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(physicalexerciseList);
    }

    /*
        GET http://localhost:8080/api/physicalexercise/7
     */
    @GetMapping("/physicalexercise/{id}")
    public ResponseEntity<PhysicalExercise> findAllById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return physicalExerciseService.getPhysicalExerciseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        POST http://localhost:8080/api/physicalexercise
     */
    @PostMapping("/physicalexercise")
    public ResponseEntity<PhysicalExercise> createPhysicalExercise(@RequestBody FullPhysicalExerciseDTO physicalExercise) {
        // Si ya tiene un ID asignado, no permitimos crearlo (esto es correcto para un POST)
        if (physicalExercise.getId() != null)
            return ResponseEntity.badRequest().build();

        // Buscar la mascota por ID
        Pet pet = petService.getPetById(physicalExercise.getPetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));

        PhysicalExercise physicalExerciseCreated = new PhysicalExercise();

        physicalExerciseCreated.setId(physicalExercise.getId());
        physicalExerciseCreated.setDuration(physicalExercise.getDuration());
        physicalExerciseCreated.setExerciseDate(physicalExercise.getExerciseDate());
        physicalExerciseCreated.setPet(pet);

        physicalExerciseService.createOrUpdatePhysicalExercise(physicalExerciseCreated);
        return ResponseEntity.ok(physicalExerciseCreated);
    }

    /*
        PUT http://localhost:8080/api/physicalexercise/3
     */
    @PutMapping("/physicalexercise/{id}")
    public ResponseEntity<PhysicalExercise> updatePhysicalExercise(@PathVariable Long id, @RequestBody ChangePhysicalExerciseDTO physicalexercise) {
        if (!physicalExerciseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener el PhysicalExercise existente desde el servicio para el ID y la Mascota
        PhysicalExercise physicalexerciseToUpdate = physicalExerciseService.getPhysicalExerciseById(id)
                .orElseThrow(() -> new RuntimeException("PhysicalExercise not found"));

        physicalexerciseToUpdate.setExerciseDate(physicalexercise.getExerciseDate());
        physicalexerciseToUpdate.setDuration(physicalexercise.getDuration());

        PhysicalExercise updatedPhysicalExercise = physicalExerciseService.createOrUpdatePhysicalExercise(physicalexerciseToUpdate);
        return ResponseEntity.ok(updatedPhysicalExercise);
    }

    /*
        DELETE http://localhost:8080/api/physicalexercise/{identifier}
     */
    @DeleteMapping("/physicalexercise/{identifier}")
    public ResponseEntity<Void> deletePhysicalExerciseById(@PathVariable("identifier") Long id) {
        if (!physicalExerciseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        physicalExerciseService.deletePhysicalExerciseById(id);
        return ResponseEntity.noContent().build();
    }

}
