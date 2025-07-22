package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.TruequeController;

public class TruequesRoutes {
    private final TruequeController controller;

    public TruequesRoutes(TruequeController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        // CRUD básico
        app.get("/api/trueques", controller::getAll);
        app.get("/api/trueques/{id}", controller::getById);
        app.post("/api/trueques", controller::create);
        app.delete("/api/trueques/{id}", controller::delete);

        // Endpoints específicos de trueque
        app.get("/api/trueques/enviados/{usuarioId}", controller::getEnviados);
        app.get("/api/trueques/recibidos/{usuarioId}", controller::getRecibidos);
        app.put("/api/trueques/{id}/aceptar", controller::aceptar);
        app.put("/api/trueques/{id}/rechazar", controller::rechazar);
    }
}