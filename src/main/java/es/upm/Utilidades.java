package es.upm;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 *
 * @author Mario Aldeondo
 * @author Robert Voong
 * @version 1.0
 */
public class Utilidades {
    private static final String NUMBER_MSG = "El número debe estar entre [%d] y [%d].";
    private static final String DOUBLE_MSG = "El número debe estar entre [%.2f] y [%.2f].";
    private static final String ERROR_MSG = "Por favor, introduce un número válido.";

    private static final String FORMAT_MSG = "Formato incorrecto. Usa el formato HH:MM (por ejemplo, 09:30).";
    private static final String HOUR_MSG = "Las horas deben estar entre 00 y 23.";
    private static final String MIN_MSG = "Los minutos deben estar entre 00 y 59.";



    /**
     * Metodo que imprime por pantalla un mensaje (mensaje) para pedir una cadena de texto
     *
     * @param teclado objeto de la clase Scanner para la lectura de teclado
     * @param mensaje       mensaje que se imprime para pedir la cadena de texto
     * @return        la cadena de texto leida del teclado
     */
    public static String leerCadena(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
      
        return teclado.nextLine();
    }

    /**
     * Metodo que imprime por pantalla un mensaje para pedir un numero comprendido entre
     * los valores permitidos, si no lo esta se muestran mensajes de error
     *
     * @param teclado El objeto de la clase Scanner para la lectura de teclado
     * @param mensaje El mendaje que que se imprime para pedir el numero por pantalla
     * @param minimo El valor minimo que puede tomar el numero aportado
     * @param maximo El valor maximo que puede tomar el numero aportado
     * @return El numero leido por teclado
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int output = minimo - 1; boolean correct = false;
        
        do {
            try {
                System.out.print(mensaje);
                output = teclado.nextInt();
                teclado.nextLine();
            
                if (output < minimo || output > maximo) System.out.println(String.format(NUMBER_MSG, minimo, maximo));
                else correct = true; 
                
            } catch (InputMismatchException ex) {
                System.out.println(ERROR_MSG);
                teclado.nextLine();
            }
        } while (!correct);

        return output;
    }

    /**
     * Metodo que imprime un mensaje por pantalla para pedir un numero con decimales comprendido
     * entre los valores permitidos, en caso de no estarlo se muestran mensajes de error
     *
     * @param teclado Objeto de la clase Scanner para la lectura de teclado
     * @param mensaje Mensaje que se imprime por pantalla para pedir el numero
     * @param minimo Valor minimo que puede tomar el numero proporcionado
     * @param maximo Valor maximo que puede tomar el numero proporcionado
     * @return El numero con decimales
     */
    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        double output = minimo - 1; boolean correct = false;
        
        do {
            try {
                System.out.print(mensaje);
                output = teclado.nextDouble();
                teclado.nextLine();

                if (output < minimo || output > maximo) System.out.println(String.format(DOUBLE_MSG, minimo, maximo));
                else correct = true; 
                
            } catch (InputMismatchException ex) {
                System.out.println(ERROR_MSG);
                teclado.nextLine();
            }
        } while (!correct);

        return output;
    }

    /**
     * Metodo que imprime un mensaje por pantalla para pedir una hora, si la hora no se da en el formato
     * adecuado o esta fuera de las posibles en un dia se muestran mensajes de error
     *
     * @param teclado Objeto de la clase Scanner para la lectura de teclado
     * @param mensaje Mensaje que se imprime por pantalla para pedir la hora
     * @return La hora proporcionada con el formato adecuado
     */
    public static String leerHora(Scanner teclado, String mensaje) {
        String output; boolean correct = false;
        int h = 0, m = 0;
        System.out.println(mensaje);

        do {
            System.out.print(mensaje);
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
                    teclado.nextLine();
                }
            }
        } while (!correct);

        return output;
    }

    /**
     * Metodo que recibe una hora en formato HH:MM y devuelve los minutos que han pasado
     * desde las 00:00 hasta la hora proporcionada
     *
     * @param hora La hora en formato HH:MM
     * @return     El valor de la hora en cantidad de minutos
     */
    public static int horaAMinutos(String hora) {
        String digito1 = hora.split(":")[0];
        String digito2 = hora.split(":")[1];

        return (Integer.parseInt(digito1) * 60) + Integer.parseInt(digito2);
    }

    /**
     * Metodo que recibe una cantidad de minutos y devuelve la hora equivalente en
     * formato HH:MM
     *
     * @param minutos los minutos que han transcurrido desde las 00:00
     * @return la hora correspondiente en formato HH:MM
     */
    public static String minutosAHora(int minutos) {
       int digito1 =  minutos / 60;
       int digito2 =  minutos % 60;
    
       return String.format("%02d:%02d", digito1, digito2);
    }

    /**
     * Metodo que recibe una duracion en minutos y la devuelve en horas
     *
     * @param duracionMinutos La duracion en minutos
     * @return  La duracion en horas
     */
    public static String formatearDuracion(int duracionMinutos) {
        int digito1 =  duracionMinutos / 60;
        int digito2 =  duracionMinutos % 60;
        String minutos;

        if(duracionMinutos < 60) minutos = String.format("%2dmin", digito2);
        else if(digito2 == 0) minutos = String.format("%dh", digito1);
        else minutos = String.format("%dh %2dmin", digito1, digito2);

        return minutos;
    }

    /**
     * Recibe el precio de una actividad como un double y lo devuelve con formato de precio con €
     *
     * @param precio El valor del precio en numero
     * @return El precio con formato *0.00€
     */
    public static String formatearPrecio(double precio) {
        return String.format("%.2f €", precio);
    }

    /**
     * Metodo que recibe como parametro un precio con formato *0.00€ y devuelve su valor
     * como un double
     *
     * @param precioStr El precio con el formato *0.00€
     * @return El valor en forma de double
     */
    public static double cadenaAPrecio(String precioStr) {
        return Double.parseDouble(precioStr.replace("€","").trim());
    }
}
