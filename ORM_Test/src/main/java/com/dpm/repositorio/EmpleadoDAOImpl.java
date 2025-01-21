package com.dpm.repositorio;

import com.dpm.modelo.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * @author danielpm.dev
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    EntityManagerFactory emf;
    EntityManager em;

    public EmpleadoDAOImpl(String up) {
        this.emf = Persistence.createEntityManagerFactory(up);
    }

    @Override
    public boolean addEmpleado(Empleado empleado) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteEmpleado(Long id) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Empleado empleado = em.find(Empleado.class, id);
            em.remove(empleado);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteEmpleado(String name) {
        em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Empleado empleado = em.find(Empleado.class, name);
            em.remove(empleado);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateEmpleado(Empleado empleado) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(empleado); //Hace un persist si no existe y lo actualiza si existe
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public Empleado getEmpleadoByNombre(String nombre) {
        em = emf.createEntityManager();
        String queryByNombre = "from Empleado d where d.nombre = :nombre";
        TypedQuery<Empleado> typedQuery = em.createQuery(queryByNombre, Empleado.class);
        typedQuery.setParameter("nombre", nombre);
        Empleado empleadoEncontrado = typedQuery.getSingleResult();
        em.close();
        return empleadoEncontrado;
    }

    @Override
    public Empleado getEmpleadoById(Long id) {
        em = emf.createEntityManager();
        Empleado newEmpleado = em.find(Empleado.class, id);
        em.close();
        return newEmpleado;
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        em = emf.createEntityManager();
        List<Empleado> lista = em.createQuery("from Empleado").getResultList();
        em.close();
        return lista;
    }
}
