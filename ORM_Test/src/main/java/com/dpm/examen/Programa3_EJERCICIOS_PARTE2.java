package com.dpm.examen;

import com.dpm.modelo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Programa3_EJERCICIOS_PARTE2 {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("upCreate");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Crear dos artículos
            Articulo art1 = new Articulo();
            art1.setNombre("Jarrón Chino");
            art1.setPrecioCompra(100.00f);

            Articulo art2 = new Articulo();
            art2.setNombre("Reloj Antiguo");
            art2.setPrecioCompra(200.0f);

            //Recoger el departamento de ventas
            Departamento det1 = em.find(Departamento.class, 3);

            // Crear una dirección
            Direccion direccion = new Direccion("Calle Mayor, 10", "Madrid", "28013", null);

            // Crear un empleado
            Empleado empleado = new Empleado(
                    "Juan Pérez",             // Nombre
                    3000.50f,                 // Salario
                    LocalDate.of(1985, 5, 15), // Fecha de nacimiento
                    det1,             // Departamento
                    direccion                 // Dirección
            );


            // Crear un cliente
            Cliente cliente = new Cliente();
            cliente.setNombre("María López");
            cliente.setTelefono("629912312");

            // Crear dos ventas
            Venta venta1 = new Venta();
            venta1.setArticulo(art1);
            venta1.setEmpleado(empleado);
            venta1.setCliente(cliente);
            venta1.setFechaVenta(LocalDate.now());
            venta1.setPrecioVenta(150.0);

            Venta venta2 = new Venta();
            venta2.setArticulo(art2);
            venta2.setEmpleado(empleado);
            venta2.setCliente(cliente);
            venta2.setFechaVenta(LocalDate.now());
            venta2.setPrecioVenta(250.0);

            // Persistir datos
            em.persist(art1);
            em.persist(art2);
            em.persist(empleado);
            em.persist(cliente);
            em.persist(venta1);
            em.persist(venta2);

            em.getTransaction().commit();

            // Consultar todas las ventas
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT v.idVenta, e.nombre, c.nombre, a.nombre, a.precioCompra, v.precioVenta, " +
                            "(v.precioVenta - a.precioCompra) AS diferencia " +
                            "FROM Venta v JOIN v.empleado e JOIN v.cliente c JOIN v.articulo a", Object[].class);

            List<Object[]> resultados = query.getResultList();

            System.out.println("\n");
            // Mostrar resultados
            for (Object[] fila : resultados) {
                System.out.printf(
                        "ID Venta: %d | Empleado: %s | Cliente: %s | Artículo: %s | Precio Compra: %.2f | Precio Venta: %.2f | Diferencia: %.2f%n",
                        fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6]
                );
            }

        } finally {
            em.close();
            emf.close();
        }
    }
}