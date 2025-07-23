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

    // POST /api/historiales
    public void create(Context ctx) {
        try {
            var req = ctx.bodyAsClass(org.devquality.trukea.persistance.entities.HistorialTrueque.class);
            var creado = service.crearDirecto(req);
            ctx.status(HttpStatus.CREATED).json(creado);
        } catch (Exception e) {
            log.error("create historial", e);
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos inválidos"));
        }
    }

    // PUT /api/historiales/{id}
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            var req = ctx.bodyAsClass(org.devquality.trukea.persistance.entities.HistorialTrueque.class);
            var actualizado = service.actualizar(id, req);
            ctx.status(HttpStatus.OK).json(actualizado);
        } catch (Exception e) {
            log.error("update historial", e);
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos o ID inválidos"));
        }
    }

    // DELETE /api/historiales/{id}
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);
            if (ok) ctx.status(HttpStatus.OK).json("Historial eliminado");
            else ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No encontrado"));
        } catch (Exception e) {
            log.error("delete historial", e);
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("ID inválido"));
        }
    }
}
