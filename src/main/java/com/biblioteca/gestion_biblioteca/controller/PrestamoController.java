package com.biblioteca.gestion_biblioteca.controller;

import com.biblioteca.gestion_biblioteca.model.Prestamo;
import com.biblioteca.gestion_biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // --- GET: Ver todos los préstamos ---
    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoService.listarTodos();
    }

    // --- POST: Crear un nuevo préstamo ---
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequest request) {
        try {
            Prestamo nuevoPrestamo = prestamoService.registrarPrestamo(request.getLibroId(), request.getUsuarioId());
            return ResponseEntity.ok(nuevoPrestamo);
        } catch (RuntimeException e) {
            // Si el libro no existe o está ocupado, devolvemos error 400
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- CLASE AUXILIAR (DTO) PARA RECIBIR EL JSON ---
    // Solo sirve para capturar los IDs que envía el usuario/frontend
    public static class PrestamoRequest {
        private Long libroId;
        private Long usuarioId;

        // Getters y Setters necesarios
        public Long getLibroId() { return libroId; }
        public void setLibroId(Long libroId) { this.libroId = libroId; }
        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    }

    // --- PUT: Devolver un libro ---
    // URL: /api/prestamos/devolver/5 (donde 5 es el ID del libro)
    @PutMapping("/devolver/{libroId}")
    public ResponseEntity<?> devolverLibro(@PathVariable Long libroId) {
        try {
            prestamoService.registrarDevolucion(libroId);
            return ResponseEntity.ok("Libro devuelto con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}