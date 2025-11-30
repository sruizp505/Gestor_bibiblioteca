package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Esto le dice a Spring: "Aquí está la lógica de negocio"
public class LibroService {

    // Inyectamos el Repositorio (es como tener tu antigua clase Conexion lista para usar)
    @Autowired
    private LibroRepository libroRepository;

    // --- 1. REGISTRAR (Equivalente a tu insertarLibro) ---
    public Libro registrarLibro(Libro libro) {
        // .save() hace el INSERT INTO automáticamente
        return libroRepository.save(libro);
    }

    // --- 2. MOSTRAR CATÁLOGO (Equivalente a tu mostrarCatalogo) ---
    public List<Libro> obtenerTodos() {
        // .findAll() hace el SELECT * FROM libros
        return libroRepository.findAll();
    }

    // --- 3. BUSCAR POR ID ---
    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    // --- 4. PRESTAR LIBRO (Lógica migrada de tu Biblioteca.java) ---
    public Libro prestarLibro(Long id) {
        // 1. Buscamos el libro
        Libro libro = libroRepository.findById(id).orElse(null);

        if (libro != null && libro.isDisponible()) {
            // 2. Cambiamos el estado
            libro.setDisponible(false);
            // 3. Guardamos los cambios (Spring detecta que ya existe y hace un UPDATE)
            return libroRepository.save(libro);
        }
        return null; // O podrías lanzar una excepción si no se puede prestar
    }

    // --- 5. DEVOLVER LIBRO ---
    public Libro devolverLibro(Long id) {
        Libro libro = libroRepository.findById(id).orElse(null);

        if (libro != null && !libro.isDisponible()) {
            libro.setDisponible(true);
            return libroRepository.save(libro);
        }
        return null;
    }

    // --- 6. ELIMINAR LIBRO ---
    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }
}