package com.company.books.backend.service.libro;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.libro.LibroResponseRest;
import org.springframework.http.ResponseEntity;

public interface ILibroService {

    public ResponseEntity <LibroResponseRest> getLibros();

    public ResponseEntity <LibroResponseRest> getLibrosById(Long id);

    public ResponseEntity <LibroResponseRest> insertLibro(Libro request);

    public ResponseEntity <LibroResponseRest> updateLibro(Libro request, Long id);

    public ResponseEntity <LibroResponseRest> deleteLibro(Long id);

}
