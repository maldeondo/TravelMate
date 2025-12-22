package es.upm;
import java.io.*;
import java.lang.NumberFormatException;

/**
 * Viaje es una clase con la funcion de manejar las actividades que constituyen todo el viaje,
 * contiene metodos para agregar, buscar y eliminar actividades del viaje asi como metodos
 * para mostrar el itinerario del viaje al completo y guardarlo en el archivo que se desee.
 *
 * Esta clase se apoya en MatrizViaje para la manipulacion de las actividades, mediante un array de
 * objetos de la clase de tamaño días de duración. De esta forma, cada día tiene un objeto MatrizViaje
 * asociado, que a su vez contiene un catálogo (array de actividades) y un array de horas de inicio.
 *
 * Esto crea una nueva capa de abstracción, pues cada día tiene sus actividades y horas de inicio enlazadas
 * con una posición ordenada en función de la hora de inicio.
 *
 * Revisar la clase MatrizViaje es recomendable para entender el funcionamiento de esta clase.
 *
 * @author Mario Aldeondo
 * @author Robert Voong
 * @version 1.0
 */
public class Viaje {

    /**
     * Constante que contiene el valor de que se devuelve tras una operacion exitosa
     */
    public static final int EXITO = 0;
    /**
     * Constante que contiene el valor que se devuelve tras una operacion fallida
     * por motivo de que el dia no es valido
     */
    public static final int ERROR_DIA_INVALIDO = 1;
    /**
     * Constante que contiene el valor que se devuelve tras una operacion fallida
     * por motivo de  que no se admiten mas dias
     */
    public static final int ERROR_DIA_COMPLETO = 2;
    /**
     * Constante que contiene el valor que se devuelve tras una operacion fallida
     * por motivo de solapamiento entre las horas de una actividad
     */
    public static final int ERROR_SOLAPAMIENTO = 3;

    /**
     * Constante con el valor en minutos de la hora de inicio de una actividad, se inicializa
     * en 0 porque una actividad como minimo empieza al comienzo del dia
     */
    public static final int MINUTOS_MINIMO = 0;
    /**
     * Constante con el valor en minutos de la hora final de una actividad, se inicializa al
     * final del dia porque es lo maximo hasta lo que se puede extender una actividad
     */
    public static final int MINUTOS_MAXIMO = Utilidades.horaAMinutos("23:59");

    /**
     * Numero de dias que dura el viaje
     */
    private int numDias;
    /**
     * Array de actividades y horas, con un objeto de MatrizViaje por día
     */
    private MatrizViaje[] matriz;

    /**
     * Constructor que recibe los parámetros de la clase Main y crea un array de MatrizViaje
     * de tamaño días que dura el viaje, creando un objeto MatrizViaje de tamaño maxActividades
     * por cada día que dura el viaje.
     *
     * El constructor lanza una excepción NumberFormatException si los argumentos introducidos
     * son valores absurdos, que es manejada por el Main.
     *
     * @param numDias Número de días del viaje (argumento de Main)
     * @param maxActividades Número máximo de actividades por día (argumento de Main)
     */
    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {
            matriz = new MatrizViaje[numDias];

            for (int i = 0; i < numDias; i++) {
                matriz[i] = new MatrizViaje(maxActividades);
            }

            this.numDias = numDias;

        } else throw new NumberFormatException();
    }

    /**
     * Getter que devuelve el atributo numDias, la cantidad de días que tiene el viaje.
     *
     * @return Número de días del viaje.
     */
    public int getNumDias() { return numDias; }

    /**
     * Método que comprueba si el valor entero está en el
     * intervalo 0 <-> (numDias - 1), que para el
     * usuario es 1 <-> numDias.
     *
     * @param dia Número de día a analizar
     * @return True si la comprobación es exitosa
     */
    private boolean diaValido(int dia) { return (dia >= 0 && dia < numDias); }

    /**
     * Método que extrae el catálogo correspondiente a la matriz en el día introducido y llama
     * al método actividadesCompletas.
     *
     * @param dia Número de día a analizar
     * @return True si el día está lleno de actividades
     */
    private boolean catalogoLleno(int dia) { return matriz[dia].getCatalogo().actividadesCompletas(); }

    /**
     * Método que provee la capa de abstracción para acceder a la "matriz" (que realmente es un conjunto
     * de distintos tipos de arrays). Dados día y posición devuelve la actividad solicitada para
     * no tener que usar los métodos y posición en array específicos de MatrizViaje y CatalogoActividades.
     *
     * @param dia Número de día en el array de MatrizViaje[]
     * @param actividad Posición de actividad para el catálogo dentro del objeto MatrizViaje
     * @return Actividad solicitada
     */
    private Actividad getActividadfromMatrix(int dia, int actividad) {
        return matriz[dia].getCatalogo().getArray()[actividad];
    }

    /**
     * Método similar a getActividadfromMatrix que provee la capa de abstracción esta vez para
     * las horas de inicio. Dados días y posición devuelve la hora de inicio solicitada (en minutos).
     *
     * @param dia Número de día en el array de MatrizViaje[]
     * @param actividad Posición de actividad para el catálogo dentro del objeto MatrizViaje
     * @return Hora de inicio solicitada (en minutos)
     */
    private int getIniciofromMatrix(int dia, int actividad) {
        return matriz[dia].getInicio(actividad);
    }

    /**
     * Método principal que, junto con buscarIndex y actividadesSolapan, se encargan de añadir una actividad
     * al día en cuestión, garantizando que se hace respetando el orden de horas del día, y que no
     * se produce ningún solapamiento con actividades que previamente se encuentran agregadas.
     *
     * En todo este proceso se usan los métodos de abstracción getActividadfromMatrix y getIniciofromMatrix,
     * que permiten acceder a los datos de forma intuitiva.
     *
     * @param dia Número de día en el que se quiere agregar la actividad
     * @param actividad Actividad que se quiere agregar
     * @param horaInicio Hora de inicio en la que se quiere agregar la actividad (String con formato)
     * @return Código de salida de entre los definidos constantes como atributos de la clase
     */
    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {
        int exitcode = -1;

        int inicio = Utilidades.horaAMinutos(horaInicio);
        int fin = inicio + actividad.getDuracionMinutos();

        int posicion;

        if (!diaValido(dia)) exitcode = ERROR_DIA_INVALIDO;
        else if (catalogoLleno(dia)) exitcode = ERROR_DIA_COMPLETO;
        else {
            exitcode = EXITO;

            // Busca el índice en el que debe ir la actividad en función de la hora
            posicion = buscarIndex(dia, inicio);
            
            if (actividadesSolapan(dia, posicion, inicio, fin)) exitcode = ERROR_SOLAPAMIENTO;
            else matriz[dia].insertarActividadMatrix(actividad, posicion, inicio);
        }

        return exitcode;
    }

    /**
     * Método que itera sobre los datos de la matriz para encontrar el índice en el que se
     * deberá insertar la actividad nueva en caso de que no haya solapamientos.
     *
     * Como este método se usa siempre para añadir actividades a la "matriz" de datos, se
     * garantiza que el orden siempre es correcto antes de empezar a agregar una nueva
     * actividad, por lo que se puede asumir sin problema que el array está ordenado antes
     * de analizar dónde se debe insertar la nueva actividad.
     *
     * El problema de usar un algoritmo de ordenación total como Bubble Sort es que añade una
     * enorme cantidad de operaciones iteradas para un problema que no las requiere. Pues este caso
     * no es un problema de ordenación (array totalmente desordenado -> ordenar desde 0), sino un
     * problema de inserción/búsqueda (array previamente ordenado -> buscar posición adecuada.).
     *
     * Como detalle, se podría hacer búsqueda binaria, pero dado el tamaño esperado de los
     * catálogos, no parece merecer la pena.
     *
     * @param dia Número de día que se va a analizar
     * @param hora Hora de inicio (en minutos) de la actividad que se quiere agregar
     * @return Posición en la que, de no haber solapamientos, debe ir la actividad
     */
    // Se podría hacer binsearch, pero con el tamaño que manejan los catálogos no merece la pena
    private int buscarIndex(int dia, int hora) {
        int index = 0;

        for (int i = 0; i < getNumActividadesDia(dia); i++) {
            if (hora > getIniciofromMatrix(dia, i)) index = i + 1;
        }
        
        return index;
    }

    /**
     * El tercero de los métodos principales, considera los puntos de inicio y fin de la actividad
     * entrante y los compara con los datos existentes en la matriz para verificar que no existe ningún solapamiento.
     *
     * Está pensado para tener todos los casos en cuenta, tanto que sea la primera actividad en ser añadida (no se
     * hace ninguna comprobación), como solo tener en cuenta las posiciones de array respectivas en caso de estar en
     * el borde (índice 0 o índice numActividades ya existentes).
     *
     * Se usan las constantes MINUTOS_MINIMO y MINUTOS_MAXIMO para simplificar la estructura condicional, quedando
     * primeramente las variables horaFinalAnterior y horaInicialPosterior definidas por dichas constantes por
     * defecto. Estas variables se cambian en función de la posición objetivo de la actividad, por último, se
     * comparan con los valores de la actividad entrante según las instrucciones dadas:
     *
     * Existente -> A
     * Entrante -> B
     *
     * True si inicioB < finA o si finB > inicioA
     *
     * @param dia Número de día que se va a analizar
     * @param posicion Posición que buscarIndex ha decidido como potencialmente correcta
     * @param inicio Hora de inicio de la actividad nueva (en minutos)
     * @param fin Hora de fin de la actividad nueva (en minutos)
     * @return True si la actividad nueva solapa con una ya existente
     */
    private boolean actividadesSolapan(int dia, int posicion, int inicio, int fin) {
        int horaFinalAnterior = MINUTOS_MINIMO, horaInicialPosterior = MINUTOS_MAXIMO;
        int numActividades = getNumActividadesDia(dia);

        if (numActividades > 0) {
            if (posicion == 0) {
                horaInicialPosterior = getIniciofromMatrix(dia, posicion);
            }
            else if (posicion == numActividades) {
                horaFinalAnterior = getIniciofromMatrix(dia, posicion - 1);
                horaFinalAnterior += getActividadfromMatrix(dia, posicion - 1).getDuracionMinutos();
            } else {
                horaInicialPosterior = getIniciofromMatrix(dia, posicion);

                horaFinalAnterior = getIniciofromMatrix(dia, posicion - 1);
                horaFinalAnterior += getActividadfromMatrix(dia, posicion - 1).getDuracionMinutos();
                
            }
        }

        return (inicio < horaFinalAnterior || fin > horaInicialPosterior);
    }


    /**
     * Método que, mediante la capa de abstracción, consulta la matriz para, primero
     * hallar la actividad a eliminar según la hora de inicio, y después llama al método correspondiente
     * de MatrizViaje para modificar tanto el catálogo como el array de horas de inicio.
     *
     * @param dia Número de día a analizar
     * @param horaInicio Hora de inicio de la actividad a eliminar (String con formato)
     * @return True si se ha eliminado correctamente
     */
    public boolean eliminarActividad(int dia, String horaInicio) {
        boolean exitcode = false;
        int minutos = Utilidades.horaAMinutos(horaInicio);
        Actividad target;

        if (diaValido(dia)) {
            for (int i = 0; i < getNumActividadesDia(dia); i++) {
                target = getActividadfromMatrix(dia, i);
                
                if (minutos == getIniciofromMatrix(dia, i)) {
                    matriz[dia].eliminarActividadMatrix(target, i);
                    exitcode = true;
                }
            }
        }

        return exitcode;
    }

    /**
     * Getter que devuelve el número de actividades que se han creado
     * en un día dado.
     *
     * @param dia Número de día a analizar
     * @return Número de actividades en el día
     */
    public int getNumActividadesDia(int dia) {
        return matriz[dia].getCatalogo().getNumActividades();
    }

    public Actividad[] obtenerActividadesDia(int dia) {
        return matriz[dia].getCatalogo().getArray();
    }

    /**
     * Metodo usado para mostrar el itinerario del viaje con el formato pedido, cada dia
     * con sus respectivas actividades indicando su hora de inicio y un resumen al final,
     * si no hay actividades se mostrara: (No hay actividades)
     *
     * @return Bloque de texto con el itinerario (String)
     */
    @Override
    public String toString() {
        StringBuilder itinerario = new StringBuilder();
        Actividad actividad;
        int numActividades;

        int totalActividades = 0;
        double precio = 0;

        
        //Print de los giones + el dia cada dia
        for(int dia = 0; dia < numDias; dia++) {
            numActividades = getNumActividadesDia(dia);
            
            if (dia != 0) itinerario.append("\n");
            itinerario.append("-------------------------------------------------------------------\n");
            
            itinerario.append(String.format("Día %d\n", dia + 1));
            itinerario.append("-------------------------------------------------------------------\n");
            
            //Si no hay actividades en el dia = (No hay actividades)
            if (numActividades == 0) itinerario.append("(No hay actividades)\n");
            
            // Print de las actividades por cada dia
            else {
                for (int j = 0; j < numActividades; j++) {
                    actividad = getActividadfromMatrix(dia, j);
                    itinerario.append(String.format("%s %s\n", Utilidades.minutosAHora(getIniciofromMatrix(dia, j)), actividad.getNombre()));
                    
                    totalActividades++;
                    precio += actividad.getPrecio();
                }
            }

        }

        //Guion final y resumen de gastos hay alguna actividad
        itinerario.append("\n-------------------------------------------------------------------\n");
        itinerario.append("Resumen:\n");
        itinerario.append(String.format("- Días: %d\n", numDias));
        itinerario.append(String.format("- Actividades: %d\n", totalActividades));
        itinerario.append(String.format("- Precio: %s\n", Utilidades.formatearPrecio(precio)));

        return itinerario.toString();
    }

    /**
     * Metodo que recibe como parametro un nombre de archivo donde se guardara el itinerario de un viaje,
     * el metodo guarda las actividades por dia con sus horas de inicio en un formato especifico para
     * que su posterior lectura sea mas manejable, tambien guarda el resumen del viaje.
     *
     * @param nombreArchivo Nombre del archivo donde se guardara
     * @throws IOException Excepcion de entrada y salida que se trata en InterfazUsuario
     */
    public void guardarItinerario(String nombreArchivo) throws IOException {
        PrintWriter itinerario = new PrintWriter(nombreArchivo);
        Actividad actividad;
        double precio = 0;
        int totalActividades = 0;
        int numActividades;

        //Primer for para cada dia
        for(int dia = 0; dia < numDias; dia++){
            numActividades = getNumActividadesDia(dia);
            totalActividades += numActividades;

            itinerario.printf("Día %d:", dia + 1);
            //Si no hay actividades se ponen tres guiones y se pasa al siguiente dia
            if (numActividades == 0) itinerario.println(" ---");
            else {
                actividad = getActividadfromMatrix(dia, 0);
                itinerario.printf(" %s %s (dur %s, %s)", Utilidades.minutosAHora(getIniciofromMatrix(dia, 0)), actividad.getNombre(), 
                    Utilidades.formatearDuracion(actividad.getDuracionMinutos()), Utilidades.formatearPrecio(actividad.getPrecio()));

                precio += actividad.getPrecio();

                //Print de todas las actividades en la misma linea, el primero es distinto porque empieza sin ;
                for (int j = 1; j < numActividades; j++) {
                    actividad = getActividadfromMatrix(dia, j);
                    itinerario.printf("; %s %s (dur %s, %s)", Utilidades.minutosAHora(getIniciofromMatrix(dia, j)), actividad.getNombre(), Utilidades.formatearDuracion(actividad.getDuracionMinutos()),
                        Utilidades.formatearPrecio(actividad.getPrecio()));
                        
                    precio += actividad.getPrecio();
                }
                itinerario.println();
            }
        }
        // Resumen del viaje
        itinerario.printf("Resumen: Días: %d; Actividades: %d; Precio total: %s\n", numDias, totalActividades, Utilidades.formatearPrecio(precio));
        itinerario.close();
    }
}
