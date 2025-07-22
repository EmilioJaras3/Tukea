package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

public class PublicacionEstastusHistorial {
    private Long id;
    private Long idPublicacion;
    private String status; // Pendiente, Aceptado, Cancelado, Finalizado
    private LocalDateTime fechaModificacion;
    private Long modificadoPor;

    public PublicacionEstastusHistorial() {}

    public PublicacionEstastusHistorial(Long id, Long idPublicacion, String status, LocalDateTime fechaModificacion, Long modificadoPor) {
        this.id = id;
        this.idPublicacion = idPublicacion;
        this.status = status;
        this.fechaModificacion = fechaModificacion;
        this.modificadoPor = modificadoPor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(Long idPublicacion) { this.idPublicacion = idPublicacion; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Long getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Long modificadoPor) { this.modificadoPor = modificadoPor; }
}
