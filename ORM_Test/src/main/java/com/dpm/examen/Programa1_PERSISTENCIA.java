package com.dpm.examen;

import com.dpm.modelo.Departamento;
import com.dpm.modelo.Direccion;
import com.dpm.modelo.Empleado;
import com.dpm.repositorio.DepartamentoDAO;
import com.dpm.repositorio.DepartamentoDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */
public class Programa1_PERSISTENCIA {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("upUpdate");
        EntityManager em = emf.createEntityManager();

        Departamento d1 = new Departamento("IT", "Albacete");
        Departamento d2 = new Departamento("Administration", "Ciudad Real");

        Direccion direccion1 = new Direccion("La perdiz, 12", "Alcázar de San Juan",
                "13500", null);
        Direccion direccion2 = new Direccion("Calle Mayor, 5", "Valdepeñas",
                "13300", null);
        Direccion direccion3 = new Direccion("Avenida de la Constitución, 20",
                "Ciudad Real", "13001", null);
        Direccion direccion4 = new Direccion( "Calle del Sol, 15",
                "Puertollano", "13500", null);
        Direccion direccion5 = new Direccion( "Carrer de la Pau, 7",
                "Albacete", "02001", null);

        Empleado e1 = new Empleado("Juan Pérez", 2500.75f,
                LocalDate.of(1993, 7, 15), d1, direccion1);
        Empleado e2 = new Empleado("Ana Belén", 1700.24f,
                LocalDate.of(1995, 4, 22), d1, direccion2);
        Empleado e3 = new Empleado("Fran", 1965.33f,
                LocalDate.of(1999, 2, 2), d2, direccion3);
        Empleado e4 = new Empleado("Paco", 1415.43f,
                LocalDate.of(2004, 11, 8), d2, direccion4);
        Empleado e5 = new Empleado("Javier", 4412.53f,
                LocalDate.of(2001, 2, 7), d2, direccion5);

        //PERSISTENCIA
        em.getTransaction().begin();

        //1. Inserción de Departamentos
        d1.addEmpleado(e1);
        d1.addEmpleado(e2);
        d2.addEmpleado(e3);
        d2.addEmpleado(e4);
        d2.addEmpleado(e5);

        em.persist(d1);
        em.persist(d2);

        Departamento d3 = new Departamento("Ventas", "Toledo");
        Direccion direccion6 = new Direccion( "Calle Hornos, 14",
                "Tresjuncos", "16422", null);
        Direccion direccion7 = new Direccion( "Calle Hiedra, 7",
                "Tresjuncos", "16422", null);
        Empleado e6 = new Empleado("Daniel Porras", 7425.33f,
                LocalDate.of(2001, 2, 7), d3, direccion6);
        Empleado e7 = new Empleado("Luis", 2112.76f,
                LocalDate.of(1966, 11, 21), d3, direccion7);

        em.persist(d3);
        d3.addEmpleado(e6);
        d3.addEmpleado(e7);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
