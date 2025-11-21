package es.upm;

import java.io.BufferedReader;
import java.io.IOException;

public class Actividad {
    // Taken from doc, the following private data is necessary
    private String nombre = "";
    private String descripcion = ""; 

    private String[] recursos;
    private String[] comentarios;

    private int maxRecursos = 0;
    private int maxComentarios = 0;
    private double precio = 0.0;

    // Made by us
    private int actRecursos = 0;
    private int actComentarios = 0;

    // ---------------------------
    // Constantes de códigos de error
    // ---------------------------
    public static final int EXITO = 0;
    public static final int ERROR_VALOR_INVALIDO = 1;
    public static final int ERROR_RECURSOS_COMPLETOS = 2;
    public static final int ERROR_COMENTARIOS_COMPLETOS = 3;

    public Actividad(String nombre,int maxRecursos, int maxComentarios) {
        this.nombre = nombre; 
        this.maxRecursos = maxRecursos; 
        this.maxComentarios = maxComentarios;

        recursos = new String[maxRecursos];
        comentarios = new String[maxComentarios];
    }

    // These methods could fail handling wrong values, 
    // a try catch block should be implemented

    public String getNombre() { return nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) {
        // Setea la descripción de la actividad
    }

    public double getPrecio() {
        // Devuelve el precio de la actividad
        return 0; // @todo MODIFICAR PARA DEVOLVER EL PRECIO
    }
    public void setPrecio(double precio) {
        // Setea el precio de la actividad
    }

    public int getDuracionMinutos() {
        // Devuelve la duración de la actividad
        return 0; // @todo MODIFICAR PARA DEVOLVER LA DURACIÓN
    }
    public void setDuracionMinutos(int duracionMinutos) {
        // Setea la duración de la actividad
    }

    public int getMaxRecursos() { return maxRecursos; }

    public int getMaxComentarios() { return maxComentarios; }

    public int agregarRecurso (String recurso) {
        int result;
        
        // Pending to handle empty strings as well
        if (recurso == null || recurso == "") result = ERROR_VALOR_INVALIDO;
        else if (!recursosCompletos()) {
            recursos[actRecursos] = recurso;
            actRecursos++;

            result = EXITO;
        } else result = ERROR_RECURSOS_COMPLETOS;

        return result;
    }

    public int agregarComentario(String comentario) {
        int result;
        
        // Pending to handle empty strings as well
        if (comentario == null || comentario == "") result = ERROR_VALOR_INVALIDO;
        else if (!recursosCompletos()) {
            comentarios[actComentarios] = comentario;
            actComentarios++;

            result = EXITO;
        } else result = ERROR_RECURSOS_COMPLETOS;

        return result;
    }

    /*
    private static void addToArray(String[] array, String object, int ocupation) {
        array[ocupation] = object;
    }
    */

    public String[] getRecursos() { return recursos; }

    public String[] getComentarios() { return comentarios; }

    public boolean recursosCompletos() { return actRecursos == maxRecursos; }

    public boolean comentariosCompletos() { return actComentarios == actRecursos; }

    public int getNumRecursos() { return actRecursos; }

    public int getNumComentarios() { return actComentarios; }

    @Override
    public String toString() {
        // Devuelve la representación textual completa de la actividad
        return null; // @todo MODIFICAR PARA DEVOLVER LA REPRESENTACIÓN TEXTUAL
    }

    public String toRawString() {
        // Devuelve la representación textual compacta para guardado/carga
        return null; // @todo MODIFICAR PARA DEVOLVER LA REPRESENTACIÓN TEXTUAL COMPACTA
    }

    public static Actividad fromBufferedReader(
            BufferedReader reader,
            int maxRecursos,
            int maxComentarios) throws IOException {
        // Devuelve la actividad leída de un BufferedReader
        return null; // @todo MODIFICAR PARA DEVOLVER LA ACTIVIDAD LEÍDA
    }
}
