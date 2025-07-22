package org.devquality.trukea.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.devquality.trukea.dtos.request.PublicacionEstastusRequestDTO;
import org.devquality.trukea.services.IPublicacionEstastusService;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PublicacionEstastusRoutes {

    private final IPublicacionEstastusService service;

    public PublicacionEstastusRoutes(IPublicacionEstastusService service) {
        this.service = service;
    }

    public EndpointGroup routes() {
        return () -> {
            path("/api/publicacion-estatus", () -> {
                post(ctx -> {
                    PublicacionEstastusRequestDTO dto = ctx.bodyAsClass(PublicacionEstastusRequestDTO.class);
                    service.registrarCambio(dto);
                    ctx.status(201);
                });
                get("/{id}", ctx -> {
                    Long id = Long.parseLong(ctx.pathParam("id"));
                    ctx.json(service.obtenerHistorial(id));
                });
            });
        };
    }
}
