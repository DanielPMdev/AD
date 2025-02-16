package es.dpm.controller;

import es.dpm.model.Manufacturer;
import es.dpm.service.ManufacturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    /*
        GET http://localhost:8080/api/manufacturers
     */
    @GetMapping("/manufacturers")
    public ResponseEntity<List<Manufacturer>> findAll() {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        if (manufacturers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(manufacturers);
    }

    /*
        GET http://localhost:8080/api/manufacturers/year/1990
     */
    @GetMapping("/manufacturers/year/{year}")
    public ResponseEntity<List<Manufacturer>> findAllByYear(@PathVariable Integer year) {
        if (year < 1885 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturersByYear(year);
        if (manufacturers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(manufacturers);
    }

    /*
        GET http://localhost:8080/api/manufacturers/7
     */
    @GetMapping("/manufacturers/{id}")
    public ResponseEntity<Manufacturer> findAllById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        return manufacturerService.getManufacturerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        POST http://localhost:8080/api/manufacturers
     */
    @PostMapping("/manufacturers")
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer){
        if(manufacturer.getId() != null) //Si ya tiene un id asignado
            return ResponseEntity.badRequest().build();

        manufacturerService.createOrUpdateManufacturer(manufacturer);
        return ResponseEntity.ok(manufacturer);
    }
    /*
        PUT http://localhost:8080/api/manufacturers
     */
    @PutMapping("/manufacturers/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Long id, @RequestBody Manufacturer manufacturer) {
        if (!manufacturerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        manufacturer.setId(id); //Verificar que el Id de la URL es igual al del body
        Manufacturer updatedManufacturer = manufacturerService.createOrUpdateManufacturer(manufacturer);
        return ResponseEntity.ok(updatedManufacturer);
    }

    /*
        DELETE http://localhost:8080/api/manufacturers/{identifier}
     */
    @DeleteMapping("/manufacturers/{identifier}")
    public ResponseEntity<Void> deleteManufacturerById(@PathVariable("identifier") Long id) {
        if (!manufacturerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        manufacturerService.deleteManufacturerById(id);
        return ResponseEntity.noContent().build();
    }

}
