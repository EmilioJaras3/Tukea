package org.devquality.trukea.web.dtos.calificaciones.request;

public class CreateCalificacionRequest {
    private Long usuarioCalificadorId;
    private Long usuarioCalificadoId;
    private Integer puntuacion;
    private String comentario;

    public CreateCalificacionRequest() {}

    public CreateCalificacionRequest(Long usuarioCalificadorId, Long usuarioCalificadoId,
                                     Integer puntuacion, String comentario) {
        this.usuarioCalificadorId = usuarioCalificadorId;
        this.usuarioCalificadoId = usuarioCalificadoId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public boolean isValid() {
        return usuarioCalificadorId != null && usuarioCalificadorId > 0 &&
                usuarioCalificadoId != null && usuarioCalificadoId > 0 &&
                puntuacion != null && puntuacion >= 1 && puntuacion <= 5 &&
                !usuarioCalificadorId.equals(usuarioCalificadoId);
    }

    // Getters y setters
    public Long getUsuarioCalificadorId() {
        return usuarioCalificadorId;
    }

    public void setUsuarioCalificadorId(Long usuarioCalificadorId) {
        this.usuarioCalificadorId = usuarioCalificadorId;
    }

    public Long getUsuarioCalificadoId() {
        return usuarioCalificadoId;
    }

    public void setUsuarioCalificadoId(Long usuarioCalificadoId) {
        this.usuarioCalificadoId = usuarioCalificadoId;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}