package com.company.books.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "libros")
public class Libro implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Id autogenerado e incrementado
    private long id;

    @NotNull(message = "no puede ser nulo")
    @NotBlank(message = "no puede estar en blanco")
    @Size(min = 3, max = 40)
    private String titulo;

    @NotNull(message = "no puede ser nulo")
    @NotBlank(message = "no puede estar en blanco")
    @Size(min = 3, max = 40)
    private String descripcion;

    @NotNull(message = "no puede ser nulo")
    @NotBlank(message = "no puede estar en blanco")
    @Size(min = 3, max = 40)
    private String autor;//hacerlo con Many to One después si es que quiero.

    //Establecemos que será una relacion de muchos a uno
    //Lazy es para especificar cómo se cargará esta relación desde la base de datos.
    /*
    *   La relación no se carga inmediatamente cuando se recupera la entidad principal de la base de datos.
        En lugar de cargar automáticamente los datos relacionados, se crea un proxy que los carga sólo cuando son necesarios.
        Es útil para optimizar el rendimiento al evitar la carga innecesaria de datos relacionados.
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})//si no encunetra un categoria asocicada a libro lo ignorará a nivel de JSON
    private Categoria categoria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


}
