package es.upm;

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
        
        String numbermsg = "El número debe estar entre [%d] y [%d].";
        String errormsg = "Por favor, introduce un número válido.";

        do {
            try {
                System.out.println(mensaje);
                output = teclado.nextInt();
            
                if (output < minimo || output > maximo) System.out.println(String.format(numbermsg, minimo, maximo));
                else correct = true; 
                
            } catch (InputMismatchException ex) {
                System.out.println(errormsg);
                teclado.next();
            }
        } while (!correct);

        return output;
    }

    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        double output = minimo - 1; boolean correct = false;
        
        String numbermsg = "El número debe estar entre [%.2f] y [%.2f].";
        String errormsg = "Por favor, introduce un número válido.";

        do {
            try {
                System.out.println(mensaje);
                output = teclado.nextDouble();
            
                if (output < minimo || output > maximo) System.out.println(String.format(numbermsg, minimo, maximo));
                else correct = true; 
                
            } catch (InputMismatchException ex) {
                System.out.println(errormsg);
                teclado.next();
            }
        } while (!correct);

        return output;
    }

    public static String leerHora(Scanner teclado, String mensaje) {
        String output = "23:11"; boolean correct = false;
        int h = 0, m = 0;

        String formatmsg = "Formato incorrecto. Usa el formato HH:MM (por ejemplo, 09:30).";
        String hourmsg = "Las horas deben estar entre 00 y 23.";
        String minmsg = "Los minutos deben estar entre 00 y 59.";

        do {
            output = teclado.nextLine();

            if ((output.length() != 5) || (output.charAt(2) != ':')) System.out.println(formatmsg);
            else {
                try {
                    h = Integer.parseInt(output.substring(0, 2));
                    m = Integer.parseInt(output.substring(3, 5));

                    if (h > 23) System.out.println(hourmsg);
                    else if (m > 59) System.out.println(minmsg);
                    else correct = true;

                } catch (NumberFormatException ex) {
                    System.out.println(formatmsg);
                }
            }
        } while (!correct);

        return output;
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
