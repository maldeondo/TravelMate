package es.upm;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

public class CatalogoActividades {
    private int maxActividades; //should be final, but won't compile
    private int actActividades = 0;

    // array aproach, the data structure is not specified by doc
    private Actividad[] arrayActividades;

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    public CatalogoActividades(int maxActividades) {
        if (maxActividades > 0) {
            this.maxActividades = maxActividades;

            arrayActividades = new Actividad[maxActividades];
        }
        
    }

    public boolean actividadesCompletas() { return actActividades == maxActividades; }

    public int getNumActividades() { return actActividades; }


    private static boolean notNullEntry(Actividad act) { return (act != null); }

    public int agregarActividad(Actividad actividad) {
        int exitcode;

        if (!notNullEntry(actividad)) exitcode = ERROR_ACTIVIDAD_NULL;
        else if (actividadesCompletas()) exitcode = ERROR_DEMASIADOS;
        else {
            arrayActividades[actActividades] = actividad;
            actActividades++;

            exitcode = EXITO;
        }

        return exitcode;
    }

    public boolean eliminarActividad(Actividad seleccionada) {
        int target_index = -1;
        boolean target_found = false;

        if (!notNullEntry(seleccionada)) System.out.println("null");
        else {
            // find the target
            for (int i = 0; i < actActividades; i++) {
                if (arrayActividades[i] == seleccionada) {
                    target_index = i;
                    target_found = true;
                }
            }

            // replace each entry after the removed target
            if (target_found) {
                for (int i = target_index; i < actActividades - 1; i++) {
                    arrayActividades[i] = arrayActividades[i + 1];
                }
                actActividades--;
            }  
        }
  
        return target_found; 
    }

    public Actividad[] buscarActividadPorNombre(String texto) {

        // create the void array before so to check if texto is a null pointer
        Actividad[] target_array = {};

        if (texto != null) {
            int[] index_array = new int[maxActividades];
            texto = texto.toLowerCase();
            
            int target_count = 0;

            // search for targets and save its index into the temp index_array
            for (int i = 0; i < actActividades; i++) {

                // check if the LOWERCASE name contains any LOWERCASE substring
                // that matches with the provided texto
                if (arrayActividades[i].getNombre().toLowerCase().contains(texto)) {

                    // index_array[i] = i would result in an array with "jumps"
                    // here target_count is recycled to get a continious array
                    index_array[target_count] = i;
                    target_count++;
                }
            }

            target_array = new Actividad[target_count];

            for (int i = 0; i < target_count; i++) {

                // build target_array with the main arrayActividades value ONLY using
                // the correct indexes found previously (which are inside index_array)
                target_array[i] = arrayActividades[index_array[i]];
            }
        }

        return target_array;
    }

    public void guardarActividades(String nombreArchivo) throws IOException {
        // Guarda todas las actividades en un archivo de texto usando su representación compacta
    }

    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        // Carga actividades desde un archivo de texto previamente guardado
    }
}
