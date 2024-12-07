package com.company.books.backend.service;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoriaService {

    public ResponseEntity <CategoriaResponseRest> buscarCategorias();

    public ResponseEntity <CategoriaResponseRest> buscarCategoriasById(Long id);

    public ResponseEntity <CategoriaResponseRest> insertCategoria(Categoria request);

    public ResponseEntity <CategoriaResponseRest> updateCategoria(Categoria request, Long id);

    public ResponseEntity <CategoriaResponseRest> deleteCategoria(Long id);

}
