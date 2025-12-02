package es.upm;
import java.io.*;

public class CatalogoActividades {
    private int maxActividades; //should be final, but won't compile
    private int actActividades = 0;
    private static final String SEPARATOR = "-----";

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

    public boolean actividadesCompletas() {
        return actActividades == maxActividades;
    }

    public int getNumActividades() {
        return actActividades;
    }


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
        PrintWriter actividad = new PrintWriter(nombreArchivo);

        for (int i = 0; i < actActividades; i++) {
            actividad.print(arrayActividades[i].toRawString());
        }

        actividad.close();
    }

    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        /*
        FileReader archivo = new FileReader(nombreArchivo);

        
        BufferedReader actividad = new BufferedReader(archivo);
        String linea;
        int contador = 0;
        /* Lector de actividades hasta final de linea y no mas actividades
         que las que permite el catalogo
        */
        /* Es necesario guardar el .readLine() dentro de una variable
           para poder trabajar sobre la primera linea que se lee
        

        while ((linea = actividad.readLine()) != null && contador < maxActividades && !actividadesCompletas()){

            // Asignar a la nueva actividad su nombre, descripcion, precio y duracion.
            Actividad nuevaActividad = new Actividad(linea,maxRecursos,maxComentarios);
            nuevaActividad.setDescripcion(actividad.readLine());
            nuevaActividad.setPrecio(Double.parseDouble(actividad.readLine()));
            nuevaActividad.setDuracionMinutos(Integer.parseInt(actividad.readLine()));

            //Agregar todos los recursos hasta encontrar la palabra COMENTARIOS
            while((linea = actividad.readLine()) != null && !linea.equals("COMENTARIOS")){
                if(nuevaActividad.getNumRecursos() < maxRecursos){
                    nuevaActividad.agregarRecurso(linea);
                }
            }

            //Agregar todos los comentarios hasta encontrar -----
            while((linea = actividad.readLine()) != null && !linea.equals("-----")){
                if(nuevaActividad.getNumComentarios() < maxComentarios){
                    nuevaActividad.agregarComentario(linea);
                }
            }
            //Agregar actividad al catalogo
            agregarActividad(nuevaActividad);
            contador++;

        }
        actividad.close();
        */
        FileReader archivo = new FileReader(nombreArchivo);
        BufferedReader block;
        Actividad result;

        String[] inputArray = archivo.toString().split("-----");
        StringReader strReader;

        for (int i = 0; (i < inputArray.length) && (i < maxActividades); i++) {
            inputArray[i] += "\n-----\n";
            strReader = new StringReader(inputArray[i]);

            block = new BufferedReader(strReader);

            result = Actividad.fromBufferedReader(block, maxRecursos, maxComentarios);

            agregarActividad(result);
        }

        archivo.close();
    }
}
