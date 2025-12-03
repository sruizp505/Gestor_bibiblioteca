package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.model.Prestamo;
import com.biblioteca.gestion_biblioteca.model.Usuario;
import com.biblioteca.gestion_biblioteca.repository.LibroRepository;
import com.biblioteca.gestion_biblioteca.repository.PrestamoRepository;
import com.biblioteca.gestion_biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- MAGIA: CREAR PRÉSTAMO ---
    @Transactional // Si algo falla en medio, deshace todos los cambios (Rollback)
    public Prestamo registrarPrestamo(Long libroId, Long usuarioId) {

        // 1. Buscar el Libro
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // 2. Validar disponibilidad
        if (!libro.isDisponible()) {
            throw new RuntimeException("El libro ya está prestado");
        }

        // 3. Buscar el Usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 4. Crear el Préstamo
        Prestamo prestamo = new Prestamo(LocalDate.now(), usuario, libro);

        // 5. BLOQUEAR EL LIBRO (Actualizar estado)
        libro.setDisponible(false);
        libroRepository.save(libro);

        // 6. Guardar el ticket
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }
}