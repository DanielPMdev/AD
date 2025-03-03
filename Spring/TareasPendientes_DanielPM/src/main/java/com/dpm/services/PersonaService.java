package com.dpm.services;

import com.dpm.dto.ResumenPersonas;
import com.dpm.models.Estado;
import com.dpm.models.Persona;
import com.dpm.models.Tarea;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface PersonaService {
    
    boolean existsById(Long id);

    //Methods retrive
    List<Persona> getAllPersonas();

    Optional<Persona> getPersonaById(Long id);

    List<ResumenPersonas> obtenerResumenPersonas();

    Optional<Persona> getPersonaByName(String name);

    //Methods create / update
    Persona createOrUpdatePersona(Persona Persona);
    

    //Methods delete
    void deletePersonaById(Long id);
}
