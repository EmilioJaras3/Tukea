package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.devquality.trukea.services.IProductoService;
import org.devquality.trukea.web.dtos.productos.request.CreateProductoRequest;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Implementación COMPLETA de la lógica de negocio para la gestión de productos.
 */
public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository productoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    public ProductoServiceImpl(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // --- OPERACIONES DE ESCRITURA ---

    @Override
    public CreateProductoResponse createProducto(CreateProductoRequest request) {
        try {
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Datos de producto inválidos.");
            }
            if (request.getIdUsuario() == null || request.getIdUsuario() <= 0) {
                throw new IllegalArgumentException("El ID del usuario es un campo requerido.");
            }
            if (request.getIdCategoria() == null || request.getIdCategoria() <= 0) {
                throw new IllegalArgumentException("El ID de la categoría es un campo requerido.");
            }
            Producto producto = new Producto();
            producto.setTitulo(request.getNombreProducto());
            producto.setDescripcion(request.getDescripcionProducto());
            producto.setCategoria(request.getCategoria());
            producto.setUsuarioId(request.getIdUsuario().longValue());
            producto.setEstado(request.getEstado());
            producto.setImagenUrl(request.getImagenUrl());
            producto.setZonaSeguraId(request.getZonaSeguraId());
            producto.setFechaPublicacion(LocalDateTime.now());

            Producto productoCreado = this.productoRepository.createProducto(producto);
            return mapToCreateProductoResponse(productoCreado);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado en el servicio al crear producto", e);
            throw new RuntimeException("Ocurrió un error inesperado al procesar la solicitud.", e);
        }
    }

    @Override
    public CreateProductoResponse updateProducto(Long id, CreateProductoRequest request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto a actualizar es inválido.");
        }
        Producto productoExistente = this.productoRepository.findById(id);
        if (productoExistente == null) {
            throw new IllegalArgumentException("No se encontró un producto con el ID: " + id);
        }

        productoExistente.setTitulo(request.getNombreProducto());
        productoExistente.setDescripcion(request.getDescripcionProducto());
        productoExistente.setCategoria(request.getCategoria());
        productoExistente.setEstado(request.getEstado());
        productoExistente.setImagenUrl(request.getImagenUrl());
        productoExistente.setZonaSeguraId(request.getZonaSeguraId());
        productoExistente.setFechaPublicacion(LocalDateTime.now());

        Producto productoActualizado = this.productoRepository.updateProducto(productoExistente);
        return mapToCreateProductoResponse(productoActualizado);
    }

    @Override
    public boolean deleteProducto(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto es inválido.");
        }
        if (!this.productoRepository.existsById(id)) {
            logger.warn("Se intentó borrar un producto inexistente con ID: {}", id);
            return false;
        }
        return this.productoRepository.deleteProducto(id);
    }

    // --- OPERACIONES DE LECTURA ---

    @Override
    public ArrayList<CreateProductoResponse> findAll() {
        ArrayList<Producto> productos = productoRepository.findAllProductos();
        return productos.stream()
                .map(this::mapToCreateProductoResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Producto findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto es inválido.");
        }
        return productoRepository.findById(id);
    }

    @Override
    public ArrayList<CreateProductoResponse> findByUsuarioId(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario es inválido.");
        }
        ArrayList<Producto> productos = productoRepository.findByUsuarioId(usuarioId);
        return productos.stream()
                .map(this::mapToCreateProductoResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<CreateProductoResponse> findByCategoriaId(Long categoriaId) {
        if (categoriaId == null || categoriaId <= 0) {
            throw new IllegalArgumentException("El ID de la categoría es inválido.");
        }
        // Adaptar: ahora la categoría es String, no ID
        throw new UnsupportedOperationException("Buscar por ID de categoría ya no es válido. Usa findByCategoria(String categoria)");
    }

    // --- OPERACIONES DE UTILIDAD ---

    @Override
    public boolean existsById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return productoRepository.existsById(id);
    }

    @Override
    public int countByUsuarioId(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            return 0;
        }
        return productoRepository.countByUsuarioId(usuarioId);
    }

    @Override
    public int countByCategoriaId(Long categoriaId) {
        if (categoriaId == null || categoriaId <= 0) {
            return 0;
        }
        // Adaptar: ahora la categoría es String, no ID
        throw new UnsupportedOperationException("Contar por ID de categoría ya no es válido. Usa countByCategoria(String categoria)");
    }

    // --- MÉTODO PRIVADO DE AYUDA (HELPER) ---

    private CreateProductoResponse mapToCreateProductoResponse(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new CreateProductoResponse(
                producto.getId() != null ? producto.getId().intValue() : null,
                producto.getTitulo(),
                producto.getDescripcion(),
                null, // valorEstimado eliminado
                null, // idCalidad eliminado
                null  // categoriaId eliminado
        );
    }
}