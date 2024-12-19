package com.dpm.repositorio;

public interface SQLiteDAO {

    public boolean crearTablas();
    public boolean eliminarTablas();

    public boolean ejecutarScript();
}
