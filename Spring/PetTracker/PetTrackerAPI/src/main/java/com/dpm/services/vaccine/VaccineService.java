package com.dpm.services.vaccine;

import com.dpm.entities.Vaccine;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface VaccineService {

    boolean existsById(Long id);

    //Methods retrive
    List<Vaccine> getAllVaccines();

    Optional<Vaccine> getVaccineById(Long id);
    Optional<Vaccine> getVaccineByName(String vaccineName);

    //Methods create / update
    Vaccine createOrUpdateVaccine(Vaccine Vaccine);

    //Methods delete
    void deleteVaccineById(Long id);
    void deleteAllVaccines();
}
