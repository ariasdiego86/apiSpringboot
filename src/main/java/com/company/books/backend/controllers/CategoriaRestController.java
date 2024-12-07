package com.company.books.backend.controllers;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//inidcamos que es un controlador
@RestController
@RequestMapping("/v1")//"localhost80:80/v1
public class CategoriaRestController {


    private final ICategoriaService service;

    //"inyectamos" la dependencia
    @Autowired
    public CategoriaRestController(ICategoriaService service){
        this.service = service;
    }

    //"localhost80:80/v1/categorias  indicamos también que es de tipo Get
    @GetMapping("/categorias")
    public ResponseEntity<CategoriaResponseRest> consultarCategoria(){

        ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();

        return response;
    }

    //uso de PathVariable
    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> getCategoriaById(@PathVariable Long id){

        ResponseEntity<CategoriaResponseRest> response = service.buscarCategoriasById(id);
        return response;

    }

    //Insert
    @PostMapping("/categorias")                                                     //categoria
    public ResponseEntity<CategoriaResponseRest> crearCategoria(@RequestBody Categoria request){
                                                                                //categoria
        ResponseEntity<CategoriaResponseRest> response = service.insertCategoria(request);
        return response;
    }

    //Update
    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> actualizarCategoria(@RequestBody Categoria request, @PathVariable Long id){

        ResponseEntity<CategoriaResponseRest> response = service.updateCategoria(request, id);
        return response;
    }

    //Delete
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> eliminarCategoria(@PathVariable Long id){

        ResponseEntity<CategoriaResponseRest> response = service.deleteCategoria(id);
        return response;
    }

}
