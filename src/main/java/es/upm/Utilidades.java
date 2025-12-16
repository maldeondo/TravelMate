package es.upm;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 */
public class Utilidades {
    private static final String NUMBER_MSG = "El número debe estar entre [%d] y [%d].";
    private static final String DOUBLE_MSG = "El número debe estar entre [%.2f] y [%.2f].";
    private static final String ERROR_MSG = "Por favor, introduce un número válido.";

    private static final String FORMAT_MSG = "Formato incorrecto. Usa el formato HH:MM (por ejemplo, 09:30).";
    private static final String HOUR_MSG = "Las horas deben estar entre 00 y 23.";
    private static final String MIN_MSG = "Los minutos deben estar entre 00 y 59.";

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
            
                if (output < minimo || output > maximo) System.out.println(String.format(NUMBER_MSG, minimo, maximo));
                else correct = true; 
                
            } catch (InputMismatchException ex) {
                System.out.println(ERROR_MSG);
                teclado.next();
            }
        } while (!correct);

        return output;
    }

    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        double output = minimo - 1; boolean correct = false;
        
        do {
            try {
                System.out.println(mensaje);
                output = teclado.nextDouble();
            
                if (output < minimo || output > maximo) System.out.println(String.format(NUMBER_MSG, minimo, maximo));
                else correct = true; 
                
            } catch (InputMismatchException ex) {
                System.out.println(ERROR_MSG);
                teclado.next();
            }
        } while (!correct);

        return output;
    }

    public static String leerHora(Scanner teclado, String mensaje) {
        String output = "23:11"; boolean correct = false;
        int h = 0, m = 0;

        do {
            output = teclado.nextLine();

            if ((output.length() != 5) || (output.charAt(2) != ':')) System.out.println(FORMAT_MSG);
            else {
                try {
                    h = Integer.parseInt(output.substring(0, 2));
                    m = Integer.parseInt(output.substring(3, 5));

                    if (h > 23) System.out.println(HOUR_MSG);
                    else if (m > 59) System.out.println(MIN_MSG);
                    else correct = true;

                } catch (NumberFormatException ex) {
                    System.out.println(FORMAT_MSG);
                }
            }
        } while (!correct);

        return output;
    }
    
    // =========================================================================
    // Métodos de conversión de formatos
    // =========================================================================

    // Metodos sin manejo de excepciones porque ya son tratados anteriormente

    public static int horaAMinutos(String hora) {
        String digito1 = hora.split(":")[0];
        String digito2 = hora.split(":")[1];
        int horas = Integer.parseInt(digito1);
        int minutos = Integer.parseInt(digito2);
        return horas * 60 + minutos;
    }
    public static String minutosAHora(int minutos) {
       int digito1 =  minutos / 60;
       int digito2 =  minutos % 60;
        return String.format("%02d:%02d",digito1,digito2);
    }

    public static String formatearDuracion(int duracionMinutos) {
        int digito1 =  duracionMinutos / 60;
        int digito2 =  duracionMinutos % 60;
        String minutos;

        if(duracionMinutos < 60) minutos = String.format("%2dmin", digito2);
        else if(digito2 == 0) minutos = String.format("%dh", digito1);
        else minutos = String.format("%dh %2dmin", digito1, digito2);

        return minutos;
    }

    public static String formatearPrecio(double precio) {
        return String.format("%.2f €", precio);
    }
/* Problema de coherencia: El enunciado me pide que de los valores separando los decimales con puntos
   pero Intel solo reconoce datos de entrada como decimales si van con coma.
 */
    public static double cadenaAPrecio(String precioStr) {
        return Double.parseDouble(precioStr.replace("€","").trim());
    }
}
