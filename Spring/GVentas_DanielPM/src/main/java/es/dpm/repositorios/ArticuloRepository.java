package es.dpm.repositorios;

import es.dpm.entities.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    @Query("SELECT a FROM Articulo a WHERE a.venta IS NULL")
    List<Articulo> findArticulosSinVenta();
}
