package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.web.dtos.productos.request.CreateProductoRequest;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;

import java.util.ArrayList;

/**
 * Define las operaciones de negocio para la gestión de Productos.
 * Esta interfaz actúa como un contrato que la clase de implementación
 * (como ProductoServiceImpl) debe cumplir.
 */
public interface IProductoService {

    // --- OPERACIONES DE LECTURA (READ) ---

    /**
     * Devuelve una lista de todos los productos.
     * @return una lista de DTOs de respuesta de producto.
     */
    ArrayList<CreateProductoResponse> findAll();

    /**
     * Busca un producto por su ID único.
     * @param id El ID del producto a buscar.
     * @return La entidad Producto completa, o null si no se encuentra.
     */
    Producto findById(Long id);

    /**
     * Busca todos los productos que pertenecen a un usuario específico.
     * @param usuarioId El ID del usuario propietario.
     * @return una lista de DTOs de respuesta de producto.
     */
    ArrayList<CreateProductoResponse> findByUsuarioId(Long usuarioId);

    /**
     * Busca todos los productos que pertenecen a una categoría específica.
     * @param categoriaId El ID de la categoría.
     * @return una lista de DTOs de respuesta de producto.
     */
    ArrayList<CreateProductoResponse> findByCategoriaId(Long categoriaId);


    // --- OPERACIONES DE ESCRITURA ---

    /**
     * Crea un nuevo producto en el sistema.
     * El objeto request debe contener todos los datos necesarios,
     * incluyendo el ID del usuario propietario.
     * @param request El DTO con la información para crear el producto.
     * @return El DTO de respuesta del producto recién creado.
     */
    CreateProductoResponse createProducto(CreateProductoRequest request);

    /**
     * Actualiza un producto existente.
     * @param id El ID del producto a actualizar.
     * @param request El DTO con los nuevos datos para el producto.
     * @return El DTO de respuesta del producto actualizado.
     */
    CreateProductoResponse updateProducto(Long id, CreateProductoRequest request);

    /**
     * Elimina un producto por su ID.
     * @param id El ID del producto a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    boolean deleteProducto(Long id);


    // --- OPERACIONES DE UTILIDAD ---

    /**
     * Verifica si un producto existe por su ID.
     * @param id El ID del producto.
     * @return true si existe, false si no.
     */
    boolean existsById(Long id);

    /**
     * Cuenta el número de productos que pertenecen a un usuario.
     * @param usuarioId El ID del usuario.
     * @return el número total de productos.
     */
    int countByUsuarioId(Long usuarioId);

    /**
     * Cuenta el número de productos que pertenecen a una categoría.
     * @param categoriaId El ID de la categoría.
     * @return el número total de productos.
     */
    int countByCategoriaId(Long categoriaId);
}