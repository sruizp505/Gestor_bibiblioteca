package com.biblioteca.gestion_biblioteca.repository;

import com.biblioteca.gestion_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    // ¡NO ESCRIBAS NADA AQUÍ!
    // Al extender de JpaRepository, Spring crea automáticamente el código para:
    // .save() -> Guardar
    // .findAll() -> Buscar todos
    // .findById() -> Buscar por ID
    // .deleteById() -> Borrar
}