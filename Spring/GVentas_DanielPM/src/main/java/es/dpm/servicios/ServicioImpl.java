package es.dpm.servicios;

import es.dpm.repositorios.DepartamentoRepository;
import es.dpm.repositorios.EmpleadoRepository;
import es.dpm.repositorios.VentaRepository;
import org.springframework.stereotype.Service;

//Spring
@Service
public class ServicioImpl implements IServicio {

    private final DepartamentoRepository depRepository;
    private final EmpleadoRepository empRepository;
    private final VentaRepository ventaRepository;

    public ServicioImpl(DepartamentoRepository depRepository, EmpleadoRepository empRepository,
                        VentaRepository ventaRepository) {
        this.depRepository = depRepository;
        this.empRepository = empRepository;
        this.ventaRepository = ventaRepository;
    }

    @Override
    public void mostrarDepartamentos() {
        depRepository.findAll().forEach(d -> {
            System.out.println(d.getNombre() + " -- " + d.getListaEmpleados().size() + " Empleados");
        });
    }

    @Override
    public void mostrarEmpleadosAgrupadoPorDepartamentos() {
        depRepository.findAll().forEach(d -> {
            System.out.println(d.getNombre() + " -- " + d.getListaEmpleados().size() + " Empleados");
            d.getListaEmpleados().forEach(e -> {
                System.out.println("\t" + e.getNombre() + " -- " + e.getDireccion().getLocalidad());
            });
        });
    }

    @Override
    public void mostrarEmpleados() {
        empRepository.findAll().forEach(e -> {
            System.out.println(e.getNombre() + " -- " +
                    e.getDepartamento().getNombre() + " -- " +
                    e.getDireccion().getLocalidad() + " -- " +
                    e.getVentas().size() + " Ventas realizados");
        });
    }

    @Override
    public void mostarEmpleadosFiltradoPorSueldo(float sueldo) {
        empRepository.findEmpleadoBySalarioGreaterThan(sueldo).forEach(empleado -> {
            System.out.println(empleado.getNombre() + " -- " +
                    empleado.getSalario() + " -- " +
                    empleado.getDireccion().getLocalidad() + " -- " +
                    empleado.getFechaNacimiento() + " -- " +
                    empleado.getDepartamento().getNombre());
        });
    }

    @Override
    public void mostrarEmpleadosConSueldoEnRango(float inicio, float fin) {
        empRepository.findEmpleadoBySalarioBetween(inicio, fin).forEach(empleado -> {
            System.out.println(empleado.getNombre() + " -- " +
                    empleado.getSalario() + " -- " +
                    empleado.getDireccion().getLocalidad() + " -- " +
                    empleado.getFechaNacimiento() + " -- " +
                    empleado.getDepartamento().getNombre());
        });
    }

    @Override
    public void mostrarMediaSueldo() {
        empRepository.obtenerMediaSalarioPorDepartamento().forEach(e->{
            System.out.println(e.getNombreDep()
                    + " -- "+ e.getMediaSalario());
        });
    }

    @Override
    public void mostrarVentasDelCliente (String nombre) {
        ventaRepository.obtenerVentasByCliente(nombre).forEach(venta -> {
            System.out.println("Ventas para el cliente: " + nombre);
            System.out.println("--------------------");
            System.out.println("Fecha: " + venta.getFechaVenta());
            System.out.println("Artículo: " + venta.getArticulo().getNombre());
            System.out.println("Precio: " + venta.getPrecioVenta());
            System.out.println("Empleado: " + venta.getEmpleado().getNombre());
        });
    }
    @Override
    public void mostrarResumenVentas() {
        ventaRepository.obtenerResumenVentas().forEach(resumenVenta -> {
            System.out.println("Fecha Venta: " + resumenVenta.getFechaVenta());
            System.out.println("Cliente: " + resumenVenta.getNombreCliente());
            System.out.println("Empleado: " + resumenVenta.getNombreEmpleado());
            System.out.println("Artículo: " + resumenVenta.getNombreArticulo());
            System.out.println("Precio Compra: " + resumenVenta.getPrecioCompra());
            System.out.println("IVA: " + resumenVenta.getIva());
            System.out.println("Importe Total: " + resumenVenta.getImporteTotal());
            System.out.println("--------------------"); // Separador entre ventas
        });
    }
}
