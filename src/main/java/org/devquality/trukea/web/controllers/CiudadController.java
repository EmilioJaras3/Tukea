package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.ICiudadService;
import org.devquality.trukea.web.dtos.ciudades.request.CreateCiudadRequest;
import org.devquality.trukea.web.dtos.ciudades.response.CiudadResponse;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CiudadController {

    private final ICiudadService service;
    private static final Logger logger = LoggerFactory.getLogger(CiudadController.class);

    public CiudadController(ICiudadService service) {
        this.service = service;
    }

    // GET /api/ciudades
    public void getAll(Context ctx) {
        try {
            ArrayList<CiudadResponse> ciudades = service.findAll();
            ctx.status(HttpStatus.OK).json(ciudades);
        } catch (Exception e) {
            logger.error("Error in getAll ciudades", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // GET /api/ciudades/{id}
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

            CiudadResponse ciudad = service.findById(id);
            if (ciudad == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Ciudad no encontrada"));
                return;
            }

            ctx.status(HttpStatus.OK).json(ciudad);
        } catch (Exception e) {
            logger.error("Error in getById ciudad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // POST /api/ciudades
    public void create(Context ctx) {
        try {
            CreateCiudadRequest request = ctx.bodyAsClass(CreateCiudadRequest.class);
            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de ciudad inválidos"));
                return;
            }

            CiudadResponse ciudadCreada = service.create(request);
            ctx.status(HttpStatus.CREATED).json(ciudadCreada);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in create ciudad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // PUT /api/ciudades/{id}
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

            CreateCiudadRequest request = ctx.bodyAsClass(CreateCiudadRequest.class);
            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de ciudad inválidos"));
                return;
            }

            CiudadResponse ciudadActualizada = service.update(id, request);
            if (ciudadActualizada == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Ciudad no encontrada"));
                return;
            }

            ctx.status(HttpStatus.OK).json(ciudadActualizada);
        } catch (Exception e) {
            logger.error("Error in update ciudad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // DELETE /api/ciudades/{id}
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
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Ciudad no encontrada"));
            }
        } catch (Exception e) {
            logger.error("Error in delete ciudad", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }
}