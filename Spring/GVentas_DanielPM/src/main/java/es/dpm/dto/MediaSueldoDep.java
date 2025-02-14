package es.dpm.dto;

/**
 * @author danielpm.dev
 */
public class MediaSueldoDep {
    private String nombreDep;
    private double mediaSalario;

    public MediaSueldoDep() {
    }

    public MediaSueldoDep(String nombreDep, double mediaSalario) {
        this.nombreDep = nombreDep;
        this.mediaSalario = mediaSalario;
    }

    public String getNombreDep() {
        return nombreDep;
    }

    public void setNombreDep(String nombreDep) {
        this.nombreDep = nombreDep;
    }

    public double getMediaSalario() {
        return mediaSalario;
    }

    public void setMediaSalario(double mediaSalario) {
        this.mediaSalario = mediaSalario;
    }
}
