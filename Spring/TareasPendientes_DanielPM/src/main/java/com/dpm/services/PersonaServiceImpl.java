package com.dpm.services;

import com.dpm.dto.ResumenPersonas;
import com.dpm.models.Persona;
import com.dpm.repositorys.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return personaRepository.existsById(id);
    }

    @Override
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Optional<Persona> getPersonaById(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    public List<ResumenPersonas> obtenerResumenPersonas() {
        return personaRepository.findPersonasConTareas();
    }

    @Override
    public Optional<Persona> getPersonaByName(String name) {
        return personaRepository.findByNombre(name);
    }

    @Override
    public Persona createOrUpdatePersona(Persona Persona) {
        personaRepository.save(Persona);
        return Persona;
    }

    @Override
    public void deletePersonaById(Long id) {
        personaRepository.deleteById(id);
    }
}
