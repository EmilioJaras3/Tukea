
package org.devquality.trukea.persistance.repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductoRepositoryImpl implements IProductoRepository {
    private final DatabaseConfig databaseConfig;
    private static final Logger logger = LoggerFactory.getLogger(ProductoRepositoryImpl.class);

    // ✅ CONSULTAS CORREGIDAS CON TODOS LOS CAMPOS Y JOINS
    private static final String SELECT_PRODUCTOS_COMPLETOS = """
        SELECT p.id_producto, p.nombre_producto, p.descripcion_producto,
               p.valor_estimado, p.id_calidad, p.id_categoria,
               p.id_usuario_propietario,
               c.categoria as categoria_nombre, c.descripcion_categoria,
               cal.nivel_calidad, cal.descripcion_calidad,
               u.nombre as propietario_nombre, 
               CONCAT(u.nombre, ' ', u.apellido_paterno) as propietario_nombre_completo,
               ciu.nombre as propietario_ciudad
        FROM productos p
        INNER JOIN categorias c ON p.id_categoria = c.id_categoria
        INNER JOIN calidad cal ON p.id_calidad = cal.id_calidad
        INNER JOIN usuarios u ON p.id_usuario_propietario = u.id_usuario
        LEFT JOIN ciudades ciu ON u.id_ciudad = ciu.id_ciudad
        """;

    private static final String SELECT_PRODUCTO_BY_ID = SELECT_PRODUCTOS_COMPLETOS + " WHERE p.id_producto = ?";
    private static final String SELECT_PRODUCTOS_BY_USUARIO = SELECT_PRODUCTOS_COMPLETOS + " WHERE p.id_usuario_propietario = ?";
    private static final String SELECT_PRODUCTOS_BY_CATEGORIA = SELECT_PRODUCTOS_COMPLETOS + " WHERE p.id_categoria = ?";

    private static final String INSERT_PRODUCTO = """
        INSERT INTO productos (nombre_producto, descripcion_producto, valor_estimado, 
                             id_calidad, id_categoria, id_usuario_propietario) 
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    private static final String UPDATE_PRODUCTO = """
        UPDATE productos SET nombre_producto = ?, descripcion_producto = ?, valor_estimado = ?,
                           id_calidad = ?, id_categoria = ?, id_usuario_propietario = ? 
        WHERE id_producto = ?
        """;

    private static final String DELETE_PRODUCTO = "DELETE FROM productos WHERE id_producto = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM productos WHERE id_producto = ?";
    private static final String COUNT_BY_USUARIO = "SELECT COUNT(*) FROM productos WHERE id_usuario_propietario = ?";
    private static final String COUNT_BY_CATEGORIA = "SELECT COUNT(*) FROM productos WHERE id_categoria = ?";

    public ProductoRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Producto> findAllProductos() {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTOS_COMPLETOS + " ORDER BY p.nombre_producto");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ArrayList<Producto> productos = new ArrayList<>();
            while(resultSet.next()) {
                Producto producto = this.mapResultSetToProductoCompleto(resultSet);
                productos.add(producto);
                logger.debug("Producto found: {}", producto.getNombre());
            }
            return productos;
        } catch (SQLException e) {
            logger.error("Error finding all productos", e);
            throw new RuntimeException("Error al obtener todos los productos", e);
        }
    }

    @Override
    public Producto findById(Long id) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTO_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return this.mapResultSetToProductoCompleto(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error finding producto by id: {}", id, e);
            throw new RuntimeException("Error al buscar producto por ID", e);
        }
    }

    @Override
    public ArrayList<Producto> findByUsuarioId(Long usuarioId) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTOS_BY_USUARIO)) {

            preparedStatement.setLong(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Producto> productos = new ArrayList<>();
            while(resultSet.next()) {
                Producto producto = this.mapResultSetToProductoCompleto(resultSet);
                productos.add(producto);
            }
            return productos;
        } catch (SQLException e) {
            logger.error("Error finding productos by usuario id: {}", usuarioId, e);
            throw new RuntimeException("Error al buscar productos por usuario", e);
        }
    }

    @Override
    public ArrayList<Producto> findByCategoriaId(Long categoriaId) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTOS_BY_CATEGORIA)) {

            preparedStatement.setLong(1, categoriaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Producto> productos = new ArrayList<>();
            while(resultSet.next()) {
                Producto producto = this.mapResultSetToProductoCompleto(resultSet);
                productos.add(producto);
            }
            return productos;
        } catch (SQLException e) {
            logger.error("Error finding productos by categoria id: {}", categoriaId, e);
            throw new RuntimeException("Error al buscar productos por categoría", e);
        }
    }

    @Override
    public Producto createProducto(Producto producto) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTO, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setInt(3, producto.getValorEstimado());
            preparedStatement.setInt(4, producto.getIdCalidad());
            preparedStatement.setLong(5, producto.getCategoriaId());
            preparedStatement.setLong(6, producto.getIdUsuarioPropietario());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating producto failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getLong(1));
                }
            }

            logger.info("Producto created successfully with ID: {}", producto.getId());
            return producto;
        } catch (SQLException e) {
            logger.error("Error creating producto: {}", producto.getNombre(), e);
            throw new RuntimeException("Error al crear producto", e);
        }
    }

    @Override
    public Producto updateProducto(Producto producto) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTO)) {

            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setInt(3, producto.getValorEstimado());
            preparedStatement.setInt(4, producto.getIdCalidad());
            preparedStatement.setLong(5, producto.getCategoriaId());
            preparedStatement.setLong(6, producto.getIdUsuarioPropietario());
            preparedStatement.setLong(7, producto.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating producto failed, no rows affected.");
            }

            logger.info("Producto updated successfully with ID: {}", producto.getId());
            return producto;
        } catch (SQLException e) {
            logger.error("Error updating producto: {}", producto.getId(), e);
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public boolean deleteProducto(Long id) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTO)) {

            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            boolean deleted = affectedRows > 0;

            if (deleted) {
                logger.info("Producto deleted successfully with ID: {}", id);
            } else {
                logger.warn("No producto found to delete with ID: {}", id);
            }
            return deleted;
        } catch (SQLException e) {
            logger.error("Error deleting producto: {}", id, e);
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Error checking if producto exists by id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de producto", e);
        }
    }

    @Override
    public int countByUsuarioId(Long usuarioId) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_USUARIO)) {

            preparedStatement.setLong(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            logger.error("Error counting productos by usuario id: {}", usuarioId, e);
            throw new RuntimeException("Error al contar productos por usuario", e);
        }
    }

    @Override
    public int countByCategoriaId(Long categoriaId) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_CATEGORIA)) {

            preparedStatement.setLong(1, categoriaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            logger.error("Error counting productos by categoria id: {}", categoriaId, e);
            throw new RuntimeException("Error al contar productos por categoría", e);
        }
    }

    // ✅ MAPEO COMPLETO CON TODOS LOS CAMPOS
    private Producto mapResultSetToProductoCompleto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id_producto"));
        producto.setNombre(resultSet.getString("nombre_producto"));
        producto.setDescripcion(resultSet.getString("descripcion_producto"));

        // ⭐ CAMPOS QUE FALTABAN EN TU VERSIÓN ORIGINAL:
        producto.setValorEstimado(resultSet.getInt("valor_estimado"));
        producto.setIdCalidad(resultSet.getInt("id_calidad"));
        producto.setCategoriaId(resultSet.getLong("id_categoria"));
        producto.setIdUsuarioPropietario(resultSet.getLong("id_usuario_propietario"));

        return producto;
    }
}
