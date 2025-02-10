package es.dpm.modelo;

/**
 * @author danielpm.dev
 */
public enum Equipo {
    REAL_MADRID("Real Madrid"),
    ATLETICO_DE_MADRID("Patetico de Madrid"),
    FC_BARCELONA("FC Barcelona"),
    SEVILLA("Sevilla"),
    VALENCIA("Valencia a Segunda"),
    VILLARREAL("Villarreal");

    private final String nombre;

    Equipo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

