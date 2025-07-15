package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.IHistorialTruequeService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistorialTruequeController {

    private final IHistorialTruequeService service;
    private static final Logger log = LoggerFactory.getLogger(HistorialTruequeController.class);

    public HistorialTruequeController(IHistorialTruequeService service) {
        this.service = service;
    }

    // GET /api/historiales
    public void getAll(Context ctx) {
        ctx.json(service.findAll()).status(HttpStatus.OK);
    }

    // GET /api/historiales/{id}
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            var h = service.findById(id);
            if (h == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Historial no encontrado"));
                return;
            }
            ctx.json(h).status(HttpStatus.OK);
        } catch (Exception e) {
            log.error("getById historial", e);
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("ID inválido"));
        }
    }

    // GET /api/historiales/usuario/{uid}
    public void getByUsuario(Context ctx) {
        try {
            Long uid = Long.parseLong(ctx.pathParam("uid"));
            var list = service.findByUsuarioId(uid);
            if (list.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Sin historiales"));
                return;
            }
            ctx.json(list).status(HttpStatus.OK);
        } catch (Exception e) {
            log.error("getByUsuario historial", e);
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("ID inválido"));
        }
    }
}
