package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.categorias.request.CreateCategoriaRequest;
import org.devquality.trukea.web.dtos.categorias.response.CreateCategoriaResponse;
import java.util.ArrayList;
import org.devquality.trukea.persistance.entities.Categoria;

public interface ICategoriaService {
    ArrayList<CreateCategoriaResponse> findAll();
    Categoria findById(Long id);

    // 👇 Este método es el nuevo que necesitas
    CreateCategoriaResponse createCategoria(CreateCategoriaRequest request);
}
