package es.dpm.repository;

import es.dpm.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author danielpm.dev
 */
public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
