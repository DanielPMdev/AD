package com.dpm.repositorio;

import com.dpm.modelo.Departamento;
import com.dpm.modelo.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */
public class DepartamentoDAOImpl implements DepartamentoDAO {

    EntityManagerFactory emf;
    EntityManager em;


    public DepartamentoDAOImpl (String up){
        emf = Persistence.createEntityManagerFactory(up);
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }

    @Override
    public boolean addDepartamento(Departamento departamento) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(departamento);
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
    public boolean deleteDepartamento(Long id) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Departamento departamento = em.find(Departamento.class, id);
            em.remove(departamento);
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
    public boolean deleteDepartamento(String name) {
        em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Departamento departamento = em.find(Departamento.class, name);
            em.remove(departamento);
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
    public boolean updateDepartamento(Departamento departamento) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(departamento); //Hace un persist si no existe y lo actualiza si existe
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public Departamento getDepartamentoByNombre(String nombre) {
        em = emf.createEntityManager();
        String queryByNombre = "from Departamento d where d.nombre = :nombre";
        TypedQuery<Departamento> typedQuery = em.createQuery(queryByNombre, Departamento.class);
        typedQuery.setParameter("nombre", nombre);
        Departamento departamentoEncontrado = typedQuery.getSingleResult();
        em.close();
        return departamentoEncontrado;
    }

    @Override
    public Departamento getDepartamentoById(Long id) {
        em = emf.createEntityManager();
        Departamento newDepartamento = em.find(Departamento.class, id);
        em.close();
        return newDepartamento;
    }

    @Override
    public List<Departamento> getAllDepartamentos() {
        em = emf.createEntityManager();
        List<Departamento> lista = em.createQuery("from Departamento").getResultList();
        em.close();
        return lista;
    }
}
