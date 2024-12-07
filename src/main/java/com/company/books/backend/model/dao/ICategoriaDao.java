package com.company.books.backend.model.dao;

import com.company.books.backend.model.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface ICategoriaDao extends CrudRepository<Categoria, Long> {

}
