package es.dpm.service;

import es.dpm.model.Manufacturer;
import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    boolean existsById(Long id);

    //Methods retrive
    List<Manufacturer> getAllManufacturers();
    List<Manufacturer> getAllManufacturersByYear(Integer year);

    Optional<Manufacturer> getManufacturerById(Long id);
    Optional<Manufacturer> getManufacturerByName(String name);

    //Methods create / update
    Manufacturer createOrUpdateManufacturer(Manufacturer manufacturer);

    //Methods delete
    void deleteManufacturerById(Long id);
    void deleteAllManufacturers();

    //More business logic
    //1. Cars manufactured
    //2. Benefits obtained


}
