package es.upm;
import java.io.*;

public class Viaje {

    // ---------------------------
    // Constantes de códigos de error
    // ---------------------------
    public static final int EXITO = 0;
    public static final int ERROR_DIA_INVALIDO = 1;
    public static final int ERROR_DIA_COMPLETO = 2;
    public static final int ERROR_SOLAPAMIENTO = 3;

    //Atributos
    private int numDias;
    private int maxActividades;
    private int actividadesTotales;

    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {
            this.numDias = numDias;
            this.maxActividades = maxActividades;
            actividadesTotales = numDias * maxActividades;
        } else System.out.println("Estos valores no tienen sentido");
    }

    public int getNumDias() {return numDias;}

    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {

        return 0;
    }


    private void ordenarActividadesDia(int dia) {
        // Ordena las actividades de un día por hora de inicio (método de burbuja)
    }

    public boolean eliminarActividad(int dia, String horaInicio) {
        // Elimina la actividad programada en el día y hora indicados
        return true; // @todo MODIFICAR PARA DEVOLVER SI SE HA ELIMINADO
    }

    public Actividad[] obtenerActividadesDia(int dia) {
        // Devuelve las actividades de un día ordenadas por hora
        return null; // @todo MODIFICAR PARA DEVOLVER EL ARRAY DE ACTIVIDADES
    }

    public int getNumActividadesDia(int dia) {
        // Devuelve el número de actividades planificadas para un día
        return 0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO REAL
    }

    @Override
    public String toString() {
        // Devuelve la representación textual del itinerario
        return null; // @todo MODIFICAR PARA DEVOLVER LA REPRESENTACIÓN TEXTUAL
    }

    public void guardarItinerario(String nombreArchivo) throws IOException {
        // Guarda el itinerario en un archivo de texto (formato compacto)
    }
}
