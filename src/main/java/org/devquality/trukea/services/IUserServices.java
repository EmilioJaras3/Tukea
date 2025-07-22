package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.usuarios.request.CreateUsuarioRequest;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;

import java.util.ArrayList;

public interface IUserServices {

    // ============================================
    // READ OPERATIONS
    // ============================================

    /**
     * Obtiene todos los usuarios del sistema
     * @return Lista de usuarios en formato CreateUsuarioResponse
     */
    ArrayList<CreateUsuarioResponse> getAllUsers();

    /**
     * Busca un usuario por su email
     * @param email Email del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    CreateUsuarioResponse getUserByEmail(String email);

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    CreateUsuarioResponse getUserById(Long id);

    // ============================================
    // CREATE OPERATION
    // ============================================

    /**
     * Crea un nuevo usuario en el sistema
     * @param request Datos del usuario a crear
     * @return Usuario creado con ID asignado
     * @throws IllegalArgumentException si los datos son inválidos
     * @throws RuntimeException si el email ya existe
     */
    CreateUsuarioResponse createUser(CreateUsuarioRequest request);

    // ============================================
    // UPDATE OPERATION
    // ============================================

    /**
     * Actualiza un usuario existente
     * @param id ID del usuario a actualizar
     * @param request Nuevos datos del usuario
     * @return Usuario actualizado o null si no existe
     * @throws IllegalArgumentException si los datos son inválidos
     */
    CreateUsuarioResponse updateUser(Long id, CreateUsuarioRequest request);

    // ============================================
    // DELETE OPERATION
    // ============================================

    /**
     * Elimina un usuario del sistema
     * @param id ID del usuario a eliminar
     * @return true si se eliminó exitosamente, false si no existe
     */
    boolean deleteUser(Long id);

    // ============================================
    // UTILITY OPERATIONS
    // ============================================

    /**
     * Verifica si existe un usuario con el email dado
     * @param email Email a verificar
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario con el ID dado
     * @param id ID a verificar
     * @return true si existe, false si no
     */
    boolean existsById(Long id);

    /**
     * Valida que los datos del usuario sean correctos
     * @param request Request a validar
     * @return true si es válido, false si no
     */
    boolean isValidUserData(CreateUsuarioRequest request);

    /**
     * Cuenta el total de usuarios en el sistema
     * @return Número total de usuarios
     */
    int countAllUsers();
}