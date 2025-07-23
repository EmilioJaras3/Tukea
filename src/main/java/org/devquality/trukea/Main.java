package org.devquality.trukea;

import io.javalin.Javalin;
import org.devquality.trukea.persistance.repositories.impl.ProductoRepositoryImpl;
import org.devquality.trukea.services.impl.ProductoServiceImpl;
import org.devquality.trukea.web.controllers.ProductoController;

/**
 * Prueba de depuración atómica. Todo se crea y se registra aquí
 * para eliminar CUALQUIER otra posible fuente de error.
 */
public class Main {

    public static void main(String[] args) {

        // El try-catch definitivo para CUALQUIER error de arranque.
        try {
            System.out.println("=================================================");
            System.out.println("--- INICIANDO PRUEBA DE ARRANQUE DIRECTO ---");
            System.out.println("=================================================");

            // --- PASO 1: Creando el Repositorio de Productos ---
            System.out.println("--> PASO 1: Creando ProductoRepositoryImpl...");
            ProductoRepositoryImpl productoRepository = new ProductoRepositoryImpl();
            System.out.println("    ...Éxito: ProductoRepositoryImpl creado.");

            // --- PASO 2: Creando el Servicio de Productos ---
            System.out.println("--> PASO 2: Creando ProductoServiceImpl...");
            ProductoServiceImpl productoService = new ProductoServiceImpl(productoRepository);
            System.out.println("    ...Éxito: ProductoServiceImpl creado.");

            // --- PASO 3: Creando el Controlador de Productos ---
            System.out.println("--> PASO 3: Creando ProductoController...");
            ProductoController productoController = new ProductoController(productoService);
            System.out.println("    ...Éxito: ProductoController creado.");

            // --- PASO 4: Creando la aplicación Javalin ---
            System.out.println("--> PASO 4: Creando instancia de Javalin...");
            Javalin app = Javalin.create(config -> {
                config.bundledPlugins.enableCors(cors -> cors.addRule(it -> it.anyHost()));
            });
            System.out.println("    ...Éxito: Instancia de Javalin creada.");

            // --- PASO 5: REGISTRANDO LOS DOS ENDPOINTS DIRECTAMENTE ---
            // Si el programa llega hasta aquí, estas rutas TIENEN que registrarse.
            System.out.println("--> PASO 5.1: Registrando GET /api/productos...");
            app.get("/api/productos", productoController::getAllProductos);
            System.out.println("    ...Éxito: Endpoint GET registrado.");

            System.out.println("--> PASO 5.2: Registrando POST /api/productos...");
            app.post("/api/productos", productoController::createProducto);
            System.out.println("    ...Éxito: Endpoint POST registrado.");

            // --- PASO FINAL: Arrancando el servidor ---
            int port = 8082;
            app.start(port);

            System.out.println("\n=======================================================");
            System.out.println("---     SERVIDOR INICIADO EN EL PUERTO " + port + "    ---");
            System.out.println("---       PRUEBA EN POSTMAN AHORA       ---");
            System.out.println("=======================================================");

        } catch (Throwable t) {
            // Si CUALQUIER cosa falla, lo veremos aquí.
            System.err.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.println("!!!   FALLO CATASTRÓFICO DURANTE EL ARRANQUE   !!!");
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            t.printStackTrace();
        }
    }
}