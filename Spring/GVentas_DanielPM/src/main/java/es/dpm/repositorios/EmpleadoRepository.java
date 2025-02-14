package es.dpm.repositorios;

import es.dpm.dto.MediaSueldoDep;
import es.dpm.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findEmpleadoBySalarioGreaterThan(float filtroSueldo);

    List<Empleado> findEmpleadoBySalarioBetween(float salarioAfter, float salarioBefore);


    //--CONSULTAS DISEÑADAS A PARTIR DE UNA QUERY JPQL-------------------

    //obtener los empleados de salario mayor que una cantidad
    @Query("from Empleado where salario >= :num")
    List<Empleado> empSueldoMayorX(float num);

    //obtener los empleados de salario este comprendido entre dos valores
    @Query ("from Empleado where salario between :ini and :fin")
    List<Empleado> empFiltradoSueldoRango(float ini, float fin);
    //-------------------------------------------------------------

    //--CONSULTAS DISEÑADAS A PARTIR DE UNA QUERY JPQL Y DEVUELVEN UN DTO
    // DTO son las siglas de Data Transfer Object (Objeto de Transferencia de Datos).
    // Es un patrón de diseño utilizado en programación para transferir datos entre
    // diferentes capas de una aplicación, especialmente entre la capa de presentación y la capa de negocio
    // -------------------
    //Obtener el nombre y media de salario de por cada departamento
    @Query("SELECT new es.dpm.dto.MediaSueldoDep(d.nombre, AVG(e.salario)) " +
            "FROM Empleado e JOIN e.departamento d GROUP BY d.nombre")
    List<MediaSueldoDep> obtenerMediaSalarioPorDepartamento();
}
