package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.IZonaService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.devquality.trukea.web.dtos.zona.response.ZonaResponseDTO;
import org.devquality.trukea.web.dtos.zona.request.CreateZonaRequest;
import org.devquality.trukea.web.dtos.zona.response.ZonaHorarioResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ZonaController {

    private static final Logger logger = LoggerFactory.getLogger(ZonaController.class);
    private final IZonaService zonaService;

    public ZonaController(IZonaService zonaService) {
        this.zonaService = zonaService;
    }

    // GET /api/zonas
    public void getAll(Context ctx) {
        try {
            ArrayList<ZonaResponseDTO> zonas = zonaService.getAll();
            ctx.status(HttpStatus.OK).json(zonas);
        } catch (Exception e) {
            logger.error("Error al obtener zonas", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // POST /api/zonas
    public void create(Context ctx) {
        try {
            CreateZonaRequest request = ctx.bodyAsClass(CreateZonaRequest.class);
            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos inválidos para crear zona"));
                return;
            }

            ZonaResponseDTO nuevaZona = zonaService.create(request);
            ctx.status(HttpStatus.CREATED).json(nuevaZona);
        } catch (Exception e) {
            logger.error("Error al crear zona", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }

    // GET /api/zonas/{id}/horarios
    public void getHorarios(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            int id = Integer.parseInt(idParam);

            ArrayList<ZonaHorarioResponseDTO> horarios = zonaService.getHorariosByZona(id);
            ctx.status(HttpStatus.OK).json(horarios);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("ID inválido"));
        } catch (Exception e) {
            logger.error("Error al obtener horarios de zona", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor"));
        }
    }
}
