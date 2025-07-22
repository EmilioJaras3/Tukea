package org.devquality.trukea.dtos

        ;

public class ZonaRequestDTO {
    public String nombreZona;
    public String direccion;

    public ZonaRequestDTO() {}

    public ZonaRequestDTO(String nombreZona, String direccion) {
        this.nombreZona = nombreZona;
        this.direccion = direccion;
    }

    public boolean isValid() {
        return nombreZona != null && !nombreZona.trim().isEmpty() &&
                direccion != null && !direccion.trim().isEmpty();
    }

    // Getters y setters
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