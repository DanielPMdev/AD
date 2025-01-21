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
public class PruebasHelper {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("upCreate");
        EntityManager em = emf.createEntityManager();

        //AÑADIR EMPLEADOS A UN DEPARTAMENTO
        Departamento d1 = em.find(Departamento.class, 1);
        Direccion direccion4 = new Direccion(4, "Calle del Sol, 15",
                "Puertollano", "13500", null);

        Empleado e4 = new Empleado("Paco Gento", 2300.52f,
                LocalDate.of(2003, 5, 23), d1, direccion4);



        em.getTransaction().begin();
        em.persist(e4);
        d1.addEmpleado(e4);
        em.getTransaction().commit();


        em.close();
        emf.close();
    }
}
