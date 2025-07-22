package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.CalificacionesController;

public class CalificacionesRoutes {
    private final CalificacionesController controller;

    public CalificacionesRoutes(CalificacionesController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        // CRUD básico
        app.get("/api/calificaciones", controller::getAll);
        app.get("/api/calificaciones/{id}", controller::getById);
        app.post("/api/calificaciones", controller::create);
        app.delete("/api/calificaciones/{id}", controller::delete);

        // Endpoints específicos
        app.get("/api/calificaciones/usuario/{usuarioId}", controller::getByUsuario);
    }
}