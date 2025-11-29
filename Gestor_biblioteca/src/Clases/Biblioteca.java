package Clases;

import java.util.ArrayList;

public class Biblioteca {

    private ArrayList<Libro> catalogo;

    public Biblioteca() {
        this.catalogo = new ArrayList<>();
    }

    // --- 1. REGISTRAR (Sin cambios, recibe el objeto ya creado) ---
    public void registrarLibro(Libro nuevoLibro) {
        catalogo.add(nuevoLibro);
        System.out.println("Libro registrado con ID " + nuevoLibro.getId() + ": " + nuevoLibro.getTitulo());
    }

    // --- 2. BUSCAR POR ID (Nuevo y Recomendado) ---
    // Usamos 'int' porque el id es un número
    public Libro buscarLibro(int id) {
        for (Libro libro : catalogo) {
            if (libro.getId() == id) {
                return libro; // Retorna el libro si el ID coincide
            }
        }
        return null; // No existe
    }

    // --- 3. BUSCAR POR TÍTULO (Opcional - Sobrecarga) ---
    // Mantenemos este por si el usuario olvida el ID
    public Libro buscarLibro(String titulo) {
        for (Libro libro : catalogo) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    // --- 4. PRESTAR LIBRO (Actualizado para usar ID) ---
    public void prestarLibro(int id) {
        Libro libro = buscarLibro(id); // Buscamos por ID

        if (libro == null) {
            System.out.println("Error: No existe un libro con el ID " + id);
        } else {
            if (libro.isDisponible()) {
                libro.setDisponible(false);
                System.out.println("Préstamo exitoso: Te llevas '" + libro.getTitulo() + "'");
            } else {
                System.out.println("El libro '" + libro.getTitulo() + "' ya está prestado.");
            }
        }
    }

    // --- 5. DEVOLVER LIBRO (Nuevo) ---
    public void devolverLibro(int id) {
        Libro libro = buscarLibro(id);

        if (libro == null) {
            System.out.println("Error: No existe un libro con el ID " + id);
        } else {
            if (!libro.isDisponible()) { // Si NO está disponible (está prestado)
                libro.setDisponible(true);
                System.out.println("Devolución exitosa: '" + libro.getTitulo() + "' vuelve a estar disponible.");
            } else {
                System.out.println("Este libro ya estaba en la biblioteca (no estaba prestado).");
            }
        }
    }

    // --- 6. MOSTRAR CATÁLOGO ---
    public void mostrarCatalogo() {
        System.out.println("\n--- INVENTARIO DE LA BIBLIOTECA ---");
        if (catalogo.isEmpty()) {
            System.out.println("(Vacío)");
        } else {
            for (Libro libro : catalogo) {
                // Esto usará automáticamente tu nuevo toString() con editora e ID
                System.out.println(libro);
            }
        }
        System.out.println("-----------------------------------\n");
    }

    // --- 7. ELIMINAR LIBRO (Nuevo) ---
    public void eliminarLibro(int id) {
        Libro libro = buscarLibro(id);

        if (libro == null) {
            System.out.println("Error: No se puede eliminar. El libro con ID " + id + " no existe.");
        } else {
            catalogo.remove(libro); // El método .remove() borra el objeto de la lista
            System.out.println("El libro '" + libro.getTitulo() + "' ha sido eliminado del catálogo permanentemente.");
        }
    }
}