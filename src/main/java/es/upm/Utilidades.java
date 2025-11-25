package es.upm;

import java.util.Scanner;

/**
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 */
public class Utilidades {

    // =========================================================================
    // Métodos de entrada por teclado
    // =========================================================================

    public static String leerCadena(Scanner teclado, String s) {
        // Muestra un mensaje por pantalla y lee una cadena de texto introducida por el usuario
        return null; // @todo MODIFICAR PARA DEVOLVER LA CADENA LEÍDA
    }

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        // Muestra un mensaje y lee un número entero en el rango [minimo, maximo]
        return 0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO LEÍDO
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

    // Metodos sin manejo de excepciones porque ya son tratados anteriormente

    public static int horaAMinutos(String hora) {
        String digito1 = hora.split(":")[0];
        String digito2 = hora.split(":")[1];
        int horas = Integer.parseInt(digito1);
        int minutos = Integer.parseInt(digito2);
        int minutosDesde = horas * 60 + minutos;
        return minutosDesde;
    }
    public static String minutosAHora(int minutos) {
       int digito1 =  minutos / 60;
       int digito2 =  minutos % 60;
        StringBuilder horaEnFormato = new StringBuilder();
        horaEnFormato.append(String.format("%02d:%02d",digito1,digito2));
        return horaEnFormato.toString();
    }

    public static String formatearDuracion(int duracionMinutos) {
        int digito1 =  duracionMinutos / 60;
        int digito2 =  duracionMinutos % 60;
        StringBuilder minutosEnFormato = new StringBuilder();
        if(duracionMinutos < 60) {
            minutosEnFormato.append(String.format("%2dmin", digito2));
        }
        else minutosEnFormato.append(String.format("%2dh %2dmin",digito1,digito2));
        return minutosEnFormato.toString();
    }

    public static String formatearPrecio(double precio) {

        StringBuilder precioEnFormato = new StringBuilder();
        precioEnFormato.append(String.format("%.2f€", precio));
        return precioEnFormato.toString().replace(",",".");
    }
/* Problema de coherencia: El enunciado me pide que de los valores separando los decimales con puntos
   pero Intel solo reconoce datos de entrada como decimales si van con coma.
 */
    public static double cadenaAPrecio(String precioStr) {
        String cadena = precioStr.replace("€","").replace(",",".").trim();
        return Double.parseDouble(cadena);
    }
}
