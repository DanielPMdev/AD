package es.dpm.service;

import es.dpm.model.Manufacturer;
import es.dpm.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return manufacturerRepository.existsById(id);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public List<Manufacturer> getAllManufacturersByYear(Integer year) {
        Objects.requireNonNull(year);
        return manufacturerRepository.findAllByYear(year);
    }

    @Override
    public Optional<Manufacturer> getManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public Optional<Manufacturer> getManufacturerByName(String name) {
        return manufacturerRepository.findByName(name);
    }

    @Override
    public Manufacturer createOrUpdateManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return manufacturer;
    }

    @Override
    public void deleteManufacturerById(Long id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public void deleteAllManufacturers() {
        manufacturerRepository.deleteAll();
    }
}
