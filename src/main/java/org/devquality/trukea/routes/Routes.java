package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.repositories.impl.*;
import org.devquality.trukea.services.impl.*;
import org.devquality.trukea.web.controllers.*;
import org.devquality.trukea.repositories.impl.ZonaRepositoryImpl;
import org.devquality.trukea.services.impl.ZonaServiceImpl;
import org.devquality.trukea.web.controllers.ZonaController;
import org.devquality.trukea.persistance.repositories.impl.HistorialTruequeRepositoryImpl;
import org.devquality.trukea.services.impl.HistorialTruequeServiceImpl;
import org.devquality.trukea.web.controllers.HistorialTruequeController;
import org.devquality.trukea.persistance.repositories.ICiudadRepository;
import org.devquality.trukea.services.impl.CiudadServiceImpl;
import org.devquality.trukea.web.controllers.CiudadController;
import org.devquality.trukea.services.impl.CloudinaryService;
import org.devquality.trukea.web.controllers.ImagenController;
import org.devquality.trukea.services.impl.PublicacionEstastusServiceImpl;
import org.devquality.trukea.web.controllers.PublicacionEstastusController;
import org.devquality.trukea.repositories.impl.PublicacionEstastusRepositoryImpl;
import org.devquality.trukea.routes.UsuariosRoutes;
import org.devquality.trukea.routes.ProductosRoutes;
import org.devquality.trukea.routes.CategoriasRoutes;
import org.devquality.trukea.routes.CalificacionesRoutes;
import org.devquality.trukea.routes.IntercambiosRoutes;
import org.devquality.trukea.routes.ImagenesRoutes;
import org.devquality.trukea.routes.ZonaRoutes;
import org.devquality.trukea.routes.PublicacionEstastusRoutes;

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
        IntercambioRepositoryImpl intercambioRepository = new IntercambioRepositoryImpl(databaseConfig);
        PublicacionRepositoryImpl publicacionRepository = new PublicacionRepositoryImpl(databaseConfig);
        CalidadRepositoryImpl calidadRepository = new CalidadRepositoryImpl(databaseConfig);
        // Repositorio de Productos que maneja su propia conexión
        ProductoRepositoryImpl productoRepository = new ProductoRepositoryImpl(databaseConfig);
        // Repositorios adicionales
        ZonaRepositoryImpl zonaRepository = new ZonaRepositoryImpl();
        HistorialTruequeRepositoryImpl historialTruequeRepository = new HistorialTruequeRepositoryImpl(databaseConfig);
        PublicacionEstastusRepositoryImpl publicacionEstastusRepository = new PublicacionEstastusRepositoryImpl();
        // ICiudadRepository no tiene implementación, se debe crear si se requiere funcionalidad de ciudades

        // ==========================================================
        // PASO 2: INICIALIZAR TODOS LOS SERVICIOS
        // ==========================================================
        UserServiceImpl usuarioService = new UserServiceImpl(usuarioRepository);
        CategoriaServiceImpl categoriaService = new CategoriaServiceImpl(categoriaRepository);
        ProductoServiceImpl productoService = new ProductoServiceImpl(productoRepository);
        CalificacionesServiceImpl calificacionesService = new CalificacionesServiceImpl(calificacionesRepository);
        IntercambioServiceImpl intercambioService = new IntercambioServiceImpl(intercambioRepository);
        PublicacionServiceImpl publicacionService = new PublicacionServiceImpl(publicacionRepository);
        CalidadServiceImpl calidadService = new CalidadServiceImpl(calidadRepository);
        ZonaServiceImpl zonaService = new ZonaServiceImpl(zonaRepository);
        HistorialTruequeServiceImpl historialTruequeService = new HistorialTruequeServiceImpl(historialTruequeRepository);
        // CiudadServiceImpl requiere un repositorio, se omite si no existe implementación
        // Servicios adicionales
        CloudinaryService cloudinaryService = new CloudinaryService();
        PublicacionEstastusServiceImpl publicacionEstastusService = new PublicacionEstastusServiceImpl(publicacionEstastusRepository);

        // ==========================================================
        // PASO 3: INICIALIZAR TODOS LOS CONTROLADORES
        // ==========================================================
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        CategoriaController categoriaController = new CategoriaController(categoriaService);
        ProductoController productoController = new ProductoController(productoService);
        CalificacionesController calificacionesController = new CalificacionesController(calificacionesService);
        IntercambioController intercambioController = new IntercambioController(intercambioService);
        PublicacionController publicacionController = new PublicacionController(publicacionService);
        CalidadController calidadController = new CalidadController(calidadService);
        ZonaController zonaController = new ZonaController(zonaService);
        HistorialTruequeController historialTruequeController = new HistorialTruequeController(historialTruequeService);
        ImagenController imagenController = new ImagenController(cloudinaryService);
        PublicacionEstastusController publicacionEstastusController = new PublicacionEstastusController(publicacionEstastusService);
        // CiudadController ciudadController = new CiudadController(ciudadService); // Descomentar si tienes el servicio y repositorio

        // ==========================================================
        // PASO 4: CONFIGURAR TODAS LAS RUTAS
        // ==========================================================

        // --- RUTAS GENERALES ---
        app.get("/api/health", ctx -> ctx.json("API Trukea funcionando correctamente ✅"));
        app.get("/api/status", ctx -> ctx.json("Server running"));

        // --- RUTAS DELEGADAS ---
        new UsuariosRoutes(usuarioController).configure(app);
        new ProductosRoutes(productoController).configure(app);
        new CategoriasRoutes(categoriaController).configure(app);
        new CalificacionesRoutes(calificacionesController).configure(app);
        new IntercambiosRoutes(intercambioController).configure(app);
        new ImagenesRoutes(imagenController).configure(app);

        // Registrar rutas de historial_trueques
        app.get("/api/historiales", historialTruequeController::getAll);
        app.get("/api/historiales/{id}", historialTruequeController::getById);
        app.get("/api/historiales/usuario/{uid}", historialTruequeController::getByUsuario);
        app.post("/api/historiales", historialTruequeController::create);
        app.put("/api/historiales/{id}", historialTruequeController::update);
        app.delete("/api/historiales/{id}", historialTruequeController::delete);

    }
}