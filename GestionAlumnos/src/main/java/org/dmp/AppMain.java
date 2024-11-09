package org.dmp;

import org.dmp.controlador.Controlador;
import org.dmp.modelo.ModeloAlumno;
import org.dmp.servicios.Servicios;
import org.dmp.servicios.ServiciosImpl;
import org.dmp.servicios.ServiciosImplSQL;
import org.dmp.vistas.Ventana;

import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indicar persistencia (F->File; B->Base de datos): ");
        String opcion = scanner.nextLine().trim();

        Servicios servicio = null;
        if (opcion.equals("F")) {
            servicio = new ServiciosImpl(); // Para archivos
        } else if (opcion.equals("B")) {
            servicio = new ServiciosImplSQL(); // Para base de datos
        } else {
            System.out.println("Opción no válida. Usando implementación por defecto.");
            servicio = new ServiciosImpl(); // Asignar un servicio por defecto
        }
        scanner.close();

        //ASIGNO LOS SERVICIOS YA

        ModeloAlumno listaAlumnos = new ModeloAlumno(servicio);
        Ventana vista = new Ventana();
        //ModeloAlumno listaAlumnos = new ModeloAlumno();
        Controlador controlador = new Controlador(vista, listaAlumnos);
    }
}