package com.dpm.repository;

import com.dpm.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByName(String name);
}
