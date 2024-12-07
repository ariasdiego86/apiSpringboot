package com.company.books.backend.model.dao;

import com.company.books.backend.model.Libro;
import org.springframework.data.repository.CrudRepository;

public interface ILibroDao extends CrudRepository<Libro, Long> {

}
