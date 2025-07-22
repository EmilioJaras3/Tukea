package org.devquality.trukea.persistance.entities;

public class ZonaSegura {
    private Long idZona;
    private String nombreZona;
    private String direccion;

    public ZonaSegura() {}

    public ZonaSegura(Long idZona, String nombreZona, String direccion) {
        this.idZona = idZona;
        this.nombreZona = nombreZona;
        this.direccion = direccion;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
