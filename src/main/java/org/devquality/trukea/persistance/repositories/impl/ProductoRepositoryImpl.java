package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.devquality.trukea.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Implementación de IProductoRepository adaptada a la tabla publicaciones.
 */
public class ProductoRepositoryImpl implements IProductoRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductoRepositoryImpl.class);
    private final DatabaseConfig databaseConfig;

    public ProductoRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    private Connection getConnection() throws SQLException {
        return databaseConfig.getConnection();
    }

    // --- HELPER DE MAPEO ---
    private Producto mapRowToProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setUsuarioId(rs.getLong("usuario_id"));
        p.setTitulo(rs.getString("titulo"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setCategoria(rs.getString("categoria"));
        p.setEstado(rs.getString("estado"));
        p.setImagenUrl(rs.getString("imagen_url"));
        p.setZonaSeguraId(rs.getObject("zona_segura_id") != null ? rs.getLong("zona_segura_id") : null);
        Timestamp ts = rs.getTimestamp("fecha_publicacion");
        if (ts != null) {
            p.setFechaPublicacion(ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        return p;
    }

    // --- CREATE ---
    @Override
    public Producto createProducto(Producto producto) {
        String sql = "INSERT INTO publicaciones (usuario_id, titulo, descripcion, categoria, estado, imagen_url, zona_segura_id, fecha_publicacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, producto.getUsuarioId());
            stmt.setString(2, producto.getTitulo());
            stmt.setString(3, producto.getDescripcion());
            stmt.setString(4, producto.getCategoria());
            stmt.setString(5, producto.getEstado());
            stmt.setString(6, producto.getImagenUrl());
            if (producto.getZonaSeguraId() != null) stmt.setLong(7, producto.getZonaSeguraId());
            else stmt.setNull(7, Types.BIGINT);
            if (producto.getFechaPublicacion() != null) stmt.setTimestamp(8, Timestamp.valueOf(producto.getFechaPublicacion()));
            else stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) { producto.setId(generatedKeys.getLong(1)); }
            }
            return producto;
        } catch (SQLException e) {
            logger.error("Error SQL al crear publicación", e);
            throw new RuntimeException("Error en BD al crear publicación", e);
        }
    }

    // --- READ ---
    @Override
    public ArrayList<Producto> findAllProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM publicaciones";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) { productos.add(mapRowToProducto(rs)); }
        } catch (SQLException e) { logger.error("Error SQL en findAllProductos", e); }
        return productos;
    }

    @Override
    public Producto findById(Long id) {
        String sql = "SELECT * FROM publicaciones WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return mapRowToProducto(rs); }
        } catch (SQLException e) { logger.error("Error SQL en findById", e); }
        return null;
    }

    @Override
    public ArrayList<Producto> findByUsuarioId(Long usuarioId) {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM publicaciones WHERE usuario_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { productos.add(mapRowToProducto(rs)); }
            }
        } catch (SQLException e) { logger.error("Error SQL en findByUsuarioId", e); }
        return productos;
    }

    @Override
    public ArrayList<Producto> findByCategoria(String categoria) {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM publicaciones WHERE categoria = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { productos.add(mapRowToProducto(rs)); }
            }
        } catch (SQLException e) { logger.error("Error SQL en findByCategoria", e); }
        return productos;
    }

    // --- UPDATE ---
    @Override
    public Producto updateProducto(Producto producto) {
        String sql = "UPDATE publicaciones SET usuario_id = ?, titulo = ?, descripcion = ?, categoria = ?, estado = ?, imagen_url = ?, zona_segura_id = ?, fecha_publicacion = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, producto.getUsuarioId());
            stmt.setString(2, producto.getTitulo());
            stmt.setString(3, producto.getDescripcion());
            stmt.setString(4, producto.getCategoria());
            stmt.setString(5, producto.getEstado());
            stmt.setString(6, producto.getImagenUrl());
            if (producto.getZonaSeguraId() != null) stmt.setLong(7, producto.getZonaSeguraId());
            else stmt.setNull(7, Types.BIGINT);
            if (producto.getFechaPublicacion() != null) stmt.setTimestamp(8, Timestamp.valueOf(producto.getFechaPublicacion()));
            else stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(9, producto.getId());
            stmt.executeUpdate();
            return producto;
        } catch (SQLException e) {
            logger.error("Error SQL en updateProducto", e);
            throw new RuntimeException("Error en BD al actualizar publicación", e);
        }
    }

    // --- DELETE ---
    @Override
    public boolean deleteProducto(Long id) {
        String sql = "DELETE FROM publicaciones WHERE id = ?";
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
        String sql = "SELECT 1 FROM publicaciones WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { logger.error("Error SQL en existsById", e); }
        return false;
    }

    @Override
    public int countByUsuarioId(Long usuarioId) {
        String sql = "SELECT COUNT(*) FROM publicaciones WHERE usuario_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        } catch (SQLException e) { logger.error("Error SQL en countByUsuarioId", e); }
        return 0;
    }

    @Override
    public int countByCategoria(String categoria) {
        String sql = "SELECT COUNT(*) FROM publicaciones WHERE categoria = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        } catch (SQLException e) { logger.error("Error SQL en countByCategoria", e); }
        return 0;
    }
}