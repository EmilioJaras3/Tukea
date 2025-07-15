package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.impl.CloudinaryService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ImagenController {

    private final CloudinaryService cloudinaryService;
    private static final Logger logger = LoggerFactory.getLogger(ImagenController.class);

    public ImagenController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    // POST /api/imagenes
    public void subirImagen(Context ctx) {
        try {
            UploadedFile file = ctx.uploadedFile("imagen");
            if (file == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("No se proporcionó ninguna imagen"));
                return;
            }

            try (InputStream inputStream = file.content()) {
                String url = cloudinaryService.subirImagen(inputStream);
                ctx.status(HttpStatus.CREATED).json(url);
            }
        } catch (Exception e) {
            logger.error("Error al subir imagen", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error al subir la imagen: " + e.getMessage()));
        }
    }
}
