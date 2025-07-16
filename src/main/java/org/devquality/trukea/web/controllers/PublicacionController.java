package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.IPublicacionService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.devquality.trukea.web.dtos.publicaciones.request.CreatePublicacionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicacionController {

    private final IPublicacionService service;
    private static final Logger log = LoggerFactory.getLogger(PublicacionController.class);

    public PublicacionController(IPublicacionService service) { this.service = service; }

    // GET /api/publicaciones
    public void getAll(Context ctx) {
        ctx.json(service.findAll()).status(HttpStatus.OK);
    }

    // GET /api/publicaciones/{id}
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            var pub = service.findById(id);
            if (pub == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No encontrada"));
            } else {
                ctx.json(pub).status(HttpStatus.OK);
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("ID inválido"));
        }
    }

    // GET /api/publicaciones/usuario/{uid}
    public void getByUsuario(Context ctx) {
        try {
            Long uid = Long.parseLong(ctx.pathParam("uid"));
            var list = service.findByUsuario(uid);
            ctx.json(list).status(HttpStatus.OK);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("ID inválido"));
        }
    }

    // POST /api/publicaciones
    public void create(Context ctx) {
        try {
            var req = ctx.bodyAsClass(CreatePublicacionRequest.class);
            var resp = service.create(req);
            ctx.json(resp).status(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("create publicacion", e);
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos inválidos"));
        }
    }

    // DELETE /api/publicaciones/{id}
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            boolean ok = service.delete(id);
            if (!ok) ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No encontrada"));
            else ctx.status(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("ID inválido"));
        }
    }
}

