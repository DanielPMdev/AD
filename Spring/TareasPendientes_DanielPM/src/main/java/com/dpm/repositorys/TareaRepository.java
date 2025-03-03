package com.dpm.repositorys;

import com.dpm.models.Estado;
import com.dpm.models.Persona;
import com.dpm.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author danielpm.dev
 */
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    List<Tarea> findAllByEstado(Estado estado);
    
    List<Tarea> findAllByPersona_Id(Long personaId);

    List<Tarea> findAllByEstadoAndPersona_Id(Estado estado, Long personaId);
}
