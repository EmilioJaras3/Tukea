package org.devquality.trukea.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.devquality.trukea.web.dtos.zona.request.CreateZonaRequest;
import org.devquality.trukea.services.IZonaService;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ZonaRoutes {

    private final IZonaService service;

    public ZonaRoutes(IZonaService service) {
        this.service = service;
    }

    public EndpointGroup routes() {
        return () -> {
            path("/api/zonas", () -> {
                get(ctx -> ctx.json(service.getAll()));
                post(ctx -> {
                    CreateZonaRequest dto = ctx.bodyAsClass(CreateZonaRequest.class);
                    ctx.json(service.create(dto));
                    ctx.status(201);
                });
                get("/{id}/horarios", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    ctx.json(service.getHorariosByZona(id));
                });
            });
        };
    }
}