package com.dpm.repositorio;

import com.dpm.modelo.Empleado;

import java.util.List;

/**
 * @author danielpm.dev
 */
public interface EmpleadoDAO {

    public boolean addEmpleado(Empleado empleado);

    public boolean deleteEmpleado(Long id);

    public boolean deleteEmpleado(String name);

    public boolean updateEmpleado(Empleado empleado);

    public Empleado getEmpleadoByNombre(String nombre);
    public Empleado getEmpleadoById(Long id);

    public List<Empleado> getAllEmpleados();
}
