package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import org.devquality.trukea.dtos.request.PublicacionEstastusRequestDTO;
import org.devquality.trukea.services.IPublicacionEstastusService;

public class PublicacionEstastusController {

    private final IPublicacionEstastusService service;

    public PublicacionEstastusController(IPublicacionEstastusService service) {
        this.service = service;
    }

    public void registrar(Context ctx) {
        PublicacionEstastusRequestDTO dto = ctx.bodyAsClass(PublicacionEstastusRequestDTO.class);
        service.registrarCambio(dto);
        ctx.status(201);
    }

    public void obtenerHistorial(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        ctx.json(service.obtenerHistorial(id));
    }
}
