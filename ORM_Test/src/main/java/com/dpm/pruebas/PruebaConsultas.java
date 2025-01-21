package com.dpm.pruebas;

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
public class PruebaConsultas {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("upCreate");
        EntityManager em = emf.createEntityManager();

        /*
        Departamento d1 = em.find(Departamento.class, 1);
        System.out.println(d1);

        // 1. Buscar empleados por departamento
        // HQL -> NO SQL -> Se ejecuta sobre las clases y objetos
        String query = "from Empleado e where e.departamento.nombre = 'IT' ";
        List<Empleado> listaEmpleados = em.createQuery(query).getResultList();

        for (Empleado empleado : listaEmpleados) {
            System.out.println(empleado);
        }

        // 2. Buscar un empleado por nombre
        String queryByNombre = "from Empleado e where e.nombre = :nombre";
        TypedQuery<Empleado> typedQuery = em.createQuery(queryByNombre, Empleado.class);
        typedQuery.setParameter("nombre", "Ana Belén");
        Empleado empleadoEncontrado = typedQuery.getSingleResult();

        System.out.println(empleadoEncontrado + " "+
                empleadoEncontrado.getDepartamento().getNombre() + " " +
                empleadoEncontrado.getDireccion().getLocalidad());

        System.out.println("------------------------------------------------");



        // 3. Consultar una lista con nombre del empleado, el nombre
        // del departamento al que pertenece y su localidad
        String consultaEmpleados = "from Empleado e";
        TypedQuery<Empleado> typedQueryEmpleados = em.createQuery(consultaEmpleados, Empleado.class);
        List<Empleado> listaEmpleadosEncontrados = typedQueryEmpleados.getResultList();

        listaEmpleadosEncontrados.forEach(empleado -> {
            System.out.println(empleado.getNombre() + ", " + empleado.getDepartamento().getNombre()
            + ", " + empleado.getDireccion().getLocalidad());
        });



        // 4. Consulta el nombre del departamento y cuantos empleados tiene cada
        String consultaDepartamentos = "from Departamento d";
        TypedQuery<Departamento> typedQueryDepartamentos = em.createQuery(consultaDepartamentos, Departamento.class);
        List<Departamento> listaDepartamentosEncontrados = typedQueryDepartamentos.getResultList();
        listaDepartamentosEncontrados.forEach(departamento -> {
            System.out.println("Departamento: "+ departamento.getNombre() + ", \tNúmero de Empleados: " +
                    departamento.getListaEmpleados().size());
            System.out.println("-----------------------------------------------");
        });


        // 5. Consulta el nombre del departamento y de cada departamento el nombre, sueldo y localidad
        // de sus empleados
        String consultaDepartamentos2 = "from Departamento d";
        TypedQuery<Departamento> typedQueryDepartamentos2 = em.createQuery(consultaDepartamentos2, Departamento.class);
        List<Departamento> listaDepartamentosEncontrados2 = typedQueryDepartamentos2.getResultList();
        listaDepartamentosEncontrados2.forEach(departamento -> {
            System.out.println("Departamento: "+ departamento.getNombre());
            departamento.getListaEmpleados().forEach(empleado ->
                    System.out.println("\tEmpleado: "+empleado.getNombre() + ", "+empleado.getSalario() + "€" +
                            ", " + empleado.getDireccion().getLocalidad()));
            System.out.println("-----------------------------------------------");
        });

        // 6. IDEM desde empleados
        String consulta6 = "from Empleado e";
        TypedQuery<Empleado> typedQuery6 = em.createQuery(consulta6, Empleado.class);
        List<Empleado> listaEmpleados6 = typedQuery6.getResultList();
        listaEmpleados6.forEach(empleado -> {
            System.out.println("Departamento: "+ empleado.getDepartamento().getNombre());
            empleado.getDepartamento().getListaEmpleados().forEach(emp ->
                    System.out.println("\tEmpleado: "+emp.getNombre() + ", "+emp.getSalario() + "€" +
                            ", " + emp.getDireccion().getLocalidad()));
            System.out.println("-----------------------------------------------");
        });

         */

        em.close();
        emf.close();
    }
}
