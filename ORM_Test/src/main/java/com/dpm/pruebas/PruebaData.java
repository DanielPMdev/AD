package com.dpm.pruebas;

import com.dpm.modelo.Departamento;
import com.dpm.modelo.Direccion;
import com.dpm.modelo.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */
public class PruebaData {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("upUpdate");
        EntityManager em = emf.createEntityManager();


        Departamento d1 = new Departamento(1, "IT", "Toledo");
        Departamento d2 = new Departamento(2 , "Administration", "Ciudad Real");

        Direccion direccion1 = new Direccion(1, "La perdiz, 12", "Alcázar de San Juan",
                "13500", null);
        Direccion direccion2 = new Direccion(2, "Calle Mayor, 5", "Valdepeñas",
                "13300", null);
        Direccion direccion3 = new Direccion(3, "Avenida de la Constitución, 20",
                "Ciudad Real", "13001", null);
        /*
        Direccion direccion4 = new Direccion(4, "Calle del Sol, 15",
                "Puertollano", "13500", null);
        Direccion direccion5 = new Direccion(5, "Carrer de la Pau, 7",
                "Albacete", "02001", null);
        */

        Empleado e1 = new Empleado("Juan Pérez", 2500.75f,
                LocalDate.of(1993, 7, 15), d1, direccion1);
        Empleado e2 = new Empleado("Ana Belén", 1700.24f,
                LocalDate.of(1995, 4, 22), d1, direccion2);
        Empleado e3 = new Empleado("Fran", 1965.33f,
                LocalDate.of(1999, 2, 2), d2, direccion3);

        //PERSISTENCIA
        em.getTransaction().begin();

        //1. Inserción de Departamentos
        em.persist(d1);
        em.persist(d2);

        //em.persist(direccion1);
        //em.persist(direccion2);
        //em.persist(direccion3);

        //1. Inserción de Empleados y en cascada las direcciones
        em.persist(e1);
        em.persist(e2);
        em.persist(e3);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
