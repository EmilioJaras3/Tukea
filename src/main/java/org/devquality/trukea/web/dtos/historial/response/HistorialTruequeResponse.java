package org.devquality.trukea.web.dtos.historial.response;

import java.time.LocalDateTime;

/** Objeto que el controlador devuelve al frontend */
public class HistorialTruequeResponse {

    private Long id;
    private Long productoOfrecidoId;
    private Long productoDeseadoId;
    private Long usuarioOfrecidoId;
    private Long usuarioRecibidoId;
    private LocalDateTime fecha;

    public HistorialTruequeResponse(Long id,
                                    Long productoOfrecidoId,
                                    Long productoDeseadoId,
                                    Long usuarioOfrecidoId,
                                    Long usuarioRecibidoId,
                                    LocalDateTime fecha) {
        this.id = id;
        this.productoOfrecidoId = productoOfrecidoId;
        this.productoDeseadoId = productoDeseadoId;
        this.usuarioOfrecidoId = usuarioOfrecidoId;
        this.usuarioRecibidoId = usuarioRecibidoId;
        this.fecha = fecha;
    }

    /* Getters y setters */
    // …
}
