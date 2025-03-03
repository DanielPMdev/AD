package com.dpm.services;

import com.dpm.models.Estado;
import com.dpm.models.Tarea;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface TareaService {

    boolean existsById(Long id);

    //Methods retrive
    List<Tarea> getAllTareas();

    Optional<Tarea> getTareaById(Long id);
    Optional<Tarea> getTareaByName(String username);

    //Methods create / update
    Tarea createOrUpdateTarea(Tarea Tarea);

    Tarea changeEstadoTarea(Long id, Estado estado);

    //Methods delete
    void deleteTareaById(Long id);

    //FILTROS
    List<Tarea> getTareasByEstado(String estado);
    List<Tarea> getTareasByPersona(Long personaId);
    List<Tarea> getTareasByEstadoAndPersona(String estado, Long personaId);
}
