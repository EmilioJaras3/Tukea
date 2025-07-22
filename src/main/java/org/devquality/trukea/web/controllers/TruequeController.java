package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.ArrayList;
import org.devquality.trukea.services.ITruequeService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.devquality.trukea.web.dtos.trueques.request.CreateTruequeRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TruequeController {
    private final ITruequeService truequeService;
    private static final Logger logger = LoggerFactory.getLogger(TruequeController.class);

    public TruequeController(ITruequeService truequeService) {
        this.truequeService = truequeService;
    }

    public void getAll(Context ctx) {
        try {
            ArrayList<CreateTruequeResponse> trueques = truequeService.findAll();
            if (trueques.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron trueques"));
                return;
            }
            ctx.status(HttpStatus.OK).json(trueques);
        } catch (Exception e) {
            logger.error("Error in getAll", e);
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

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            // Cambio aquí: usar findById que retorna entidad, luego convertir
            org.devquality.trukea.persistance.entities.Trueque trueque = truequeService.findById(id);
            if (trueque == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Trueque no encontrado con ID: " + id));
                return;
            }

            // Convertir entidad a DTO
            CreateTruequeResponse response = new CreateTruequeResponse(
                    trueque.getId(),
                    trueque.getProductoOfrecidoId(),
                    trueque.getProductoDeseadoId(),
                    trueque.getEstado(),
                    trueque.getFecha()
            );

            ctx.status(HttpStatus.OK).json(response);
        } catch (Exception e) {
            logger.error("Error in getById", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void getEnviados(Context ctx) {
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

            // Usar método existente - necesitarás agregarlo al service
            ArrayList<CreateTruequeResponse> truequesEnviados = truequeService.findByUsuarioOferente(usuarioId);
            ctx.status(HttpStatus.OK).json(truequesEnviados);
        } catch (Exception e) {
            logger.error("Error in getEnviados", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void getRecibidos(Context ctx) {
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

            // Usar método existente - necesitarás agregarlo al service
            ArrayList<CreateTruequeResponse> truequesRecibidos = truequeService.findByUsuarioReceptor(usuarioId);
            ctx.status(HttpStatus.OK).json(truequesRecibidos);
        } catch (Exception e) {
            logger.error("Error in getRecibidos", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            CreateTruequeRequest request = ctx.bodyAsClass(CreateTruequeRequest.class);
            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Datos de trueque inválidos"));
                return;
            }

            CreateTruequeResponse truequeCreado = truequeService.createTrueque(request);
            ctx.status(HttpStatus.CREATED).json(truequeCreado);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in create", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void aceptar(Context ctx) {
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

            // Necesitarás agregar este método al service
            boolean actualizado = truequeService.aceptarTrueque(id);
            if (actualizado) {
                ctx.status(HttpStatus.OK).json("Trueque aceptado exitosamente");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Trueque no encontrado"));
            }
        } catch (Exception e) {
            logger.error("Error in aceptar", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    public void rechazar(Context ctx) {
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

            // Necesitarás agregar este método al service
            boolean actualizado = truequeService.rechazarTrueque(id);
            if (actualizado) {
                ctx.status(HttpStatus.OK).json("Trueque rechazado exitosamente");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Trueque no encontrado"));
            }
        } catch (Exception e) {
            logger.error("Error in rechazar", e);
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

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            boolean deleted = truequeService.deleteTrueque(id);
            if (deleted) {
                ctx.status(HttpStatus.NO_CONTENT).result("");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Trueque no encontrado"));
            }
        } catch (Exception e) {
            logger.error("Error in delete", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }
}