package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.IntercambioController;

public class IntercambiosRoutes {
    private final IntercambioController controller;

    public IntercambiosRoutes(IntercambioController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        // CRUD básico
        app.get("/api/intercambios", controller::getAll);
        app.get("/api/intercambios/{id}", controller::getById);
        app.post("/api/intercambios", controller::create);
        app.put("/api/intercambios/{id}", controller::update);
        app.delete("/api/intercambios/{id}", controller::delete);
    }
}