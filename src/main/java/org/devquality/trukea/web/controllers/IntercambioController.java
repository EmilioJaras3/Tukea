package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.ArrayList;
import org.devquality.trukea.services.IIntercambioService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.devquality.trukea.web.dtos.trueques.request.CreateIntercambioRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateIntercambioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.devquality.trukea.persistance.entities.Intercambio;

public class IntercambioController {
    private final IIntercambioService truequeService;
    private static final Logger logger = LoggerFactory.getLogger(IntercambioController.class);

    public IntercambioController(IIntercambioService truequeService) {
        this.truequeService = truequeService;
    }

    public void getAll(Context ctx) {
        try {
            var intercambios = truequeService.findAll();
            if (intercambios.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron intercambios"));
                return;
            }
            ctx.status(HttpStatus.OK).json(intercambios);
        } catch (Exception e) {
            logger.error("Error en getAll", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }
            Long id = Long.parseLong(idParam);
            var intercambio = truequeService.findById(id);
            if (intercambio == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Intercambio no encontrado con ID: " + id));
                return;
            }
            ctx.status(HttpStatus.OK).json(intercambio);
        } catch (Exception e) {
            logger.error("Error en getById", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            Intercambio intercambio = ctx.bodyAsClass(Intercambio.class);
            if (intercambio == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de intercambio inválidos"));
                return;
            }
            Intercambio creado = truequeService.createIntercambio(intercambio);
            ctx.status(HttpStatus.CREATED).json(creado);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error en create", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void update(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }
            Long id = Long.parseLong(idParam);
            Intercambio intercambio = ctx.bodyAsClass(Intercambio.class);
            if (intercambio == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de intercambio inválidos"));
                return;
            }
            Intercambio actualizado = truequeService.updateIntercambio(id, intercambio);
            ctx.status(HttpStatus.OK).json(actualizado);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error en update", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void delete(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }
            Long id = Long.parseLong(idParam);
            boolean eliminado = truequeService.deleteIntercambio(id);
            if (eliminado) {
                ctx.status(HttpStatus.OK).json("Intercambio eliminado exitosamente");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Intercambio no encontrado"));
            }
        } catch (Exception e) {
            logger.error("Error en delete", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }
}