package es.dpm;

import es.dpm.servicios.IServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
public class GVentasDaniPruebas implements CommandLineRunner {

    private final IServicio servicio;

    public GVentasDaniPruebas(IServicio servicio) {
        this.servicio = servicio;
    }

    public static void main(String[] args) {
        SpringApplication.run(GVentasDaniPruebas.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n *** GVentasDaniPruebas ***");

        System.out.println("\n -- 1. Mostrar todos los Departamentos --");
        servicio.mostrarDepartamentos();

        System.out.println("\n -- 2. Mostrar todos los Departamentos junto con sus Empleados --");
        servicio.mostrarEmpleadosAgrupadoPorDepartamentos();

        System.out.println("\n -- 3. Mostrar todos los Empleados --");
        servicio.mostrarEmpleados();

        System.out.println("\n -- 4. Mostrar Empleados por Sueldo (+1500€) --");
        servicio.mostarEmpleadosFiltradoPorSueldo(1500);

        System.out.println("\n -- 5. Mostrar Empleados por Sueldo en Rango (2700€ - 4000€) --");
        servicio.mostrarEmpleadosConSueldoEnRango(2700, 4000);

        System.out.println("\n -- 6. Sueldo Medio de los Empleados --");
        servicio.mostrarMediaSueldo();

        System.out.println("\n -- 7. Ventas por el Cliente X --");
        servicio.mostrarVentasDelCliente("Ana López");

        System.out.println("\n -- 8. Resumen de las Ventas --");
        servicio.mostrarResumenVentas();

    }
}
