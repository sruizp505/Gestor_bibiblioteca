package Clases;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializamos la biblioteca y el Scanner
        Biblioteca miBiblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0; // Variable para guardar la elección del usuario

        // 2. Cargamos algunos datos de prueba para no empezar vacíos
        miBiblioteca.registrarLibro(new Libro("El Quijote", "Cervantes", "Alfaguara", 101));
        miBiblioteca.registrarLibro(new Libro("1984", "Orwell", "Debolsillo", 102));
        miBiblioteca.registrarLibro(new Libro("Clean Code", "Robert C. Martin", "Prentice", 103));

        // 3. El Bucle del Menú
        do {
            System.out.println("\n---  SISTEMA DE BIBLIOTECA ---");
            System.out.println("1. Ver Catálogo");
            System.out.println("2. Prestar Libro");
            System.out.println("3. Devolver Libro");
            System.out.println("4. Eliminar Libro (NUEVO)");
            System.out.println("5. Salir");
            System.out.print("--> Elige una opción: ");

            // Leemos la opción. Si el usuario escribe texto en vez de número, esto fallaría
            // (pero por ahora asumiremos que escribe números)
            opcion = scanner.nextInt();

            // 4. El Switch (El cerebro de las decisiones)
            switch (opcion) {
                case 1:
                    miBiblioteca.mostrarCatalogo();
                    break; // El break es vital para que no siga ejecutando los casos de abajo

                case 2:
                    System.out.print("Ingresa el ID del libro a PRESTAR: ");
                    int idPrestar = scanner.nextInt();
                    miBiblioteca.prestarLibro(idPrestar);
                    break;

                case 3:
                    System.out.print("Ingresa el ID del libro a DEVOLVER: ");
                    int idDevolver = scanner.nextInt();
                    miBiblioteca.devolverLibro(idDevolver);
                    break;

                case 4:
                    // AQUÍ ESTÁ TU NUEVA FUNCIONALIDAD
                    System.out.print(" ATENCIÓN: Ingresa el ID del libro a ELIMINAR: ");
                    int idEliminar = scanner.nextInt();
                    miBiblioteca.eliminarLibro(idEliminar);
                    break;

                case 5:
                    System.out.println("Cerrando sistema... ¡Hasta luego!");
                    break;

                default:
                    System.out.println(" Opción no válida. Intenta del 1 al 5.");
            }

        } while (opcion != 5); // El programa se repite MIENTRAS la opción NO sea 5

        scanner.close();
    }
}