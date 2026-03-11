package es.upm;

/**
 * Esta clase es una extensión de CatalogoActividades que incluye un array de enteros que contiene
 * las horas de inicio de cada actividad del catálogo. También incluye métodos para facilitar el uso desde
 * la clase Viaje. Esta implementación permite aprovechar el código ya creado en CatalogoActividades para
 * gestionar la adición y eliminación de actividades.
 *
 * Tanto el catálogo (con su array de actividades) como el array de horas de inicio "están sincronizados"
 * ya que los métodos públicos, llamados desde viaje, modifican los dos arrays al mismo tiempo.
 *
 * De esta forma, una cierta actividad ocupa la misma posición tanto dentro del catálogo como en el array de minutos.
 *
 * @author Mario Aldeondo
 * @author Robert Voong
 * @version 1.0
 */
public class MatrizViaje {

    /**
     * Objeto de la clase CatalogoActividades
     */
    private CatalogoActividades catalogo;

    /**
     * Array con las horas de inicio de actividad en minutos
     */
    private int[] minutosInicio;


    /**
     * Constructor de la clase dado el máximo de actividades, que se usa
     * desde Viaje (en un array de esta misma clase).
     *
     * Crea un objeto de la clase CatalogoActividades con el máximo dado
     * y un array con los minutos de inicio, con el mismo tamaño.
     *
     * @param maxActividades Máximo de actividades para ambos arrays
     */
    public MatrizViaje(int maxActividades) {
        catalogo = new CatalogoActividades(maxActividades);

        minutosInicio = new int[maxActividades];
    }

    /**
     * Getter que se usa para acceder al catálogo directamente desde Viaje.
     *
     * @return Catálogo de actividades
     */
    public CatalogoActividades getCatalogo() { return catalogo; }

    /**
     * Método que simplifica añadir una actividad a cierta a hora llamando internamente a insertarInicio para
     * añadir la hora y modificando el catálogo mediante el método insertarActividad.
     *
     * @param actividad Actividad que se quiere añadir
     * @param indiceActividad Posición en la que va (previamente verificada en Viaje)
     * @param inicio Hora de inicio
     */
    public void insertarActividadMatrix(Actividad actividad, int indiceActividad, int inicio) {
        catalogo.insertarActividad(actividad, indiceActividad);

        insertarInicio(indiceActividad, inicio);
    }

    /**
     * Método similar a insertarActividadMatrix, se elimina la actividad del catálogo y la hora de inicio del
     * array de minutos mediante los métodos correspondientes.
     *
     * @param actividad Actividad que se quiere eliminar
     * @param indiceActividad Posición de dicha actividad (necesaria para gestionar el array de minutos)
     */
    public void eliminarActividadMatrix(Actividad actividad, int indiceActividad) {
        catalogo.eliminarActividad(actividad);

        eliminarInicio(indiceActividad);
    }

    /**
     * Método que usa la clase para insertar una nueva hora de inicio dentro del array, desplazando el resto
     * de los datos previamente existentes una posición a la derecha.
     *
     * @param indiceActividad Posición del array en la que insertar la nueva hora de inicio
     * @param minutos Valor de la hora de inicio en minutos
     */
    private void insertarInicio(int indiceActividad, int minutos) {
        for (int i = catalogo.getNumActividades() - 1; i > indiceActividad; i--) {
            minutosInicio[i] = minutosInicio[i - 1];
        }

        minutosInicio[indiceActividad] = minutos;
    }

    /**
     * Método que, al igual que insertarInicio, modifica el array para eliminar una entrada
     * y el hueco resultante, manteniendo el array contínuo.
     *
     * @param indiceActividad Posición del array a eliminar
     */
    private void eliminarInicio(int indiceActividad) {
        for (int i = indiceActividad; i < catalogo.getNumActividades(); i++) {
            minutosInicio[i] = minutosInicio[i + 1];
        }
    }

    /**
     * Getter que devuelve la hora de inicio deseada con la posición de la actividad
     *
     * @param indiceActividad Posición de la hora en minutos a obtener
     * @return Hora en minutos extraída del array
     */
    public int getInicio(int indiceActividad) { return minutosInicio[indiceActividad]; }
}
