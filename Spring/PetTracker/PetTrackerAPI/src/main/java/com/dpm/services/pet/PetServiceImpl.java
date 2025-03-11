package com.dpm.services.pet;

import com.dpm.entities.Pet;
import com.dpm.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return petRepository.existsById(id);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public Optional<Pet> getPetByName(String name) {
        return petRepository.findByName(name);
    }

    @Override
    public Pet createOrUpdatePet(Pet Pet) {
        petRepository.save(Pet);
        return Pet;
    }

    @Override
    public void deletePetById(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public void deleteAllPets() {
        petRepository.deleteAll();
    }
}
