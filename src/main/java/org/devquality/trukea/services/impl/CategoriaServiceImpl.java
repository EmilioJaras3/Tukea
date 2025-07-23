package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.persistance.repositories.ICategoriaRepository;
import org.devquality.trukea.services.ICategoriaService;
import org.devquality.trukea.web.dtos.categorias.requests.CreateCategoriaRequest;
import org.devquality.trukea.web.dtos.categorias.responses.CreateCategoriaResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CategoriaServiceImpl implements ICategoriaService {

    private final ICategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ArrayList<CreateCategoriaResponse> findAll() {
        ArrayList<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(c -> new CreateCategoriaResponse(
                        c.getId() != null ? c.getId().intValue() : null,    // Long → Integer, null safe
                        c.getNombre(),           // String
                        null                     // descripcionCategoria (tu entidad no lo tiene)
                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public CreateCategoriaResponse createCategoria(CreateCategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getCategoria());
        // Tu entidad no tiene descripcionCategoria, así que solo usamos nombre

        Categoria nueva = categoriaRepository.save(categoria);

        return new CreateCategoriaResponse(
                nueva.getId() != null ? nueva.getId().intValue() : null,    // Long → Integer, null safe
                nueva.getNombre(),           // String
                request.getDescripcionCategoria()  // Del request, ya que la entidad no lo guarda
        );
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id);
    }
}