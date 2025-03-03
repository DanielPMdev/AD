package com.dpm.repositorys;

import com.dpm.dto.ResumenPersonas;
import com.dpm.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByNombre(String nombre);

    @Query("SELECT new com.dpm.dto.ResumenPersonas(" +
            "p.nombre, " +
            "COUNT(t), " +
            "SUM(CASE WHEN t.estado = 'PENDIENTE' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN t.estado = 'DESARROLLO' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN t.estado = 'COMPLETADA' THEN 1 ELSE 0 END)) " +
            "FROM Persona p LEFT JOIN p.listaTareas t " +
            "GROUP BY p.id, p.nombre")
    List<ResumenPersonas> findPersonasConTareas();
}
