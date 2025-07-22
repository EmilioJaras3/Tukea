package org.devquality.trukea.web.dtos.zona.response;

public class ZonaResponseDTO {
    public Long idZona;
    public String nombreZona;
    public String direccion;

    public ZonaResponseDTO() {}

    public ZonaResponseDTO(Long idZona, String nombreZona, String direccion) {
        this.idZona = idZona;
        this.nombreZona = nombreZona;
        this.direccion = direccion;
    }

    // Getters y setters
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