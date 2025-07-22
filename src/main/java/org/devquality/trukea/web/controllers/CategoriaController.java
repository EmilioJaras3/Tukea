package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.services.ICategoriaService;
import org.devquality.trukea.web.dtos.categorias.requests.CreateCategoriaRequest;
import org.devquality.trukea.web.dtos.categorias.responses.CreateCategoriaResponse;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CategoriaController {

    private final ICategoriaService categoriaService;
    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // GET /api/categorias
    public void getAllCategorias(Context ctx) {
        try {
            ArrayList<CreateCategoriaResponse> categorias = categoriaService.findAll();
            if (categorias.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(new ErrorResponse("No se encontraron categorías"));
                return;
            }
            ctx.status(HttpStatus.OK).json(categorias);
        } catch (Exception e) {
            logger.error("Error en getAllCategorias", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // POST /api/categorias
    public void createCategoria(Context ctx) {
        try {
            CreateCategoriaRequest request = ctx.bodyAsClass(CreateCategoriaRequest.class);

            if (request == null || request.getCategoria() == null || request.getCategoria().trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("El nombre de la categoría es requerido"));
                return;
            }

            CreateCategoriaResponse categoriaCreada = categoriaService.createCategoria(request);
            ctx.status(HttpStatus.CREATED).json(categoriaCreada);
        } catch (Exception e) {
            logger.error("Error en createCategoria", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/categorias/{id}
    public void getCategoriaById(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            Categoria categoria = categoriaService.findById(id);
            if (categoria == null) {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(new ErrorResponse("Categoría no encontrada con ID: " + id));
                return;
            }

            ctx.status(HttpStatus.OK).json(categoria);
        } catch (Exception e) {
            logger.error("Error en getCategoriaById", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }
}