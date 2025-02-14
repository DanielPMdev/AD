package es.dpm.servicios;

public interface IServicio {

    public void mostrarDepartamentos();

    public void mostrarEmpleadosAgrupadoPorDepartamentos();

    public void mostrarEmpleados();

    public void mostarEmpleadosFiltradoPorSueldo(float sueldo);

    public void mostrarEmpleadosConSueldoEnRango(float inicio, float fin);

    public void mostrarMediaSueldo();

    public void mostrarVentasDelCliente(String nombreCliente);

    public void mostrarResumenVentas();
}
