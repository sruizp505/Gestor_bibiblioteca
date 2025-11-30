package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class Conexion {

        // 1. CONSTANTES DE CONEXIÓN
        // La ruta hacia tu base de datos 'biblioteca'
        private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
        private static final String USER = "root";
        // ↓↓↓ CAMBIA ESTO POR TU CONTRASEÑA REAL ↓↓↓
        private static final String PASS = "root";

        // 2. MÉTODO PARA CONECTAR
        public static Connection conectar() {
            Connection conexion = null;
            try {
                // Esta línea es la que "llama" a la base de datos
                conexion = DriverManager.getConnection(URL, USER, PASS);
                System.out.println(" ¡Conexión establecida con MySQL!");
            } catch (SQLException e) {
                System.out.println(" Error: No se pudo conectar a la base de datos.");
                // Esto imprimirá el error técnico (útil para saber qué pasó)
                e.printStackTrace();
            }
            return conexion;
        }
    }

