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
import org.devquality.trukea.routes.TruequesRoutes;
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
        TruequeRepositoryImpl truequeRepository = new TruequeRepositoryImpl(databaseConfig);
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
        TruequeServiceImpl truequeService = new TruequeServiceImpl(truequeRepository);
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
        TruequeController truequeController = new TruequeController(truequeService);
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

        // --- RUTAS DE USUARIOS ---
        app.get("/api/usuarios", usuarioController::getAllUsuarios);
        app.get("/api/usuarios/{id}", usuarioController::getUserById);
        app.post("/api/usuarios", usuarioController::createUser);

        // --- RUTAS DE CATEGORÍAS ---
        app.get("/api/categorias", categoriaController::getAllCategorias);
        app.get("/api/categorias/{id}", categoriaController::getCategoriaById);
        app.post("/api/categorias", categoriaController::createCategoria);

        // --- RUTAS DE PRODUCTOS ---
        app.get("/api/productos", productoController::getAllProductos);
        app.get("/api/productos/{id}", productoController::getProductoById);
        app.get("/api/productos/usuario/{usuarioId}", productoController::getProductosByUsuario);
        app.get("/api/productos/categoria/{categoriaId}", productoController::getProductosByCategoria);
        app.post("/api/productos", productoController::createProducto);
        app.put("/api/productos/{id}", productoController::updateProducto);
        app.delete("/api/productos/{id}", productoController::deleteProducto);
        app.get("/api/productos/usuario/{usuarioId}/count", productoController::countProductosByUsuario);

        // --- RUTAS DE CALIFICACIONES ---
        app.get("/api/calificaciones", calificacionesController::getAll);
        app.get("/api/calificaciones/{id}", calificacionesController::getById);
        app.post("/api/calificaciones", calificacionesController::create);
        app.delete("/api/calificaciones/{id}", calificacionesController::delete);
        app.get("/api/calificaciones/usuario/{usuarioId}", calificacionesController::getByUsuario);

        // --- RUTAS DE TRUEQUES ---
        app.get("/api/trueques", truequeController::getAll);
        app.get("/api/trueques/{id}", truequeController::getById);
        app.post("/api/trueques", truequeController::create);
        app.delete("/api/trueques/{id}", truequeController::delete);
        app.get("/api/trueques/enviados/{usuarioId}", truequeController::getEnviados);
        app.get("/api/trueques/recibidos/{usuarioId}", truequeController::getRecibidos);
        app.put("/api/trueques/{id}/aceptar", truequeController::aceptar);
        app.put("/api/trueques/{id}/rechazar", truequeController::rechazar);

        // --- RUTAS DE PUBLICACIONES ---
        app.get("/api/publicaciones", publicacionController::getAll);
        app.get("/api/publicaciones/{id}", publicacionController::getById);
        app.get("/api/publicaciones/usuario/{uid}", publicacionController::getByUsuario);
        app.post("/api/publicaciones", publicacionController::create);
        app.put("/api/publicaciones/{id}", publicacionController::update);
        app.delete("/api/publicaciones/{id}", publicacionController::delete);

        // --- RUTAS DE CALIDAD ---
        app.get("/api/calidad", calidadController::getAll);
        app.get("/api/calidad/{id}", calidadController::getById);
        // app.post("/api/calidad", calidadController::create); // Descomentar si tienes el método

        // --- RUTAS DE HISTORIAL DE TRUEQUE ---
        app.get("/api/historiales", historialTruequeController::getAll);
        app.get("/api/historiales/{id}", historialTruequeController::getById);
        app.get("/api/historiales/usuario/{uid}", historialTruequeController::getByUsuario);

        // --- RUTAS DE ZONAS ---
        app.get("/api/zonas", zonaController::getAll);
        app.post("/api/zonas", zonaController::create);
        app.get("/api/zonas/{id}/horarios", zonaController::getHorarios);

        // --- RUTAS DE PUBLICACION ESTATUS ---
        app.post("/api/publicacion-estatus", publicacionEstastusController::registrar);
        app.get("/api/publicacion-estatus/{id}", publicacionEstastusController::obtenerHistorial);

        // --- RUTAS DE IMÁGENES ---
        app.post("/api/imagenes", imagenController::subirImagen);
    }
}