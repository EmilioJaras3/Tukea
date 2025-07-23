package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Producto;

import java.util.ArrayList;

public interface IProductoRepository {

    // READ operations
    ArrayList<Producto> findAllProductos();
    Producto findById(Long id);
    ArrayList<Producto> findByUsuarioId(Long usuarioId);
    ArrayList<Producto> findByCategoria(String categoria);

    // CREATE operation
    Producto createProducto(Producto producto);

    // UPDATE operation
    Producto updateProducto(Producto producto);

    // DELETE operation
    boolean deleteProducto(Long id);

    // UTILITY operations
    boolean existsById(Long id);
    int countByUsuarioId(Long usuarioId);
    int countByCategoria(String categoria);
}