package org.devquality.trukea.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.ZonaSegura;
import org.devquality.trukea.persistance.entities.ZonaSeguraHorario;
import org.devquality.trukea.repositories.IZonaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZonaRepositoryImpl implements IZonaRepository {

    @Override
    public List<ZonaSegura> obtenerTodasLasZonas() {
        List<ZonaSegura> lista = new ArrayList<>();
        String sql = "SELECT * FROM zona_segura";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ZonaSegura zona = new ZonaSegura();
                zona.setIdZona(rs.getLong("id_zona"));
                zona.setNombreZona(rs.getString("nombre_zona"));
                zona.setDireccion(rs.getString("direccion"));
                lista.add(zona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<ZonaSeguraHorario> obtenerHorariosPorZona(Long idZona) {
        List<ZonaSeguraHorario> lista = new ArrayList<>();
        String sql = "SELECT * FROM zona_segura_horario WHERE id_zona = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idZona);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ZonaSeguraHorario h = new ZonaSeguraHorario();
                h.setIdHorario(rs.getLong("id_horario"));
                h.setDiaDeSemana(rs.getString("dia_de_semana"));
                h.setHoraApertura(rs.getString("hora_apertura"));
                h.setHoraCierre(rs.getString("hora_cierre"));
                h.setIdZona(rs.getLong("id_zona"));
                lista.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void crearZona(ZonaSegura zona) {
        String sql = "INSERT INTO zona_segura (nombre_zona, direccion) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, zona.getNombreZona());
            stmt.setString(2, zona.getDireccion());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
