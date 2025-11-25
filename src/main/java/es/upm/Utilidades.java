package es.upm;

import java.io.IO;
import java.io.IOError;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 */
public class Utilidades {

    // =========================================================================
    // Métodos de entrada por teclado
    // =========================================================================

    public static String leerCadena(Scanner teclado, String s) {
        System.out.println(s);

        return teclado.nextLine();
    }

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int output = minimo - 1; boolean correct = false;

        do {
            try {
                System.out.println(mensaje);
                output = teclado.nextInt();
            
                if (output < minimo || output > maximo) System.out.println("Número no válido.");
                else correct = true; 
                
            } catch (InputMismatchException ex) {
                System.out.println("Entrada no válida.");
                teclado.next();
            }
            
        } while (!correct);

        return output;
    }

    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        // Muestra un mensaje y lee un número decimal en el rango [minimo, maximo]
        return 0.0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO LEÍDO
    }

    public static String leerHora(Scanner teclado, String mensaje) {
        // Muestra un mensaje y lee una hora en formato "HH:MM"
        return null; // @todo MODIFICAR PARA DEVOLVER LA HORA LEÍDA
    }

    // =========================================================================
    // Métodos de conversión de formatos
    // =========================================================================

    public static int horaAMinutos(String hora) {
        // Convierte una hora en formato "HH:MM" a minutos desde medianoche
        return 0; // @todo MODIFICAR PARA DEVOLVER LOS MINUTOS
    }

    public static String minutosAHora(int minutos) {
        // Convierte minutos desde medianoche a formato "HH:MM"
        return null; // @todo MODIFICAR PARA DEVOLVER LA HORA EN FORMATO HH:MM
    }

    public static String formatearDuracion(int duracionMinutos) {
        // Formatea una duración en minutos a formato legible (ej: 90 -> "1h 30min")
        return null; // @todo MODIFICAR PARA DEVOLVER LA DURACIÓN FORMATEADA
    }

    public static String formatearPrecio(double precio) {
        // Formatea un precio a formato legible (ej: 12.50 -> "12.50 €")
        return null; // @todo MODIFICAR PARA DEVOLVER EL PRECIO FORMATEADO
    }

    public static double cadenaAPrecio(String precioStr) {
        // Convierte una cadena con precio (ej: "12.50 €") a double
        return 0.0; // @todo MODIFICAR PARA DEVOLVER EL PRECIO COMO DOUBLE
    }
}
