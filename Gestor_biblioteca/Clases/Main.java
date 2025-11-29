package Clases;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Biblioteca miBiblioteca = new Biblioteca();

        // 1. Creamos libros con el NUEVO constructor: (Titulo, Autor, Editora, ID)
        Libro l1 = new Libro("El Quijote", "Cervantes", "Alfaguara", 101);
        Libro l2 = new Libro("1984", "George Orwell", "Debolsillo", 102);
        Libro l3 = new Libro("Java a Fondo", "Pablo Augusto", "Marcombo", 103);

        // 2. Los registramos
        miBiblioteca.registrarLibro(l1);
        miBiblioteca.registrarLibro(l2);
        miBiblioteca.registrarLibro(l3);

        // 3. Mostramos el catálogo
        miBiblioteca.mostrarCatalogo();

        // 4. Probamos prestar por ID
        System.out.println("--- Intentando prestar el libro con ID 102 ---");
        miBiblioteca.prestarLibro(102); // Presta '1984'

        // 5. Probamos prestar uno que ya está prestado
        miBiblioteca.prestarLibro(102);

        // 6. Probamos devolver
        System.out.println("\n--- Devolviendo el libro ID 102 ---");
        miBiblioteca.devolverLibro(102);

        // Verificamos estado final
        miBiblioteca.mostrarCatalogo();
    }
}