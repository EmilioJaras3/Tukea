package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.repositories.impl.*;
import org.devquality.trukea.services.impl.*;
import org.devquality.trukea.web.controllers.*;

/**
 * Clase principal que inicializa y conecta todos los componentes de la aplicación (MVC).
 * Es el único lugar que el Main.java necesita llamar para configurar todo.
 */
public class Routes {

    private final DatabaseConfig databaseConfig;

    public Routes(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    /**
     * El método principal que crea y conecta todo.
     */
    public void routes(Javalin app) {

        // ==========================================================
        // PASO 1: INICIALIZAR TODOS LOS REPOSITORIOS
        // ==========================================================
        // Repositorios que necesitan la configuración de la BD
        UsuarioRepositoryImpl usuarioRepository = new UsuarioRepositoryImpl(databaseConfig);
        CategoriaRepositoryImpl categoriaRepository = new CategoriaRepositoryImpl(databaseConfig);
        CalificacionesRepositoryImpl calificacionesRepository = new CalificacionesRepositoryImpl(databaseConfig);
        TruequeRepositoryImpl truequeRepository = new TruequeRepositoryImpl(databaseConfig);
        PublicacionRepositoryImpl publicacionRepository = new PublicacionRepositoryImpl(databaseConfig);
        CalidadRepositoryImpl calidadRepository = new CalidadRepositoryImpl(databaseConfig);

        // Repositorio de Productos que maneja su propia conexión
        ProductoRepositoryImpl productoRepository = new ProductoRepositoryImpl();

        // ==========================================================
        // PASO 2: INICIALIZAR TODOS LOS SERVICIOS (¡CORREGIDO!)
        // ==========================================================
        // Se crea una instancia NORMAL de ProductoServiceImpl, sin la parte "falsa".
        UserServiceImpl usuarioService = new UserServiceImpl(usuarioRepository);
        CategoriaServiceImpl categoriaService = new CategoriaServiceImpl(categoriaRepository);
        ProductoServiceImpl productoService = new ProductoServiceImpl(productoRepository); // <-- ¡LA FORMA CORRECTA!
        CalificacionesServiceImpl calificacionesService = new CalificacionesServiceImpl(calificacionesRepository);
        TruequeServiceImpl truequeService = new TruequeServiceImpl(truequeRepository);
        PublicacionServiceImpl publicacionService = new PublicacionServiceImpl(publicacionRepository);
        CalidadServiceImpl calidadService = new CalidadServiceImpl(calidadRepository);

        // ==========================================================
        // PASO 3: INICIALIZAR TODOS LOS CONTROLADORES
        // ==========================================================
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        CategoriaController categoriaController = new CategoriaController(categoriaService);
        ProductoController productoController = new ProductoController(productoService);
        CalificacionesController calificacionesController = new CalificacionesController(calificacionesService);
        TruequeController truequeController = new TruequeController(truequeService);
        PublicacionController publicacionController = new PublicacionController(publicacionService);
        CalidadController calidadController = new CalidadController(calidadService);

        // ==========================================================
        // PASO 4: CONFIGURAR TODAS LAS RUTAS
        // ==========================================================

        // --- RUTAS GENERALES ---
        app.get("/api/health", ctx -> ctx.json("API Trukea funcionando correctamente ✅"));
        app.get("/api/status", ctx -> ctx.json("Server running"));

        // --- RUTAS DE PRODUCTOS ---
        // Aquí le decimos que use la clase ProductosRoutes que ya tienes.
        new ProductosRoutes(productoController).configure(app);

        // --- AQUÍ IRÍAN LAS OTRAS RUTAS ---
        // Por ejemplo, si tuvieras una clase UsuarioRoutes:
        // new UsuarioRoutes(usuarioController).configure(app);
        // O puedes definirlas directamente si prefieres:
        app.get("/api/categorias", categoriaController::getAllCategorias);
        app.post("/api/categorias", categoriaController::createCategoria);
        // etc...
    }
}