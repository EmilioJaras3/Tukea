package org.devquality.trukea.persistance.repositories;
import org.devquality.trukea.persistance.entities.Calidad;

import java.util.ArrayList;

public interface ICalidadRepository {
    ArrayList<Calidad> findAll();
    Calidad findById(Integer id);
    Calidad save(Calidad calidad);
    boolean delete(Integer id);
}