package com.dpm.repositorio;

import com.dpm.modelo.Direccion;
import com.dpm.modelo.Direccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * @author danielpm.dev
 */
public class DireccionDAOImpl implements DireccionDAO {
    EntityManagerFactory emf;
    EntityManager em;

    public DireccionDAOImpl (String up){
        emf = Persistence.createEntityManagerFactory(up);
    }

    @Override
    public boolean addDireccion(Direccion direccion) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(direccion);
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
    public boolean deleteDireccion(Long id) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Direccion direccion = em.find(Direccion.class, id);
            em.remove(direccion);
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
    public boolean deleteDireccion(String name) {
        em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Direccion direccion = em.find(Direccion.class, name);
            em.remove(direccion);
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
    public boolean updateDireccion(Direccion direccion) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(direccion); //Hace un persist si no existe y lo actualiza si existe
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public Direccion getDireccionByNombre(String direccion) {
        em = emf.createEntityManager();
        String queryByNombre = "from Direccion d where d.direccion = :nombre";
        TypedQuery<Direccion> typedQuery = em.createQuery(queryByNombre, Direccion.class);
        typedQuery.setParameter("nombre", direccion);
        Direccion direccionEncontrado = typedQuery.getSingleResult();
        em.close();
        return direccionEncontrado;
    }

    @Override
    public Direccion getDireccionById(Long id) {
        em = emf.createEntityManager();
        Direccion newDireccion = em.find(Direccion.class, id);
        em.close();
        return newDireccion;
    }

    @Override
    public List<Direccion> getAllDireccions() {
        em = emf.createEntityManager();
        List<Direccion> lista = em.createQuery("from Direccion").getResultList();
        em.close();
        return lista;
    }
}
