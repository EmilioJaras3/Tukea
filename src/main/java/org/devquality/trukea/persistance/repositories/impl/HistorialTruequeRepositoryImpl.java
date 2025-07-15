package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.HistorialTrueque;
import org.devquality.trukea.persistance.repositories.IHistorialTruequeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HistorialTruequeRepositoryImpl implements IHistorialTruequeRepository {

    private final DatabaseConfig db;
    private static final Logger log = LoggerFactory.getLogger(HistorialTruequeRepositoryImpl.class);

    public HistorialTruequeRepositoryImpl(DatabaseConfig db) {
        this.db = db;
    }

    private HistorialTrueque map(ResultSet rs) throws SQLException {
        HistorialTrueque h = new HistorialTrueque();
        h.setId(rs.getLong("id"));
        h.setTruequeId(rs.getLong("trueque_id"));
        h.setProductoOfrecidoId(rs.getLong("producto_ofrecido_id"));
        h.setProductoDeseadoId(rs.getLong("producto_deseado_id"));
        h.setUsuarioOfrecidoId(rs.getLong("usuario_ofrecido_id"));
        h.setUsuarioRecibidoId(rs.getLong("usuario_recibido_id"));
        h.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        return h;
    }

    @Override
    public ArrayList<HistorialTrueque> findAll() {
        String sql = "SELECT * FROM historial_trueques";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<HistorialTrueque> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;

        } catch (SQLException e) {
            log.error("findAll historial", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<HistorialTrueque> findByUsuarioId(Long uid) {
        String sql = """
                SELECT * FROM historial_trueques
                WHERE usuario_ofrecido_id = ? OR usuario_recibido_id = ?
                """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, uid);
            ps.setLong(2, uid);
            ResultSet rs = ps.executeQuery();

            ArrayList<HistorialTrueque> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;

        } catch (SQLException e) {
            log.error("findByUsuarioId", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public HistorialTrueque findById(Long id) {
        String sql = "SELECT * FROM historial_trueques WHERE id = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;

        } catch (SQLException e) {
            log.error("findById historial", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public HistorialTrueque save(HistorialTrueque h) {
        String sql = """
            INSERT INTO historial_trueques
            (trueque_id, producto_ofrecido_id, producto_deseado_id,
             usuario_ofrecido_id, usuario_recibido_id, fecha)
            VALUES (?,?,?,?,?,?)
            """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, h.getTruequeId());
            ps.setLong(2, h.getProductoOfrecidoId());
            ps.setLong(3, h.getProductoDeseadoId());
            ps.setLong(4, h.getUsuarioOfrecidoId());
            ps.setLong(5, h.getUsuarioRecibidoId());
            ps.setTimestamp(6, Timestamp.valueOf(h.getFecha()));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) h.setId(keys.getLong(1));
            }
            return h;

        } catch (SQLException e) {
            log.error("save historial", e);
            throw new RuntimeException(e);
        }
    }
}
