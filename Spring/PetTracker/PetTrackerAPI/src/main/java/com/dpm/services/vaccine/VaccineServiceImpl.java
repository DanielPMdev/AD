package com.dpm.services.vaccine;

import com.dpm.entities.Vaccine;
import com.dpm.repository.VaccineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class VaccineServiceImpl implements VaccineService {

    private final VaccineRepository vaccineRepository;

    public VaccineServiceImpl(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return vaccineRepository.existsById(id);
    }

    @Override
    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    @Override
    public Optional<Vaccine> getVaccineById(Long id) {
        return vaccineRepository.findById(id);
    }

    @Override
    public Optional<Vaccine> getVaccineByName(String vaccineName) {
        return vaccineRepository.findByVaccineName(vaccineName);
    }

    @Override
    public Vaccine createOrUpdateVaccine(Vaccine Vaccine) {
        vaccineRepository.save(Vaccine);
        return Vaccine;
    }

    @Override
    public void deleteVaccineById(Long id) {
        vaccineRepository.deleteById(id);
    }

    @Override
    public void deleteAllVaccines() {
        vaccineRepository.deleteAll();
    }
}
