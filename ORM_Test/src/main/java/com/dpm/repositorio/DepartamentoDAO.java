package com.dpm.repositorio;

import com.dpm.modelo.Departamento;

import java.util.List;

/**
 * @author danielpm.dev
 */
public interface DepartamentoDAO {

    public boolean addDepartamento(Departamento departamento);

    public boolean deleteDepartamento(Long id);

    public boolean deleteDepartamento(String name);

    public boolean updateDepartamento(Departamento departamento);

    public Departamento getDepartamentoByNombre(String nombre);
    public Departamento getDepartamentoById(Long id);

    public List<Departamento> getAllDepartamentos();


}
