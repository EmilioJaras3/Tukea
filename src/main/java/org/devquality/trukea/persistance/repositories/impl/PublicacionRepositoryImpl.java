package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Publicacion;
import org.devquality.trukea.persistance.repositories.IPublicacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class PublicacionRepositoryImpl implements IPublicacionRepository {

    private final DatabaseConfig db;
    private static final Logger log = LoggerFactory.getLogger(PublicacionRepositoryImpl.class);

    public PublicacionRepositoryImpl(DatabaseConfig db) {
        this.db = db;
    }

    private Publicacion mapRowToPublicacion(ResultSet rs) throws SQLException {
        Publicacion p = new Publicacion();
        p.setId(rs.getLong("id"));
        p.setUsuarioId(rs.getLong("usuario_id"));
        p.setTitulo(rs.getString("titulo"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setCategoria(rs.getString("categoria"));
        p.setEstado(rs.getString("estado"));
        p.setImagenUrl(rs.getString("imagen_url"));
        p.setZonaSeguraId(rs.getLong("zona_segura_id"));
        // Cuidado: 'fecha_publicacion' puede ser nulo si no es NOT NULL en la tabla
        Timestamp fecha = rs.getTimestamp("fecha_publicacion");
        if (fecha != null) {
            p.setFechaPublicacion(fecha.toLocalDateTime());
        }
        return p;
    }

    @Override
    public ArrayList<Publicacion> findAll() {
        String sql = "SELECT * FROM publicaciones";
        ArrayList<Publicacion> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) list.add(mapRowToPublicacion(rs));
        } catch (SQLException e) {
            log.error("Error en findAll publicaciones", e);
        }
        return list;
    }

    @Override
    public Publicacion findById(Long id) {
        String sql = "SELECT * FROM publicaciones WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRowToPublicacion(rs) : null;
            }
        } catch (SQLException e) {
            log.error("Error en findById publicaciones", e);
        }
        return null;
    }

    @Override
    public ArrayList<Publicacion> findByUsuarioId(Long usuarioId) {
        String sql = "SELECT * FROM publicaciones WHERE usuario_id = ?";
        ArrayList<Publicacion> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowToPublicacion(rs));
            }
        } catch (SQLException e) {
            log.error("Error en findByUsuarioId publicaciones", e);
        }
        return list;
    }

    @Override
    public Publicacion save(Publicacion p) {
        String sql = "INSERT INTO publicaciones (usuario_id, titulo, descripcion, categoria, estado, imagen_url, zona_segura_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, p.getUsuarioId());
            ps.setString(2, p.getTitulo());
            ps.setString(3, p.getDescripcion());
            ps.setString(4, p.getCategoria());
            ps.setString(5, p.getEstado());
            ps.setString(6, p.getImagenUrl());
            ps.setObject(7, p.getZonaSeguraId(), Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getLong(1));
            }
            // Después de guardar, consultamos el objeto de nuevo para obtener la fecha de la BD
            return findById(p.getId());
        } catch (SQLException e) {
            log.error("Error al guardar la publicación", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Publicacion update(Publicacion p) {
        String sql = "UPDATE publicaciones SET usuario_id = ?, titulo = ?, descripcion = ?, categoria = ?, estado = ?, imagen_url = ?, zona_segura_id = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, p.getUsuarioId());
            ps.setString(2, p.getTitulo());
            ps.setString(3, p.getDescripcion());
            ps.setString(4, p.getCategoria());
            ps.setString(5, p.getEstado());
            ps.setString(6, p.getImagenUrl());
            ps.setObject(7, p.getZonaSeguraId(), Types.INTEGER);
            ps.setLong(8, p.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return findById(p.getId());
            }
            return p;
        } catch (SQLException e) {
            log.error("Error al actualizar la publicación", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM publicaciones WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Error al eliminar la publicación", e);
        }
        return false;
    }
}