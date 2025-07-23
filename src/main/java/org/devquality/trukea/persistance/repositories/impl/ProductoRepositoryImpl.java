package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación COMPLETA de IProductoRepository usando JDBC.
 * Se eliminaron las referencias a las columnas 'activo' y 'fecha_creacion'
 * para que coincida con la estructura de tu base de datos.
 */
public class ProductoRepositoryImpl implements IProductoRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductoRepositoryImpl.class);

    public ProductoRepositoryImpl() {}

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/bd_trukea";
        String user = "root";
        String password = "emico311006L"; // Tu contraseña real. ¡BIEN HECHO!

        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    // --- HELPER DE MAPEO ---
    private Producto mapRowToProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id_producto"));
        p.setNombre(rs.getString("nombre_producto"));
        p.setDescripcion(rs.getString("descripcion_producto"));
        p.setValorEstimado(rs.getInt("valor_estimado"));
        p.setIdCalidad((Integer) rs.getObject("id_calidad"));
        p.setCategoriaId(rs.getLong("id_categoria"));
        p.setUsuarioId(rs.getLong("id_usuario_propietario"));
        return p;
    }

    // --- CREATE ---
    @Override
    public Producto createProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre_producto, descripcion_producto, valor_estimado, id_calidad, id_categoria, id_usuario_propietario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setObject(3, producto.getValorEstimado());
            stmt.setObject(4, producto.getIdCalidad());
            stmt.setLong(5, producto.getCategoriaId());
            if (producto.getUsuarioId() != null) { stmt.setLong(6, producto.getUsuarioId()); }
            else { throw new SQLException("El ID del usuario no puede ser nulo."); }

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) { producto.setId(generatedKeys.getLong(1)); }
            }
            return producto;
        } catch (SQLException e) {
            logger.error("Error SQL al crear producto", e);
            throw new RuntimeException("Error en BD al crear producto", e);
        }
    }

    // --- READ ---
    @Override
    public ArrayList<Producto> findAllProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) { productos.add(mapRowToProducto(rs)); }
        } catch (SQLException e) { logger.error("Error SQL en findAllProductos", e); }
        return productos;
    }

    @Override
    public Producto findById(Long id) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return mapRowToProducto(rs); }
        } catch (SQLException e) { logger.error("Error SQL en findById", e); }
        return null;
    }

    @Override
    public ArrayList<Producto> findByUsuarioId(Long usuarioId) {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE id_usuario_propietario = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { productos.add(mapRowToProducto(rs)); }
            }
        } catch (SQLException e) { logger.error("Error SQL en findByUsuarioId", e); }
        return productos;
    }

    @Override
    public ArrayList<Producto> findByCategoriaId(Long categoriaId) {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE id_categoria = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, categoriaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { productos.add(mapRowToProducto(rs)); }
            }
        } catch (SQLException e) { logger.error("Error SQL en findByCategoriaId", e); }
        return productos;
    }

    // --- UPDATE ---
    @Override
    public Producto updateProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre_producto = ?, descripcion_producto = ?, valor_estimado = ?, id_calidad = ?, id_categoria = ? WHERE id_producto = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setObject(3, producto.getValorEstimado());
            stmt.setObject(4, producto.getIdCalidad());
            stmt.setLong(5, producto.getCategoriaId());
            stmt.setLong(6, producto.getId());
            stmt.executeUpdate();
            return producto;
        } catch (SQLException e) {
            logger.error("Error SQL en updateProducto", e);
            throw new RuntimeException("Error en BD al actualizar", e);
        }
    }

    // --- DELETE ---
    @Override
    public boolean deleteProducto(Long id) {
        // En este caso haremos un borrado real (hard delete)
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error de SQL en deleteProducto", e);
            return false;
        }
    }

    // --- UTILITY ---
    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM productos WHERE id_producto = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { logger.error("Error SQL en existsById", e); }
        return false;
    }

    @Override
    public int countByUsuarioId(Long usuarioId) {
        String sql = "SELECT COUNT(*) FROM productos WHERE id_usuario_propietario = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        } catch (SQLException e) { logger.error("Error SQL en countByUsuarioId", e); }
        return 0;
    }

    @Override
    public int countByCategoriaId(Long categoriaId) {
        String sql = "SELECT COUNT(*) FROM productos WHERE id_categoria = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, categoriaId);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        } catch (SQLException e) { logger.error("Error SQL en countByCategoriaId", e); }
        return 0;
    }
}