package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.ICalificacionesService;
import org.devquality.trukea.web.dtos.calificaciones.request.CreateCalificacionRequest;
import org.devquality.trukea.web.dtos.calificaciones.response.CalificacionResponse;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CalificacionesController {

    private final ICalificacionesService service;
    private static final Logger logger = LoggerFactory.getLogger(CalificacionesController.class);

    public CalificacionesController(ICalificacionesService service) {
        this.service = service;
    }

    // GET /api/calificaciones
    public void getAll(Context ctx) {
        try {
            ArrayList<CalificacionResponse> calificaciones = service.findAll();
            ctx.status(HttpStatus.OK).json(calificaciones);
        } catch (Exception e) {
            logger.error("Error in getAll calificaciones", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // GET /api/calificaciones/{id}
    public void getById(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            CalificacionResponse calificacion = service.findById(id);
            if (calificacion == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Calificación no encontrada"));
                return;
            }

            ctx.status(HttpStatus.OK).json(calificacion);
        } catch (Exception e) {
            logger.error("Error in getById calificaciones", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // GET /api/calificaciones/usuario/{usuarioId}
    public void getByUsuarioCalificado(Context ctx) {
        try {
            String usuarioIdParam = ctx.pathParam("usuarioId");
            if (usuarioIdParam == null || usuarioIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID de usuario es requerido"));
                return;
            }

            Long usuarioId;
            try {
                usuarioId = Long.parseLong(usuarioIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID de usuario debe ser un número válido"));
                return;
            }

            ArrayList<CalificacionResponse> calificaciones = service.findByUsuarioCalificado(usuarioId);
            ctx.status(HttpStatus.OK).json(calificaciones);
        } catch (Exception e) {
            logger.error("Error in getByUsuarioCalificado", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // GET /api/calificaciones/calificador/{usuarioId}
    public void getByUsuario(@NotNull Context ctx) {
        try {
            String usuarioIdParam = ctx.pathParam("usuarioId");
            if (usuarioIdParam == null || usuarioIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del usuario calificador es requerido"));
                return;
            }

            Long usuarioId;
            try {
                usuarioId = Long.parseLong(usuarioIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            ArrayList<CalificacionResponse> calificaciones = service.findByUsuarioCalificador(usuarioId);
            ctx.status(HttpStatus.OK).json(calificaciones);
        } catch (Exception e) {
            logger.error("Error in getByUsuario (calificador)", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // POST /api/calificaciones
    public void create(Context ctx) {
        try {
            CreateCalificacionRequest request = ctx.bodyAsClass(CreateCalificacionRequest.class);
            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de calificación inválidos"));
                return;
            }

            CalificacionResponse calificacionCreada = service.create(request);
            ctx.status(HttpStatus.CREATED).json(calificacionCreada);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in create calificacion", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // DELETE /api/calificaciones/{id}
    public void delete(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            boolean deleted = service.delete(id);
            if (deleted) {
                ctx.status(HttpStatus.NO_CONTENT);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Calificación no encontrada"));
            }
        } catch (Exception e) {
            logger.error("Error in delete calificacion", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }
}
