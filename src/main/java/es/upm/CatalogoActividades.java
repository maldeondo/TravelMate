package es.upm;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

public class CatalogoActividades {

    // by doc
    private final int maxActividades;
    private int actActividades = 0;

    // array aproach, the data structure is not specified by doc
    private Actividad[] arrayActividades;

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    public CatalogoActividades(int maxActividades) {
        this.maxActividades = maxActividades;

        arrayActividades = new Actividad[maxActividades];
    }

    public boolean actividadesCompletas() { return actActividades == maxActividades; }

    public int getNumActividades() { return actActividades; }


    private static boolean validEntry(Actividad act) { return (act != null); }

    public int agregarActividad(Actividad actividad) {
        int exitcode;

        if (!validEntry(actividad)) exitcode = ERROR_ACTIVIDAD_NULL;
        else if (actividadesCompletas()) exitcode = ERROR_DEMASIADOS;
        else {
            arrayActividades[actActividades] = actividad;
            actActividades++;

            exitcode = EXITO;
        }

        return exitcode;
    }

    public boolean eliminarActividad(Actividad seleccionada) {
        return true; // @todo MODIFICAR PARA DEVOLVER SI SE HA PODIDO ELIMINAR
    }

    public Actividad[] buscarActividadPorNombre(String texto) {
        // Devuelve actividades cuyo nombre contenga el texto indicado
        return null; // @todo MODIFICAR PARA DEVOLVER LAS ACTIVIDADES QUE COINCIDEN
    }

    public void guardarActividades(String nombreArchivo) throws IOException {
        // Guarda todas las actividades en un archivo de texto usando su representación compacta
    }

    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        // Carga actividades desde un archivo de texto previamente guardado
    }
}
