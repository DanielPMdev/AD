package es.dpm.repositorios;

import es.dpm.dto.ResumenVenta;
import es.dpm.entities.Cliente;
import es.dpm.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {



    //--CONSULTAS DISEÑADAS A PARTIR DE UNA QUERY JPQL-------------------

    @Query("FROM Venta v where v.cliente.nombre = :nombreCliente")
    List<Venta> obtenerVentasByCliente(String nombreCliente);

    @Query("SELECT new es.dpm.dto.ResumenVenta(v.fechaVenta, v.cliente.nombre, " +
            "v.empleado.nombre, v.articulo.nombre, v.articulo.precioCompra, " +
            "CAST(0.21 AS float), CAST(v.articulo.precioCompra * 1.21 AS float)) " +
            "FROM Venta v")
    List<ResumenVenta> obtenerResumenVentas();
}
