package es.upm;
import java.io.*;

/**
 *Viaje es una clase con la funcion de manejar las actividades que constituyen todo el viaje,
 * contiene metodos para agregar, buscar y eliminar actividades del viaje asi como metodos
 * para mostrar el itinerario del viaje al completo y guardarlo en el archivo que se desee.
 * Esta clase se apoya en Actividad y CatalogoActividades para la manipulacion de las actividades.
 *
 * @author Mario Aldeondo
 * @auhtor Robert Voong
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


    // Constantes para la comprobación de solapamiento
    private static final int MINUTOS_MINIMO = 0;
    private static final int MINUTOS_MAXIMO = Utilidades.horaAMinutos("23:59");

    //Atributos
    private int numDias; // Numero de dias que dura el viaje
    private MatrizViaje[] matriz;

    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {

            // La matriz se fija a tamaño numDias y se llena de catálogos
            // vacíos de tamaño maxActividades
            matriz = new MatrizViaje[numDias];

            for (int i = 0; i < numDias; i++) {
                matriz[i] = new MatrizViaje(maxActividades);
            }

            this.numDias = numDias;
        } else System.out.println("Estos valores no tienen sentido");
    }

    public int getNumDias() { return numDias; }

    public boolean diaValido(int dia) { return (dia >= 0 && dia < numDias); }

    private boolean catalogoLleno(int dia) { return matriz[dia].getCatalogo().actividadesCompletas(); }

    // Provee una forma más intuitiva de usar la matriz
    private Actividad getActividadfromMatrix(int dia, int actividad) {
        return matriz[dia].getCatalogo().getArray()[actividad];
    }

    private int getIniciofromMatrix(int dia, int actividad) {
        return matriz[dia].getInicio(actividad);
    }

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

    // Esta es la forma elegida de ordenar las actividades en un día, simplemente
    // comprueba en qué orden según hora de inicio debería "caer" la nueva actividad

    // El problema con usar un algoritmo como Bubble Sort es que hace una enorme 
    // cantidad de operaciones innecesarias, pues el problema no es de ORDENACIÓN
    // sino de INSERCIÓN/BÚSQUEDA

    // Se podría hacer binsearch, pero con el tamaño que manejan los catálogos no merece la pena
    private int buscarIndex(int dia, int hora) {
        int index = 0;

        for (int i = 0; i < getNumActividadesDia(dia); i++) {
            if (hora > getIniciofromMatrix(dia, i)) index = i + 1;
        }
        
        return index;
    }

    private boolean actividadesSolapan(int dia, int posicion, int inicio, int fin) {
            int horaInicialPosterior = MINUTOS_MAXIMO, horaFinalAnterior = MINUTOS_MINIMO;
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

    public int getNumActividadesDia(int dia) {
        return matriz[dia].getCatalogo().getNumActividades();
    }

    public Actividad[] obtenerActividadesDia(int dia) {
        return matriz[dia].getCatalogo().getArray();
    }

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
