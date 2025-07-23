package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Publicacion;
import java.util.ArrayList;

public interface IPublicacionRepository {
    ArrayList<Publicacion> findAll();
    Publicacion findById(Long id);
    ArrayList<Publicacion> findByUsuarioId(Long usuarioId);
    Publicacion save(Publicacion publicacion); // Para INSERT
    Publicacion update(Publicacion publicacion); // Para UPDATE
    boolean delete(Long id);
}