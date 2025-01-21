package com.dpm.pruebas;

import com.dpm.modelo.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */
public class PruebaCRUD {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("upCreate");
        EntityManager em = emf.createEntityManager();


        // 1. Borrar un objeto
        //Cuando manipulemos datos, en una transaccion
        String queryBorrarByNombre = "from Empleado e where e.nombre = :nombre";
        TypedQuery<Empleado> typedQueryBorrar = em.createQuery(queryBorrarByNombre, Empleado.class);
        typedQueryBorrar.setParameter("nombre", "Fran");
        Empleado empleadoBorrado = typedQueryBorrar.getSingleResult();

        if (empleadoBorrado != null) {
            em.getTransaction().begin();
            em.remove(empleadoBorrado);
            em.getTransaction().commit();
        } else {
            System.out.println("No se puede borrar el empleado");
        }

        // 2. Modificar un objeto
        String querryModificarByNombre = "from Empleado e where e.nombre = :nombre";
        TypedQuery<Empleado> typedQueryModificar = em.createQuery(querryModificarByNombre, Empleado.class);
        typedQueryModificar.setParameter("nombre", "Juan Pérez");
        Empleado empleadoModificado = typedQueryModificar.getSingleResult();

        if (empleadoModificado != null) {
            empleadoModificado.setNombre("Juan Nuevo");
            System.out.println(empleadoModificado);
            em.getTransaction().begin();
            //em.merge(empleadoModificado);
            em.getTransaction().commit();
        }

        em.close();
        emf.close();
    }
}
