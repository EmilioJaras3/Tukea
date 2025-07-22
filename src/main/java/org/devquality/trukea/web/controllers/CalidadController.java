package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.ICalidadService;
import org.devquality.trukea.web.dtos.Calidad.request.CreateCalidadRequest;
import org.devquality.trukea.web.dtos.Calidad.response.CalidadResponse;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CalidadController {

    private final ICalidadService service;
    private static final Logger logger = LoggerFactory.getLogger(CalidadController.class);

    public CalidadController(ICalidadService service) {
        this.service = service;
    }

    // GET /api/calidad
    public void getAll(Context ctx) {
        try {
            ArrayList<CalidadResponse> calidades = service.findAll();
            ctx.status(HttpStatus.OK).json(calidades);
        } catch (Exception e) {
            logger.error("Error in getAll calidad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // GET /api/calidad/{id}
    public void getById(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Integer id;
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            CalidadResponse calidad = service.findById(id);
            if (calidad == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Nivel de calidad no encontrado"));
                return;
            }

            ctx.status(HttpStatus.OK).json(calidad);
        } catch (Exception e) {
            logger.error("Error in getById calidad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // POST /api/calidad
    public void create(Context ctx) {
        try {
            CreateCalidadRequest request = ctx.bodyAsClass(CreateCalidadRequest.class);
            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de calidad inválidos"));
                return;
            }

            CalidadResponse calidadCreada = service.create(request);
            ctx.status(HttpStatus.CREATED).json(calidadCreada);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in create calidad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // PUT /api/calidad/{id}
    public void update(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Integer id;
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            CreateCalidadRequest request = ctx.bodyAsClass(CreateCalidadRequest.class);
            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de calidad inválidos"));
                return;
            }

            CalidadResponse calidadActualizada = service.update(id, request);
            if (calidadActualizada == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Nivel de calidad no encontrado"));
                return;
            }

            ctx.status(HttpStatus.OK).json(calidadActualizada);
        } catch (Exception e) {
            logger.error("Error in update calidad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // DELETE /api/calidad/{id}
    public void delete(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Integer id;
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            boolean deleted = service.delete(id);
            if (deleted) {
                ctx.status(HttpStatus.NO_CONTENT);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Nivel de calidad no encontrado"));
            }
        } catch (Exception e) {
            logger.error("Error in delete calidad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }
}