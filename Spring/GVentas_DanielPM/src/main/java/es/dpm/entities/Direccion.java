package es.dpm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author danielpm.dev
 */

//---------  JPA  ----------------
@Embeddable
public class Direccion {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
    private String direccion;
    private String localidad;
    @Column(name = "codigo_postal")
    private String cp;

    public Direccion() {
    }

    public Direccion(String direccion, String localidad, String cp) {
        this.direccion = direccion;
        this.localidad = localidad;
        this.cp = cp;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "direccion='" + direccion + '\'' +
                ", localidad='" + localidad + '\'' +
                ", cp='" + cp + '\'' +
                '}';
    }
}
