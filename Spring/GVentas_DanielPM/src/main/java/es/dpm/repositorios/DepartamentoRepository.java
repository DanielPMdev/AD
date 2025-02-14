package es.dpm.repositorios;

import es.dpm.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

}
