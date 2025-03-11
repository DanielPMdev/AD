package com.dpm.controller;

import com.dpm.dto.image.ImageUpdateRequest;
import com.dpm.dto.owner.ChangeOwnerDTO;
import com.dpm.dto.owner.FullOwnerDTO;
import com.dpm.dto.owner.PasswordOwnerDTO;
import com.dpm.entities.Owner;
import com.dpm.entities.Pet;
import com.dpm.services.owner.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author danielpm.dev
 */
@RestController
@RequestMapping("/api")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /*
        GET http://localhost:8080/api/owners
     */
    @GetMapping("/owners")
    public ResponseEntity<List<Owner>> findAll() {
        List<Owner> ownerList = ownerService.getAllOwners();
        if (ownerList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ownerList);
    }

    /*
        GET http://localhost:8080/api/owner/7
     */
    @GetMapping("/owner/{id}")
    public ResponseEntity<Owner> findAllById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return ownerService.getOwnerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        GET http://localhost:8080/api/owner/username/paco
     */
    @GetMapping("/owner/username/{username}")
    public ResponseEntity<Owner> findOwnerByUsername(@PathVariable String username) {
        if (username.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ownerService.getOwnerByName(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
       GET http://localhost:8080/api/owner/7/pets
    */
    @GetMapping("/owner/{id}/pets")
    public ResponseEntity<List<Pet>> findAllPets(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return ownerService.getOwnerById(id)
                .map(owner -> ResponseEntity.ok(owner.getPetList()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        POST http://localhost:8080/api/owner
     */
    @PostMapping("/owner")
    public ResponseEntity<Owner> createOwner(@RequestBody FullOwnerDTO ownerDTO) {
        if (ownerDTO.getId() != null) //Si ya tiene un id asignado
            return ResponseEntity.badRequest().build();

        Owner ownerCreated = new Owner();
        ownerCreated.setId(ownerDTO.getId());
        ownerCreated.setUsername(ownerDTO.getUsername());
        ownerCreated.setPassword(ownerDTO.getPassword());
        ownerCreated.setEmail(ownerDTO.getEmail());

        ownerService.createOrUpdateOwner(ownerCreated);
        return ResponseEntity.ok(ownerCreated);
    }

    /*
        PUT http://localhost:8080/api/owner/3
     */
    @PutMapping("/owner/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @RequestBody ChangeOwnerDTO ownerDTO) {
        if (!ownerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener el Owner existente desde el servicio
        Owner ownerToUpdate = ownerService.getOwnerById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        ownerToUpdate.setEmail(ownerDTO.getEmail());
        ownerToUpdate.setPassword(ownerDTO.getPassword());

        Owner updatedOwner = ownerService.createOrUpdateOwner(ownerToUpdate);
        return ResponseEntity.ok(updatedOwner);
    }

    /*
        PUT http://localhost:8080/api/owner/changePassword/3
     */
    @PutMapping("/owner/changePassword/{id}")
    public ResponseEntity<Owner> updatePasswordOwner(@PathVariable Long id, @RequestBody PasswordOwnerDTO ownerDTO) {
        if (!ownerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener el Owner existente desde el servicio
        Owner ownerToUpdate = ownerService.getOwnerById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        ownerToUpdate.setPassword(ownerDTO.getPassword());

        Owner updatedOwner = ownerService.createOrUpdateOwner(ownerToUpdate);
        return ResponseEntity.ok(updatedOwner);
    }

    /*
        PUT http://localhost:8080/api/owner/image/3
     */
    @PutMapping("/owner/image/{id}")
    public ResponseEntity<Owner> updateImageOwner(@PathVariable Long id, @RequestBody ImageUpdateRequest imgRequest) {
        if (!ownerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        //Obtener el Owner existente mediante el ID
        Owner ownerToUpdate = ownerService.getOwnerById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if(imgRequest.getImageUrl() != null && !imgRequest.getImageUrl().isEmpty()) {
            ownerToUpdate.setUrlImage(imgRequest.getImageUrl());

            Owner updatedOwner = ownerService.createOrUpdateOwner(ownerToUpdate);
            return ResponseEntity.ok(updatedOwner);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
        DELETE http://localhost:8080/api/owner/{identifier}
     */
    @DeleteMapping("/owner/{identifier}")
    public ResponseEntity<Void> deleteOwnerById(@PathVariable("identifier") Long id) {
        if (!ownerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ownerService.deleteOwnerById(id);
        return ResponseEntity.noContent().build();
    }

}