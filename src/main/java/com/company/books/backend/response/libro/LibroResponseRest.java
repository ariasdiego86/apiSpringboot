package com.company.books.backend.response.libro;

import com.company.books.backend.response.ResponseRest;

public class LibroResponseRest extends ResponseRest {

    private LibroResponse libroResponse = new LibroResponse();

    public LibroResponse getLibroResponse() {
        return libroResponse;
    }

    public void setLibroResponse(LibroResponse libroResponse) {
        this.libroResponse = libroResponse;
    }


}
