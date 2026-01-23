package es.upm;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Actividad es una clase que tiene el  comportamiento de una actividad dentro de un dia,
 * la clase encapsula metodos para añadir elementos propios de un actividad como el nombre,
 * la descripcion, el precio, la duracion, los recursos y los comentarios; asi como metodos
 * para mostrar estos elementos. Tambien contiene metodos toString y toRawString para mostrar
 * los contenidos de la actividad con un formato especifico y un metodo fromBufferedReader para
 * leer el contenido de un archivo y crear una actividad a partir de los elementos del archivo
 *
 * @author Mario Aldeondo
 * @author Robert Voong
 * @version 1.0
 */
public class Actividad {

    /**
     * Nombre de la actividad.
     */
    private String nombre = "";
    /**
     * Descripcion de la actividad
     */
    private String descripcion = "";

    /**
     * Array con los recursos de la actividad
     */
    private String[] recursos;
    /**
     * Array con los comentarios de la actividad
     */
    private String[] comentarios;

    /**
     * Duracion en minutos de la actividad
     */
    private int duracionMinutos = 0;
    /**
     * Numero maximo de recursos de la actividad
     */
    private int maxRecursos = 0;
    /**
     * Numero maximo de comentarios de la actividad
     */
    private int maxComentarios = 0;
    /**
     * Coste de la actividad
     */
    private double precio = 0.0;


    /**
     * Numero de recursos agregados hasta el momento
     */
    private int actRecursos = 0;
    /**
     * Numero de comentarios agregados hasta el momento
     */
    private int actComentarios = 0;

    /**
     * Cadena a partir de la cual estan separados los recursos y comentarios se
     * usa en la lectura de una actividad dentro de un archivo
     */
    private static final String FIRST_SEPARATOR = "COMENTARIOS";
    /**
     * Cadena que se usa en la lectura de una actividad para determinar
     * que se acaba la seccion de comentarios
     */
    private static final String SECOND_SEPARATOR = "-----";

    // Constantes de códigos de error

    /**
     * Constante con el valor una operacion exitosa
     */
    public static final int EXITO = 0;
    /**
     * Constante con el valor de una operacion fallida por ser un valor invlaido
     */
    public static final int ERROR_VALOR_INVALIDO = 1;
    /**
     * Constante con el valor de una operacion fallida por no caber mas recursos
     */
    public static final int ERROR_RECURSOS_COMPLETOS = 2;
    /**
     * Constante con el valor de una operacion fallida por no caber mas comentarios
     */
    public static final int ERROR_COMENTARIOS_COMPLETOS = 3;

    /**
     * Constructor de la clase Actividad para que siempre tenga
     * nombre y un numero maximo de recursos y comentarios, si el
     * numero maximo que se intenta dar es negativo se muestra un mensaje
     *
     * @param nombre Nombre de la actividad
     * @param maxRecursos Numero maximo de recursos
     * @param maxComentarios Numero maximo de comentarios
     */
    public Actividad(String nombre, int maxRecursos, int maxComentarios) {
        if (maxRecursos >= 0 && maxComentarios >= 0) {
            this.nombre = nombre; 
            this.maxRecursos = maxRecursos; 
            this.maxComentarios = maxComentarios;

            recursos = new String[maxRecursos];
            comentarios = new String[maxComentarios];
        } else System.out.println("Valores negativos.");
    }

    /**
     * Metodo que devuelve el nombre de la actividad
     *
     * @return String con el nombre de la actividad
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve la descripcion de la actividad
     *
     * @return String con la descripcion de la actividad
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Metodo para añadirle la descripcion a la actividad
     *
     * @param descripcion Descripcion de la actividad
     */
    public void setDescripcion(String descripcion) {
        if (!voidChars(descripcion)) this.descripcion = descripcion; 
    }

    /**
     * Devuelve el precio de la actividad
     *
     * @return Double del precio de la actividad
     */
    public double getPrecio() { return precio; }

    /**
     * Metodo para establecer el precio de la actividad
     *
     * @param precio Precio de la actividad
     */
    public void setPrecio(double precio) {
        if (precio >= 0) this.precio = precio; 
    }

    /**
     * Devuelve la duracion de la actividad en minutos
     *
     * @return Numero que expresa la duracion en minutos
     */
    public int getDuracionMinutos() { return duracionMinutos; }

    /**
     * Metodo para establecer la duracion en minutos de la actividad
     *
     * @param duracionMinutos Duracion de la actividad en minutos
     */
    public void setDuracionMinutos(int duracionMinutos) { 
        if (duracionMinutos >= 0) this.duracionMinutos = duracionMinutos;
    }

    /**
     * Metodo para obtener el numero máximo de recursos de una actividad
     *
     * @return Numero maximo de recursos
     */
    public int getMaxRecursos() { return maxRecursos; }

    /**
     * Metodo para obtener el numero maximo de comentarios de una actividad
     *
     * @return Numero maximo de comentarios
     */
    public int getMaxComentarios() { return maxComentarios; }


    /**
     * Metodo utilizado para agregar un recurso a la actividad, devuelve un codigo de
     * error que depende de si se ha agregado el recurso exitosamente o en caso de
     * que haya ocurrido un error devolvera un codigo correspondiente al error
     *
     * @param recurso Recurso que se quiere agregar
     * @return El codigo de error correspondiente
     */
    public int agregarRecurso (String recurso) {
        int result;
        
        // The voidChars function is called to check whether "recurso"
        // is a non-valid string

        if (voidChars(recurso)) result = ERROR_VALOR_INVALIDO;
        else if (!recursosCompletos()) {
            addToArray(recursos, recurso, actRecursos);
            actRecursos++;

            result = EXITO;
        } else result = ERROR_RECURSOS_COMPLETOS;

        return result;
    }

    /**
     * Metodo utilizado para agregar un comentario a la actividad, devuelve un codigo de
     * error que depende de si se ha agregado el comentario exitosamente o en caso de
     * que haya ocurrido un error devolvera un codigo correspondiente al error
     *
     * @param comentario El comentario que se quiere agregar
     * @return El codigo de error correspondiente
     */
    public int agregarComentario(String comentario) {
        int result;
        
        // The voidChars function is called to check whether "recurso"
        // is a non-valid string
        
        if (voidChars(comentario)) result = ERROR_VALOR_INVALIDO;
        else if (!comentariosCompletos()) {
            addToArray(comentarios, comentario, actComentarios);
            actComentarios++;

            result = EXITO;
        } else result = ERROR_COMENTARIOS_COMPLETOS;

        return result;
    }

    /**
     * Metodo utilizado para comprobar si una cadena de texto esta vacia
     *
     * @param data Cadena a comprobar
     * @return True si la cadena no contiene caracteres
     */
    private static boolean voidChars(String data) {
        boolean blank = true; char data_char;

        if (data != null && data != "") {
            for (int i = 0; i < data.length(); i++) {
                data_char = data.charAt(i);

                if (data_char != 9 && data_char != 10 && data_char != 32) blank = false;
            }
        }

        return blank;
    }

    /**
     * Metodo usado  anteriormente para agregar recursos y comentarios, su
     * funcionalidad es la de agregar un elemento al array que se indica
     *
     * @param array Array al que se le añade el elemento
     * @param object Elemento que se añade al array
     * @param position Posicion del array en la que se añade el elemento
     */
    private void addToArray(String[] array, String object, int position) {
        array[position] = object;
    }


    /**
     * Devuelve un array de strings con todos los recursos de la actividad
     *
     * @return Array de recursos
     */
    public String[] getRecursos() { return recursos; }

    /**
     * Devuelve un array de Strings con todos los comentarios de la actividad
     *
     * @return Array de comentarios
     */
    public String[] getComentarios() { return comentarios; }


    /**
     * Metodo para comprobar si el numero de recursos es el maximo, devuelve true si es cierto
     *
     * @return True si no caben mas recursos
     */
    public boolean recursosCompletos() { return actRecursos == maxRecursos; }

    /**
     * Metodo para comprobar si el numero de comentarios es el maximo, devuelve true si es cierto
     *
     * @return True si no caben mas comentarios
     */
    public boolean comentariosCompletos() { return actComentarios == maxComentarios; }


    /**
     * Devuelve el numero de recursos que tiene la actividad
     *
     * @return Numero de recursos actuales
     */
    public int getNumRecursos() { return actRecursos; }

    /**
     * Devuelve numero de comentarios que tiene la actividad
     *
     * @return Numero de comentarios actuales
     */
    public int getNumComentarios() { return actComentarios; }

    /**
     * Metodo para ordenar toda la informacion de la actividad en un formato especifico
     * y devolverlo en forma de bloque de texto
     *
     * @return Informacion de la actividad como bloque de texto
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(String.format("Actividad: %s\n", nombre));
        result.append(String.format("Descripción: %s\n", descripcion));
        result.append(String.format("Precio: %.2f €\n", precio));

        int h = (int) (duracionMinutos / 60); int m = (int) (duracionMinutos % 60);

        if (h < 1) result.append(String.format("Duración: %2dmin\n", m));
        else if (m == 0)  result.append(String.format("Duración: %dh\n", h));
        else  result.append(String.format("Duración: %dh %dmin\n", h, m)); 

        result.append("Recursos:\n");
        for (int i = 0; i < actRecursos; i++) {
            result.append(String.format("- %s\n", recursos[i]));
        }
        
        result.append("Comentarios:");
        for (int i = 0; i < actComentarios; i++) {
            result.append(String.format("\n%d. %s", (i + 1), comentarios[i]));
        }
       
        return result.append("\n").toString();
    }

    /**
     * Metodo para ordenar la informacion de la actividad en un formato especifico ( para
     * la lectura desde un archivo ) y devolverlo en forma de texto
     *
     * @return Informacion de la actividad como bloque de texto
     */
    public String toRawString() {
        StringBuilder raw = new StringBuilder();
        raw.append(String.format("%s\n",nombre));
        raw.append(String.format("%s\n", descripcion));
        raw.append(String.format("%.2f\n", precio).replaceAll("0$", ""));
        raw.append(String.format("%d\n", duracionMinutos));
        for (int i = 0; i < actRecursos; i++){
            raw.append(String.format("%s\n", recursos[i]));
        }
        raw.append("COMENTARIOS");
        for (int i = 0; i < actComentarios; i++){
            raw.append(String.format("\n%s", comentarios[i]));
        }
        raw.append("\n-----");
        return raw.append("\n").toString();
    }

    /**
     * Metodo que se utiliza para leer un archivo en el que hay una actividad,
     * extraer la informacion de esa actividad y crear una nueva con esos elementos.
     *
     * @param reader Objeto de la clase BufferedReader para leer de un archivo
     * @param maxRecursos Numero maximo de recursos que se tiene que leer
     * @param maxComentarios Numero maximo de comentarios que se tiene que leer
     * @return La actividad con los elementos del archivo
     * @throws IOException Error de entrada informacion que se trata en la clase InterfazUsusario
     */
    public static Actividad fromBufferedReader(BufferedReader reader, int maxRecursos, int maxComentarios) throws IOException {
        String linea;
        int count = 0;
        Actividad result = new Actividad(reader.readLine(), maxRecursos, maxComentarios);
    
            result.setDescripcion(reader.readLine());
            result.setPrecio(Double.parseDouble(reader.readLine().replace(',', '.')));
            result.setDuracionMinutos(Integer.parseInt(reader.readLine()));

            while (!((linea = reader.readLine()).equals(FIRST_SEPARATOR))) {
                if (count < maxRecursos) {
                    result.agregarRecurso(linea);
                    count++;
                }
            }
            count = 0;

            while (!((linea = reader.readLine()).equals(SECOND_SEPARATOR))) {
                if (count < maxComentarios) {
                    result.agregarComentario(linea);
                    count++;
                }
            }

        return result;
    }
}
