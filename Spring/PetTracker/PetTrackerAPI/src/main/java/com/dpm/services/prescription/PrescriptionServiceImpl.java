package com.dpm.services.prescription;

import com.dpm.entities.Prescription;
import com.dpm.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return prescriptionRepository.existsById(id);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public Optional<Prescription> getPrescriptionByMedName(String medName) {
        return prescriptionRepository.findByMedName(medName);
    }

    @Override
    public Prescription createOrUpdatePrescription(Prescription Prescription) {
        prescriptionRepository.save(Prescription);
        return Prescription;
    }

    @Override
    public void deletePrescriptionById(Long id) {
        prescriptionRepository.deleteById(id);
    }

    @Override
    public void deleteAllPrescriptions() {
        prescriptionRepository.deleteAll();
    }
}
