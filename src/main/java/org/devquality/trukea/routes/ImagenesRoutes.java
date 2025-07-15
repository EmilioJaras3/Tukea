package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.ImagenController;

public class ImagenesRoutes {

    private final ImagenController controller;

    public ImagenesRoutes(ImagenController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        app.post("/api/imagenes", controller::subirImagen);
    }
}
