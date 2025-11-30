package Clases;

import java.util.ArrayList;
import java.sql.*;


public class Biblioteca {

    private ArrayList<Libro> catalogo;

    public Biblioteca() {
        this.catalogo = new ArrayList<>();
    }

    // --- 1. REGISTRAR (Sin cambios, recibe el objeto ya creado) ---
    public void registrarLibro(Libro nuevoLibro) {
        // 1. La orden SQL (los ? son espacios para llenar)
        String sql = "INSERT INTO libros (id, titulo, autor, editora, disponible) VALUES (?, ?, ?, ?, ?)";

        // 2. Conectamos y preparamos la orden
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 3. Rellenamos los huecos (?) con los datos del objeto
            stmt.setInt(1, nuevoLibro.getId());
            stmt.setString(2, nuevoLibro.getTitulo());
            stmt.setString(3, nuevoLibro.getAutor());
            stmt.setString(4, nuevoLibro.getEditora());
            stmt.setBoolean(5, nuevoLibro.isDisponible());

            // 4. ¡Ejecutar!
            stmt.executeUpdate();
            System.out.println(" ¡Libro guardado en la Base de Datos!");

        } catch (SQLException e) {
            System.out.println(" Error al guardar: " + e.getMessage());
        }
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

    // --- MÉTODO ACTUALIZADO: LEER DESDE MYSQL ---
    public void mostrarCatalogo() {
        String sql = "SELECT * FROM libros";

        // Usamos Statement (no PreparedStatement) porque no hay parámetros (?) esta vez
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n---  CATÁLOGO REAL (DESDE MYSQL) ---");

            // Recorremos la tabla fila por fila
            while (rs.next()) {
                // 1. Extraemos los datos de la fila actual
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editora = rs.getString("editora");
                boolean disponible = rs.getBoolean("disponible");

                // 2. Reconstruimos el objeto Libro temporalmente para imprimirlo bonito
                Libro libroTemp = new Libro(titulo, autor, editora, id);
                libroTemp.setDisponible(disponible);

                // 3. Imprimimos usando tu toString()
                System.out.println(libroTemp);
            }
            System.out.println("--------------------------------------\n");

        } catch (SQLException e) {
            System.out.println(" Error al consultar el catálogo: " + e.getMessage());
        }
    }

    // --- 7. ELIMINAR LIBRO ---
    public void eliminarLibro(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 1. Preparamos el ID que queremos borrar
            stmt.setInt(1, id);

            // 2. Ejecutamos y vemos cuántas filas se borraron
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println(" El libro con ID " + id + " fue eliminado de la Base de Datos.");
            } else {
                System.out.println(" No se encontró ningún libro con el ID " + id + ".");
            }

        } catch (SQLException e) {
            System.out.println(" Error al eliminar: " + e.getMessage());
        }
    }
}