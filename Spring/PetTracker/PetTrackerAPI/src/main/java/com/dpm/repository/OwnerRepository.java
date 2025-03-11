package com.dpm.repository;

import com.dpm.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    
    Optional<Owner> findOwnerByUsername(String username);
    
    Optional<Owner> findOwnerByEmail(String email);

}
