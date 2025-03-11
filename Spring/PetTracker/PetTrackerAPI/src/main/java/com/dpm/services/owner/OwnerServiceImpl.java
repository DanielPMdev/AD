package com.dpm.services.owner;

import com.dpm.entities.Owner;
import com.dpm.entities.Pet;
import com.dpm.entities.Role;
import com.dpm.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class OwnerServiceImpl implements OwnerService {

    @Value("${owner.default.image}")
    private String defaultImage;

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerServiceImpl(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean existsById(Long id) {
        return ownerRepository.existsById(id);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Optional<Owner> getOwnerByName(String username) {
        return ownerRepository.findOwnerByUsername(username);
    }

    @Override
    public Optional<List<Pet>> getPetsByOwnerId(Long id) {
        return Optional.empty();
    }

    @Override
    public Owner createOrUpdateOwner(Owner owner) {
        if (owner.getId() == null) {
            // Caso de creación
            Owner ownerToSave = new Owner(
                    owner.getUsername(),
                    passwordEncoder.encode(owner.getPassword()), // Codifica la contraseña
                    owner.getEmail(),
                    owner.getPetList(),
                    Role.USER);

            if (ownerToSave.getUrlImage() == null) {
                ownerToSave.setUrlImage(defaultImage);
            }

            ownerRepository.save(ownerToSave);
            return ownerToSave;
        } else {
            // Caso de actualización
            Optional<Owner> existingOwnerOpt = ownerRepository.findById(owner.getId());
            if (existingOwnerOpt.isPresent()) {
                Owner existingOwner = existingOwnerOpt.get();

                // Verifica si la contraseña ha cambiado
                String newPassword = owner.getPassword();
                String currentEncodedPassword = existingOwner.getPassword();

                // Si la nueva contraseña es diferente a la almacenada, codifícala
                if (newPassword != null && !passwordEncoder.matches(newPassword, currentEncodedPassword)) {
                    owner.setPassword(passwordEncoder.encode(newPassword));
                } else {
                    // Si no cambió, usa la contraseña ya codificada que está en la BD
                    owner.setPassword(currentEncodedPassword);
                }

                ownerRepository.save(owner);
                return owner;
            } else {
                throw new RuntimeException("Owner con ID " + owner.getId() + " no encontrado");
            }
        }
    }

    @Override
    public void deleteOwnerById(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public void deleteAllOwners() {
        ownerRepository.deleteAll();
    }
}
