package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.services.IUserServices;
import org.devquality.trukea.web.dtos.usuarios.request.CreateUsuarioRequest;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class UsuarioController {

    private final IUserServices userService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(IUserServices userService) {
        this.userService = userService;
    }

    // ============================================
    // GET /api/usuarios - Obtener todos los usuarios
    // ============================================
    public void getAllUsuarios(Context ctx) {
        try {
            logger.info("Request to get all usuarios");

            ArrayList<CreateUsuarioResponse> usuarios = userService.getAllUsers();

            if (usuarios.isEmpty()) {
                ctx.status(HttpStatus.OK)
                        .json(new ArrayList<>());
                logger.info("No users found, returning empty list");
            } else {
                ctx.status(HttpStatus.OK)
                        .json(usuarios);
                logger.info("Found {} users", usuarios.size());
            }

        } catch (Exception e) {
            logger.error("Error getting all usuarios", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(createErrorResponse("Error al obtener usuarios: " + e.getMessage()));
        }
    }

    // ============================================
    // GET /api/usuarios/{email} - Buscar por email
    // ============================================
    public void getUserByEmail(Context ctx) {
        try {
            String email = ctx.pathParam("email");

            if (email == null || email.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Email is required"));
                return;
            }

            logger.info("Request to get user by email: {}", email);

            CreateUsuarioResponse usuario = userService.getUserByEmail(email.trim());

            if (usuario != null) {
                ctx.status(HttpStatus.OK)
                        .json(usuario);
                logger.info("User found with email: {}", email);
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(createErrorResponse("Usuario no encontrado con email: " + email));
                logger.warn("User not found with email: {}", email);
            }

        } catch (Exception e) {
            logger.error("Error getting user by email", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(createErrorResponse("Error al buscar usuario: " + e.getMessage()));
        }
    }

    // ============================================
    // GET /api/usuarios/id/{id} - Buscar por ID
    // ============================================
    public void getUserById(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");

            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("ID is required"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Invalid ID format"));
                return;
            }

            logger.info("Request to get user by ID: {}", id);

            CreateUsuarioResponse usuario = userService.getUserById(id);

            if (usuario != null) {
                ctx.status(HttpStatus.OK)
                        .json(usuario);
                logger.info("User found with ID: {}", id);
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(createErrorResponse("Usuario no encontrado con ID: " + id));
                logger.warn("User not found with ID: {}", id);
            }

        } catch (Exception e) {
            logger.error("Error getting user by ID", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(createErrorResponse("Error al buscar usuario: " + e.getMessage()));
        }
    }

    // ============================================
    // POST /api/usuarios - Crear nuevo usuario
    // ============================================
    public void createUser(Context ctx) {
        try {
            logger.info("Request to create new user");

            // Validar Content-Type
            if (!ctx.contentType().contains("application/json")) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Content-Type debe ser application/json"));
                return;
            }

            // Parsear request body
            CreateUsuarioRequest request;
            try {
                request = ctx.bodyAsClass(CreateUsuarioRequest.class);
            } catch (Exception e) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("JSON inválido: " + e.getMessage()));
                return;
            }

            // Validar request
            if (request == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Request body is required"));
                return;
            }

            if (!request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Datos de usuario inválidos. Nombre, apellidoPaterno, correo y clave son requeridos"));
                return;
            }

            logger.debug("Creating user with email: {}", request.getCorreo());

            // Crear usuario
            CreateUsuarioResponse usuarioCreado = userService.createUser(request);

            ctx.status(HttpStatus.CREATED)
                    .json(usuarioCreado);

            logger.info("User created successfully with ID: {}", usuarioCreado.getIdUsuario());

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid user data: {}", e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(createErrorResponse(e.getMessage()));

        } catch (Exception e) {
            logger.error("Error creating user", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(createErrorResponse("Error al crear usuario: " + e.getMessage()));
        }
    }

    // ============================================
    // PUT /api/usuarios/{id} - Actualizar usuario
    // ============================================
    public void updateUser(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");

            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("ID is required"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Invalid ID format"));
                return;
            }

            logger.info("Request to update user with ID: {}", id);

            // Validar Content-Type
            if (!ctx.contentType().contains("application/json")) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Content-Type debe ser application/json"));
                return;
            }

            // Parsear request body
            CreateUsuarioRequest request;
            try {
                request = ctx.bodyAsClass(CreateUsuarioRequest.class);
            } catch (Exception e) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("JSON inválido: " + e.getMessage()));
                return;
            }

            if (request == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Request body is required"));
                return;
            }

            // Actualizar usuario
            CreateUsuarioResponse usuarioActualizado = userService.updateUser(id, request);

            if (usuarioActualizado != null) {
                ctx.status(HttpStatus.OK)
                        .json(usuarioActualizado);
                logger.info("User updated successfully with ID: {}", id);
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(createErrorResponse("Usuario no encontrado con ID: " + id));
                logger.warn("User not found for update with ID: {}", id);
            }

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid update data: {}", e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(createErrorResponse(e.getMessage()));

        } catch (Exception e) {
            logger.error("Error updating user", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(createErrorResponse("Error al actualizar usuario: " + e.getMessage()));
        }
    }

    // ============================================
    // DELETE /api/usuarios/{id} - Eliminar usuario
    // ============================================
    public void deleteUser(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");

            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("ID is required"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(createErrorResponse("Invalid ID format"));
                return;
            }

            logger.info("Request to delete user with ID: {}", id);

            boolean deleted = userService.deleteUser(id);

            if (deleted) {
                ctx.status(HttpStatus.OK)
                        .json(createSuccessResponse("Usuario eliminado exitosamente"));
                logger.info("User deleted successfully with ID: {}", id);
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(createErrorResponse("Usuario no encontrado con ID: " + id));
                logger.warn("User not found for deletion with ID: {}", id);
            }

        } catch (Exception e) {
            logger.error("Error deleting user", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(createErrorResponse("Error al eliminar usuario: " + e.getMessage()));
        }
    }

    // ============================================
    // MÉTODOS AUXILIARES
    // ============================================

    private ErrorResponse createErrorResponse(String message) {
        return new ErrorResponse(message, System.currentTimeMillis());
    }

    private SuccessResponse createSuccessResponse(String message) {
        return new SuccessResponse(message, System.currentTimeMillis());
    }

    // ============================================
    // CLASES INTERNAS PARA RESPUESTAS
    // ============================================

    public static class ErrorResponse {
        private String message;
        private long timestamp;

        public ErrorResponse(String message, long timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }

    public static class SuccessResponse {
        private String message;
        private long timestamp;

        public SuccessResponse(String message, long timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}