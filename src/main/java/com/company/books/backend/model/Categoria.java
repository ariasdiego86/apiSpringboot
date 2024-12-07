package com.company.books.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity //será una entidad en la base de datos
@Table(name = "categoria")//si no le ponemos esta anotación, tomará el nombre de la clase en minúsculas
public class Categoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id Autogenerado e incrementado
    private long id;

    @NotNull(message = "no puede ser nulo")
    @NotBlank(message = "no puede estar en blanco")
    @Size(min = 3, max = 40)
    private String nombre;

    @NotNull(message = "no puede ser nulo")
    @NotBlank(message = "no puede estar en blanco")
    @Size(min = 3, max = 40)
    private String descripcion;

    @OneToMany(mappedBy = "categoria")//orphanRemoval = false es default asi
    @JsonManagedReference
    private List<Libro> libros;

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
