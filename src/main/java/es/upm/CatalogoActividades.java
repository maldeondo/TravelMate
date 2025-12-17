package es.upm;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * CatalogoActividades es una clase con la funcion de almcenar las actividades
 * asignadas a un mismo dia, la clase cuenta con una serie de metodos con la
 * funcionalidad de agregar, eliminar, buscar, cargar y guardar actividades.
 *
 * @Mario Aldeondo
 * @Robert Voong
 */
public class CatalogoActividades {
    private int maxActividades; //should be final, but won't compile
    private int actActividades = 0;
    private static final String SEPARATOR = "\n-----\n";

    // array aproach, the data structure is not specified by doc
    private Actividad[] arrayActividades;

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    /**
     * Constructor de la clase CatalogoActividades para que se inicialice siempre
     * con un numero maximo de actividades
     *
     * @param maxActividades Numero maximo de actividades en un catalogo
     */
    public CatalogoActividades(int maxActividades) {
        if (maxActividades > 0) {
            this.maxActividades = maxActividades;

            arrayActividades = new Actividad[maxActividades];
        }
        
    }

    /**
     * Metodo que devuelve un boolean que depende de si el numero de actividades
     * ha llegado al numero maximo de actividades
     *
     * @return True si no caben mas actividades
     */
    public boolean actividadesCompletas() {
        return actActividades == maxActividades;
    }

    /**
     * Metodo que devuelve el numero de actividades en el catalogo
     *
     * @return Numero de actividades en el catalogo
     */
    public int getNumActividades() {
        return actActividades;
    }

    /**
     * Metodo que devuelve un array de actividades con las actividades dentro del catalogo
     *
     * @return Array de actividades
     */
    public Actividad[] getCatalogo() {
        return arrayActividades;
    }


    /**
     * Metodo que devuelve un boolean que depende de si un objeto de la
     * clase Actividad esta vacio o no
     *
     * @param act Objeto de la clase actividad
     * @return True si la actividad esta vacia
     */
    private static boolean nullEntry(Actividad act) { return (act == null); }

    /**
     * Metodo usado para agregar una actividad al catalogo que devuelve un codigo de error
     * en base al exito o tipo de error producido al intentar añadir la actividad
     *
     * @param actividad Objeto de la clase actividad que se añade al catalogo
     * @return El codigo de error correspondiente a el resultado de la operacion
     */
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

    /**
     * Metodo usado para eliminar una actividad que devuelve un boolean en base
     * al exito o no de la operacion.
     *
     * @param seleccionada Objeto de la clase actividad que se elimina
     * @return True si se elimina la actividad
     */
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

    /**
     * Metodo para insertar una actividad en una posicion concreta del
     * catalogo que devuelve un codigo que depende del exito o tipo de error
     * al intentar insertar la actividad.
     *
     * @param actividad Objeto de la clase actividad que se quiere insertar
     * @param index Posicion en la que se quiere insertar
     * @return Codigo de error de la operacion
     */
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

    /**
     * Metodo usado para buscar actividades por nombre que devuelve un objeto de la clase actividad
     * si coincide con el nombre proporcionado como parametro.
     *
     * @param texto Nombre de la actividad que se quiere buscar
     * @return La actividad buscada si se encuentra
     */
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

    /**
     * Metodo usado para guardar una actividad en un archivo con el formato ofrecido por el metodo toRawString
     * de la clase actividad para un futura lectura correcta.
     *
     * @param nombreArchivo Nombre del archivo donde se guardara la actividad
     * @throws IOException Lanza los errores de entrada y salida que son trarados en InterfazUsuario
     */
    public void guardarActividades(String nombreArchivo) throws IOException {
        PrintWriter actividad = new PrintWriter(nombreArchivo);

        for (int i = 0; i < actActividades; i++) {
            actividad.print(arrayActividades[i].toRawString());
        }

        actividad.close();
    }

    /**
     * Metodo usado para leer informacion de una actividad dentro del archivo seleccionado
     * y crear una nueva actividad con esos datos.
     *
     * @param nombreArchivo Nombre del archivo que se quiere leer
     * @param maxRecursos Maximo de recursos que se quieren leer de la archivo
     * @param maxComentarios Maximo de comentarios que se quieren leer de el archivo
     * @throws IOException Lanza el error de salida o entrada que se trata en InterfazUsuario
     */
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
