package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Publicacion;
import org.devquality.trukea.persistance.repositories.IPublicacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PublicacionRepositoryImpl implements IPublicacionRepository {

    private final DatabaseConfig db;
    private static final Logger log = LoggerFactory.getLogger(PublicacionRepositoryImpl.class);

    public PublicacionRepositoryImpl(DatabaseConfig db) { this.db = db; }

    private Publicacion map(ResultSet rs) throws SQLException {
        return new Publicacion(
                rs.getLong("id_publicacion"),
                rs.getLong("id_producto"),
                rs.getLong("id_usuario"),
                rs.getString("titulo"),
                rs.getString("descripcion_publicacion"),
                rs.getInt("id_ciudad"),
                rs.getTimestamp("fecha_publicacion").toLocalDateTime()
        );
    }

    @Override
    public ArrayList<Publicacion> findAll() {
        String sql = "SELECT * FROM publicaciones";
        ArrayList<Publicacion> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findAll publicaciones", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Publicacion findById(Long id) {
        String sql = "SELECT * FROM publicaciones WHERE id_publicacion = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            log.error("findById publicaciones", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Publicacion> findByUsuarioId(Long usuarioId) {
        String sql = "SELECT * FROM publicaciones WHERE id_usuario = ?";
        ArrayList<Publicacion> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            log.error("findByUsuario publicaciones", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Publicacion save(Publicacion p) {
        String sql = "INSERT INTO publicaciones (id_producto, id_usuario, titulo, descripcion_publicacion, id_ciudad) " +
                "VALUES (?,?,?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, p.getProductoId());
            ps.setLong(2, p.getUsuarioId());
            ps.setString(3, p.getTitulo());
            ps.setString(4, p.getDescripcion());
            if (p.getCiudadId() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, p.getCiudadId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getLong(1));
            }
            p.setFechaPublicacion(LocalDateTime.now());
            return p;
        } catch (SQLException e) {
            log.error("save publicacion", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM publicaciones WHERE id_publicacion = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("delete publicacion", e);
            throw new RuntimeException(e);
        }
    }
}