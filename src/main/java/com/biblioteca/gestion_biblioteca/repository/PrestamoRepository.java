package com.biblioteca.gestion_biblioteca.repository;

import com.biblioteca.gestion_biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Método mágico: Spring entiende esto y busca por la columna usuario_id
    List<Prestamo> findByUsuarioId(Long usuarioId);

    // Para ver qué libros están prestados actualmente (los que no tienen fecha de devolución)
    List<Prestamo> findByLibroIdAndFechaDevolucionIsNull(Long libroId);
}