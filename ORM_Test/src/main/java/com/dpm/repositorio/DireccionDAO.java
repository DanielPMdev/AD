package com.dpm.repositorio;

import com.dpm.modelo.Direccion;

import java.util.List;

/**
 * @author danielpm.dev
 */
public interface DireccionDAO {

    public boolean addDireccion(Direccion direccion);

    public boolean deleteDireccion(Long id);

    public boolean deleteDireccion(String name);

    public boolean updateDireccion(Direccion direccion);

    public Direccion getDireccionByNombre(String nombre);
    public Direccion getDireccionById(Long id);

    public List<Direccion> getAllDireccions();
}
