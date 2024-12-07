package com.company.books.backend.response.libro;

import com.company.books.backend.model.Libro;

import java.util.List;

public class LibroResponse {

    private List <Libro> libro;

    public List<Libro> getLibro() { return libro; }

    public void setLibro(List<Libro> libro) { this.libro = libro; }
}
