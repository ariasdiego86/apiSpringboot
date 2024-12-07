package com.company.books.backend.controllers;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.libro.LibroResponseRest;
import com.company.books.backend.service.libro.ILibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class LibroRestController {


    private final ILibroService service;

    @Autowired
    public LibroRestController(ILibroService service) {
        this.service = service;
    }

    //getAll
    @GetMapping("/libros")
    public ResponseEntity <LibroResponseRest> getLibros(){

        ResponseEntity <LibroResponseRest> response = service.getLibros();
        return response;
    }

    //uso de PathVariable
    @GetMapping("/libros/{id}")
    public ResponseEntity <LibroResponseRest> getLibrosById(@PathVariable Long id){

        ResponseEntity <LibroResponseRest> response = service.getLibrosById(id);
        return response;
    }

    //Insert
    @PostMapping("/libros")
    public ResponseEntity <LibroResponseRest> postLibro (@RequestBody Libro request){

        ResponseEntity <LibroResponseRest> response = service.insertLibro(request);
        return response;
    }

    //Update
    @PutMapping("/libros/{id}")
    public ResponseEntity <LibroResponseRest> putLibro (@RequestBody Libro request, @PathVariable Long id){

        ResponseEntity <LibroResponseRest> response = service.updateLibro(request, id);
        return response;
    }

    //Delete
    @DeleteMapping("/libros/{id}")
    public ResponseEntity <LibroResponseRest> deleteLibro (@PathVariable Long id){

        ResponseEntity <LibroResponseRest> response = service.deleteLibro(id);
        return response;

    }


}
