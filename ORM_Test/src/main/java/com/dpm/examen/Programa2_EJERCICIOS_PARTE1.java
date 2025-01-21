package com.dpm.examen;

import com.dpm.modelo.Departamento;
import com.dpm.modelo.Direccion;
import com.dpm.modelo.Empleado;
import com.dpm.repositorio.DepartamentoDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */
public class Programa2_EJERCICIOS_PARTE1 {

    static DepartamentoDAOImpl departamentoDAO = new DepartamentoDAOImpl("upUpdate");

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("upUpdate");
        EntityManager em = emf.createEntityManager();

        //PERSISTENCIA
        em.getTransaction().begin();

        //EJERCICIO A
        Departamento ventas = departamentoDAO.getDepartamentoByNombre("Ventas");
        Direccion direccion = new Direccion("Calle Agua, 4", "Osa de la Vega", "16423", null);
        Empleado empleado = new Empleado("Sofia", 1955.12f,
                LocalDate.of(2003, 2, 17), ventas, direccion);

        //em.persist(empleado);
        ventas.addEmpleado(empleado);
        em.merge(ventas);



        //EJERCICIO B
        Departamento ventas2 = departamentoDAO.getDepartamentoByNombre("Ventas");
        ventas2.setLocalidad("Albacete");
        em.merge(ventas2);



        //EJERCICIO C
        Departamento departamentoObtenido = departamentoDAO.getDepartamentoByNombre("Ventas");
        List<Empleado> listaEmpleados = departamentoObtenido.getListaEmpleados();
        listaEmpleados.forEach(e -> {
            System.out.println("Nombre: " + e.getNombre());
            System.out.println("Salario Anterior: " + e.getSalario());
            e.setSalario((e.getSalario()*1.10f));
            System.out.println("Salario Actual: " + e.getSalario());
        });

        em.merge(departamentoObtenido); //GUARDAMOS LOS DATOS CON EL SALARIO AUMENTADO


        System.out.println("-------------------------------------");
        System.out.println("\n");

        //EJERCICIO D
        List<Departamento> listaDepartamentosEncontrados = departamentoDAO.getAllDepartamentos();
        listaDepartamentosEncontrados.forEach(departamento -> {
            System.out.println("Departamento: "+ departamento.getNombre() + ", " + departamento.getLocalidad() + " -- " +
                    departamento.getListaEmpleados().size() + " empleados");
            departamento.getListaEmpleados().forEach(emp ->
                    System.out.println("\tEmpleado "+ emp.getId() + " : "+emp.getNombre() + ", "+emp.getSalario() + "€" +
                            ", " + emp.getDireccion().getLocalidad()));
            System.out.println("-----------------------------------------------");
        });

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
