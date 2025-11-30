package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    @Column(name = "editora")
    private String editorial;

    // --- AQUÍ ESTABA EL PROBLEMA ---
    // Agregamos el campo 'disponible' para saber si se puede prestar
    private boolean disponible;

    public Libro() {
    }

    public Libro(String titulo, String autor, String editorial) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.disponible = true; // Por defecto, al crear un libro, está disponible
    }

    // --- GETTERS Y SETTERS (La parte que le faltaba a tu Service) ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    // ¡ESTOS SON LOS QUE BUSCABA EL SERVICIO!
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}