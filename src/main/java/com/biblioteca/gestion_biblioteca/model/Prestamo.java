package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate; // Usamos LocalDate para fechas modernas

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FECHAS IMPORTANTES
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion; // Puede ser null si aún no lo devuelve

    // --- RELACIONES (EL PUENTE) ---

    // Muchos préstamos pueden ser de Un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id") // Esto crea la columna de unión en MySQL
    private Usuario usuario;

    // Muchos préstamos pueden ser de Un libro (en diferentes momentos)
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    public Prestamo() {
    }

    public Prestamo(LocalDate fechaPrestamo, Usuario usuario, Libro libro) {
        this.fechaPrestamo = fechaPrestamo;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaDevolucion = null; // Al crear el préstamo, aún no se devuelve
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
}