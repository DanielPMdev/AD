package com.dpm.controller;

import com.dpm.dto.vaccine.ChangeVaccineDTO;
import com.dpm.dto.vaccine.FullVaccineDTO;
import com.dpm.entities.Pet;
import com.dpm.entities.Vaccine;
import com.dpm.services.pet.PetService;
import com.dpm.services.vaccine.VaccineService;
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
public class VaccineController {

    private final VaccineService vaccineService;
    private final PetService petService;

    public VaccineController(VaccineService vaccineService, PetService petService) {
        this.vaccineService = vaccineService;
        this.petService = petService;
    }

    /*
            GET http://localhost:8080/api/vaccines
    */
    @GetMapping("/vaccines")
    public ResponseEntity<List<Vaccine>> findAll() {
        List<Vaccine> vaccineList = vaccineService.getAllVaccines();
        if (vaccineList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vaccineList);
    }

    /*
        GET http://localhost:8080/api/vaccine/7
     */
    @GetMapping("/vaccine/{id}")
    public ResponseEntity<Vaccine> findAllById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return vaccineService.getVaccineById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        POST http://localhost:8080/api/vaccine
     */
    @PostMapping("/vaccine")
    public ResponseEntity<Vaccine> createVaccine(@RequestBody FullVaccineDTO fullVaccineDTO) {
        // Si ya tiene un ID asignado, no permitimos crearlo (esto es correcto para un POST)
        if (fullVaccineDTO.getId() != null)
            return ResponseEntity.badRequest().build();

        // Buscar la mascota por ID
        Pet pet = petService.getPetById(fullVaccineDTO.getPetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));

        Vaccine vaccineCreated = new Vaccine();

        vaccineCreated.setId(fullVaccineDTO.getId());
        vaccineCreated.setVaccineName(fullVaccineDTO.getVaccineName());
        vaccineCreated.setVaccineDate(fullVaccineDTO.getVaccineDate());
        vaccineCreated.setPet(pet);

        vaccineService.createOrUpdateVaccine(vaccineCreated);
        return ResponseEntity.ok(vaccineCreated);
    }

    /*
        PUT http://localhost:8080/api/vaccine/3
     */
    @PutMapping("/vaccine/{id}")
    public ResponseEntity<Vaccine> updateVaccine(@PathVariable Long id, @RequestBody ChangeVaccineDTO vaccine) {
        if (!vaccineService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener el Vaccine existente desde el servicio para el ID y la Mascota
        Vaccine vaccineToUpdate = vaccineService.getVaccineById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        vaccineToUpdate.setVaccineName(vaccine.getVaccineName());
        vaccineToUpdate.setVaccineDate(vaccine.getVaccineDate());

        Vaccine updatedVaccine = vaccineService.createOrUpdateVaccine(vaccineToUpdate);
        return ResponseEntity.ok(updatedVaccine);
    }

    /*
        DELETE http://localhost:8080/api/vaccine/{identifier}
     */
    @DeleteMapping("/vaccine/{identifier}")
    public ResponseEntity<Void> deleteVaccineById(@PathVariable("identifier") Long id) {
        if (!vaccineService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vaccineService.deleteVaccineById(id);
        return ResponseEntity.noContent().build();
    }

}
