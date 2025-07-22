package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.repositories.impl.*;
import org.devquality.trukea.services.impl.*;
import org.devquality.trukea.web.controllers.*;

public class Routes {

    private final DatabaseConfig databaseConfig;

    public Routes(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    // MÉTODO PRINCIPAL que inicializa TODOS los controllers y endpoints disponibles
    public void routes(Javalin app) {

        // === INICIALIZAR REPOSITORIES (solo los que existen) ===
        UsuarioRepositoryImpl usuarioRepository = new UsuarioRepositoryImpl(databaseConfig);
        CategoriaRepositoryImpl categoriaRepository = new CategoriaRepositoryImpl(databaseConfig);
        ProductoRepositoryImpl productoRepository = new ProductoRepositoryImpl(databaseConfig);
        CalificacionesRepositoryImpl calificacionesRepository = new CalificacionesRepositoryImpl(databaseConfig);

        // CAMBIO CRÍTICO: Usar TruequeRepositoryImpl (no HistorialTruequeRepositoryImpl)
        TruequeRepositoryImpl truequeRepository = new TruequeRepositoryImpl(databaseConfig);

        PublicacionRepositoryImpl publicacionRepository = new PublicacionRepositoryImpl(databaseConfig);
        CalidadRepositoryImpl calidadRepository = new CalidadRepositoryImpl(databaseConfig);

        // === INICIALIZAR SERVICES ===
        UserServiceImpl usuarioService = new UserServiceImpl(usuarioRepository);
        CategoriaServiceImpl categoriaService = new CategoriaServiceImpl(categoriaRepository);
        ProductoServiceImpl productoService = new ProductoServiceImpl(productoRepository);
        CalificacionesServiceImpl calificacionesService = new CalificacionesServiceImpl(calificacionesRepository);

        // CAMBIO CRÍTICO: Usar TruequeServiceImpl (no HistorialTruequeServiceImpl)
        TruequeServiceImpl truequeService = new TruequeServiceImpl(truequeRepository);

        PublicacionServiceImpl publicacionService = new PublicacionServiceImpl(publicacionRepository);
        CalidadServiceImpl calidadService = new CalidadServiceImpl(calidadRepository);

        // === INICIALIZAR CONTROLLERS ===
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        CategoriaController categoriaController = new CategoriaController(categoriaService);
        ProductoController productoController = new ProductoController(productoService);
        CalificacionesController calificacionesController = new CalificacionesController(calificacionesService);

        // ARREGLO: Ahora SÍ funciona porque TruequeServiceImpl implementa ITruequeService
        TruequeController truequeController = new TruequeController(truequeService);

        PublicacionController publicacionController = new PublicacionController(publicacionService);
        CalidadController calidadController = new CalidadController(calidadService);

        // === CONFIGURAR RUTAS FUNCIONALES ===
        configureFunctionalRoutes(app, usuarioController, productoController, categoriaController,
                calificacionesController, truequeController, publicacionController, calidadController);
    }

    // MÉTODO PARA CONFIGURAR SOLO LAS RUTAS QUE FUNCIONAN
    public static void configureFunctionalRoutes(
            Javalin app,
            UsuarioController usuarioController,
            ProductoController productoController,
            CategoriaController categoriaController,
            CalificacionesController calificacionesController,
            TruequeController truequeController,
            PublicacionController publicacionController,
            CalidadController calidadController
    ) {

        // === HEALTH CHECK ===
        app.get("/api/health", ctx -> ctx.json("API Trukea funcionando correctamente ✅"));
        app.get("/api/status", ctx -> ctx.json("Server running"));

        // === USUARIOS ===
        app.get("/api/usuarios", usuarioController::getAllUsuarios);
        app.get("/api/usuarios/{email}", usuarioController::getUserByEmail);
        app.get("/api/usuarios/id/{id}", usuarioController::getUserById);
        app.post("/api/usuarios", usuarioController::createUser);
        app.put("/api/usuarios/{id}", usuarioController::updateUser);
        app.delete("/api/usuarios/{id}", usuarioController::deleteUser);

        // === PRODUCTOS ===
        app.get("/api/productos", productoController::getAllProductos);
        app.get("/api/productos/{id}", productoController::getProductoById);
        app.get("/api/productos/usuario/{usuarioId}", productoController::getProductosByUsuario);
        app.get("/api/productos/categoria/{categoriaId}", productoController::getProductosByCategoria);
        app.get("/api/productos/usuario/{usuarioId}/count", productoController::countProductosByUsuario);
        app.post("/api/productos", productoController::createProducto);
        app.put("/api/productos/{id}", productoController::updateProducto);
        app.delete("/api/productos/{id}", productoController::deleteProducto);

        // === CATEGORÍAS ===
        app.get("/api/categorias", categoriaController::getAllCategorias);
        app.get("/api/categorias/{id}", categoriaController::getCategoriaById);
        app.post("/api/categorias", categoriaController::createCategoria);

        // === CALIFICACIONES ===
        app.get("/api/calificaciones", calificacionesController::getAll);
        app.get("/api/calificaciones/{id}", calificacionesController::getById);
        app.get("/api/calificaciones/usuario/{usuarioId}", calificacionesController::getByUsuarioCalificado);
        app.post("/api/calificaciones", calificacionesController::create);
        app.delete("/api/calificaciones/{id}", calificacionesController::delete);

        // === TRUEQUES - TOTALMENTE FUNCIONAL ===
        app.get("/api/trueques", truequeController::getAll);
        app.get("/api/trueques/{id}", truequeController::getById);
        app.get("/api/trueques/enviados/{usuarioId}", truequeController::getEnviados);
        app.get("/api/trueques/recibidos/{usuarioId}", truequeController::getRecibidos);
        app.post("/api/trueques", truequeController::create);
        app.put("/api/trueques/{id}/aceptar", truequeController::aceptar);
        app.put("/api/trueques/{id}/rechazar", truequeController::rechazar);
        app.delete("/api/trueques/{id}", truequeController::delete);

        // === PUBLICACIONES ===
        app.get("/api/publicaciones", publicacionController::getAll);
        app.get("/api/publicaciones/{id}", publicacionController::getById);
        app.get("/api/publicaciones/usuario/{uid}", publicacionController::getByUsuario);
        app.post("/api/publicaciones", publicacionController::create);
        app.put("/api/publicaciones/{id}", publicacionController::update);
        app.delete("/api/publicaciones/{id}", publicacionController::delete);

        // === CALIDAD ===
        app.get("/api/calidad", calidadController::getAll);
        app.get("/api/calidad/{id}", calidadController::getById);
        app.post("/api/calidad", calidadController::create);
        app.put("/api/calidad/{id}", calidadController::update);
        app.delete("/api/calidad/{id}", calidadController::delete);

        // === ENDPOINTS BÁSICOS ADICIONALES ===
        app.get("/api/info", ctx -> ctx.json("API Trukea v1.0 - Sistema de Intercambio"));

        // Endpoint para listar todos los endpoints disponibles
        app.get("/api/endpoints", ctx -> {
            ctx.json("Endpoints disponibles: /api/usuarios, /api/productos, /api/categorias, " +
                    "/api/calificaciones, /api/trueques, /api/publicaciones, /api/calidad, /api/health");
        });
    }
}