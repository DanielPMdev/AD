package com.dpm.services.owner;

import com.dpm.entities.Owner;
import com.dpm.entities.Pet;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface OwnerService {

    boolean existsById(Long id);

    //Methods retrive
    List<Owner> getAllOwners();

    Optional<Owner> getOwnerById(Long id);
    Optional<Owner> getOwnerByName(String username);

    Optional<List<Pet>> getPetsByOwnerId(Long id);

    //Methods create / update
    Owner createOrUpdateOwner(Owner Owner);

    //Methods delete
    void deleteOwnerById(Long id);
    void deleteAllOwners();
}
