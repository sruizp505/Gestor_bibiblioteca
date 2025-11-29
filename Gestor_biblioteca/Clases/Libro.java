package Clases;

// CORRECCIÓN 1: Nombre de la clase en SINGULAR (Convención)
// Una clase es el plano para UN libro. Una lista guarda "Libros", la clase es "Libro".
public class Libro {

    // CORRECCIÓN 2: Atributos 'private'
    private String titulo;
    private String autor;
    private String editora;
    private int id;
    private boolean disponible;

    public Libro() {
    }

    // CORRECCIÓN 3: Llenar el constructor
    public Libro(String titulo, String autor, String editora, int id) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.id = id;
        this.disponible = true; // Importante: Por defecto el libro nace disponible
    }

    // Getters y Setters (Estaban bien, solo actualizados a private)
    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // CORRECCIÓN 4: Personalizar toString
    @Override
    public String toString() {
        String estado = disponible ? "Disponible" : "Prestado";
        return "ID: " + id + " | " + titulo + " (" + autor + ") - " + editora + " [" + estado + "]";
    }
}