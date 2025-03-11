package com.dpm.services.prescription;

import com.dpm.entities.Prescription;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface PrescriptionService {

    boolean existsById(Long id);

    //Methods retrive
    List<Prescription> getAllPrescriptions();

    Optional<Prescription> getPrescriptionById(Long id);
    Optional<Prescription> getPrescriptionByMedName(String medName);

    //Methods create / update
    Prescription createOrUpdatePrescription(Prescription Prescription);

    //Methods delete
    void deletePrescriptionById(Long id);
    void deleteAllPrescriptions();
}
