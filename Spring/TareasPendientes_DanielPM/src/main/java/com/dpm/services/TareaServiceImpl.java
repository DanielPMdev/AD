package com.dpm.services;

import com.dpm.models.Estado;
import com.dpm.models.Tarea;
import com.dpm.repositorys.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Service
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;

    public TareaServiceImpl(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return tareaRepository.existsById(id);
    }

    @Override
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> getTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public Optional<Tarea> getTareaByName(String username) {
        return Optional.empty();
    }

    @Override
    public Tarea createOrUpdateTarea(Tarea Tarea) {
        tareaRepository.save(Tarea);
        return Tarea;
    }

    @Override
    public Tarea changeEstadoTarea(Long id, Estado estado) {
//        tareaRepository.findById(id).ifPresent(tarea -> {
//            tarea.setEstado(estado);
//            tareaRepository.save(tarea);
//            return tarea;
//        });
        return null;
    }

    @Override
    public void deleteTareaById(Long id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public List<Tarea> getTareasByEstado(String estado) {
        return tareaRepository.findAllByEstado(Enum.valueOf(Estado.class, estado));
    }

    @Override
    public List<Tarea> getTareasByPersona(Long personaId) {
        return tareaRepository.findAllByPersona_Id(personaId);
    }

    @Override
    public List<Tarea> getTareasByEstadoAndPersona(String estado, Long personaId) {
        return tareaRepository.findAllByEstadoAndPersona_Id(Enum.valueOf(Estado.class, estado), personaId);
    }
}
