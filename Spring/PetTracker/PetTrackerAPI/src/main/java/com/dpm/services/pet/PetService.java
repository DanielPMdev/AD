package com.dpm.services.pet;

import com.dpm.entities.Pet;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface PetService {

    boolean existsById(Long id);

    //Methods retrive
    List<Pet> getAllPets();

    Optional<Pet> getPetById(Long id);
    Optional<Pet> getPetByName(String name);

    //Methods create / update
    Pet createOrUpdatePet(Pet Pet);

    //Methods delete
    void deletePetById(Long id);
    void deleteAllPets();
}
