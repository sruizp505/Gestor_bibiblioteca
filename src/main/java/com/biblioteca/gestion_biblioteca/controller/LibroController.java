package com.biblioteca.gestion_biblioteca.controller;

import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // 1. Indica que esta clase responde vía web (JSON)
@RequestMapping("/api/libros") // 2. La URL base será: http://localhost:8080/api/libros
public class LibroController {

    @Autowired
    private LibroService libroService;

    // --- OBTENER TODOS LOS LIBROS (GET) ---
    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }

    // --- REGISTRAR UN LIBRO (POST) ---
    @PostMapping
    public Libro registrarLibro(@RequestBody Libro libro) {
        // @RequestBody convierte el JSON que envías en un objeto Libro Java
        return libroService.registrarLibro(libro);
    }

    // --- PRESTAR LIBRO (PUT) ---
    // URL ejemplo: /api/libros/prestar/1
    @PutMapping("/prestar/{id}")
    public ResponseEntity<String> prestarLibro(@PathVariable Long id) {
        Libro libroPrestado = libroService.prestarLibro(id);

        if (libroPrestado != null) {
            return ResponseEntity.ok("Éxito: Libro prestado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("Error: Libro no encontrado o no disponible.");
        }
    }

    // --- DEVOLVER LIBRO (PUT) ---
    // URL ejemplo: /api/libros/devolver/1
    @PutMapping("/devolver/{id}")
    public ResponseEntity<String> devolverLibro(@PathVariable Long id) {
        Libro libroDevuelto = libroService.devolverLibro(id);

        if (libroDevuelto != null) {
            return ResponseEntity.ok("Éxito: Libro devuelto.");
        } else {
            return ResponseEntity.badRequest().body("Error: No se pudo devolver el libro.");
        }
    }
}