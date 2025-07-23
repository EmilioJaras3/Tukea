package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

public class Intercambio {

    private Long id;
    private Long historialId;
    private Long publicacion1Id;
    private Long publicacion2Id;

    public Intercambio(Long id, Long historialId, Long publicacion1Id, Long publicacion2Id) {
        this.id = id;
        this.historialId = historialId;
        this.publicacion1Id = publicacion1Id;
        this.publicacion2Id = publicacion2Id;
    }

    public Intercambio() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHistorialId() { return historialId; }
    public void setHistorialId(Long historialId) { this.historialId = historialId; }
    public Long getPublicacion1Id() { return publicacion1Id; }
    public void setPublicacion1Id(Long publicacion1Id) { this.publicacion1Id = publicacion1Id; }
    public Long getPublicacion2Id() { return publicacion2Id; }
    public void setPublicacion2Id(Long publicacion2Id) { this.publicacion2Id = publicacion2Id; }
}