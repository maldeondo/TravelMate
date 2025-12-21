package es.upm;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CatalogoActividades {
    private int maxActividades; //should be final, but won't compile
    private int actActividades = 0;
    private static final String SEPARATOR = "\n-----\n";

    // array aproach, the data structure is not specified by doc
    private Actividad[] arrayActividades;

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    public CatalogoActividades(int maxActividades) {
        if (maxActividades >= 0) {
            this.maxActividades = maxActividades;

            arrayActividades = new Actividad[maxActividades];
        } else throw new NumberFormatException();
        
    }

    public boolean actividadesCompletas() {
        return actActividades == maxActividades;
    }

    public int getNumActividades() {
        return actActividades;
    }

    public Actividad[] getArray() {
        return arrayActividades;
    }


    private static boolean nullEntry(Actividad act) { return (act == null); }

    public int agregarActividad(Actividad actividad) {
        int exitcode;

        if (nullEntry(actividad)) exitcode = ERROR_ACTIVIDAD_NULL;
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

        if (nullEntry(seleccionada)) System.out.println("null");
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

    public int insertarActividad(Actividad actividad, int index) {
        int exitcode;

        if (nullEntry(actividad)) exitcode = ERROR_ACTIVIDAD_NULL;
        else if (actividadesCompletas()) exitcode = ERROR_DEMASIADOS;
        else {
            for (int i = actActividades - 1; i >= index; i--) {
                arrayActividades[i + 1] = arrayActividades[i];
            }

            arrayActividades[index] = actividad;
            actActividades++;

            exitcode = EXITO;
        }

        return exitcode;
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
        PrintWriter actividad = new PrintWriter(nombreArchivo);

        for (int i = 0; i < actActividades; i++) {
            actividad.print(arrayActividades[i].toRawString());
        }

        actividad.close();
    }

    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        String file = Files.readString(Path.of(nombreArchivo));
        BufferedReader block;

        String[] inputArray = file.split(SEPARATOR);
        Reader strReader = null;

        for (int i = 0; (i < inputArray.length) && (i < maxActividades); i++) {
            inputArray[i] += SEPARATOR;

            strReader = new StringReader(inputArray[i]);

            block = new BufferedReader(strReader);

            agregarActividad(Actividad.fromBufferedReader(block, maxRecursos, maxComentarios));
        }

    }
}
