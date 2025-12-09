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

    // Constantes para la comprobación de solapamiento
    private static final int MINUTOS_MINIMO = 0;
    private static final int MINUTOS_MAXIMO = Utilidades.horaAMinutos("23:59");

    //Atributos
    private int numDias; // Numero de dias que dura el viaje
    private CatalogoActividades[] matrActividades; // Array de catalogos (Matriz de actividades)

    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {

            // La matriz se fija a tamaño numDias y se llena de catálogos
            // vacíos de tamaño maxActividades
            matrActividades = new CatalogoActividades[numDias];
            for (int i = 0; i < numDias; i++) {
                matrActividades[i] = new CatalogoActividades(maxActividades);
            }

            this.numDias = numDias;
        } else System.out.println("Estos valores no tienen sentido");
    }

    public int getNumDias() { return numDias; }

    public boolean diaValido(int dia) { return (dia >= 0 && dia < numDias); }

    private boolean catalogoLleno(int index) { return matrActividades[index].actividadesCompletas(); }

    // Provee una forma más intuitiva de usar la matriz
    private Actividad getActividadfromMatrix(int dia, int actividad) {
        return matrActividades[dia].getCatalogo()[actividad];
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

            // Añade la hora directamente a la actividad (simplifica mucho el código)
            actividad.setHora(inicio);

            // Busca el índice en el que debe ir la actividad en función de la hora
            posicion = buscarIndex(dia, inicio);
            
            if (actividadesSolapan(dia, posicion, inicio, fin)) exitcode = ERROR_SOLAPAMIENTO;
            else matrActividades[dia].insertarActividad(actividad, posicion);
        }

        return exitcode;
    }

    // Esta es la forma elegida de ordenar las actividades en un día, simplemente
    // comprueba en qué orden según hora de inicio debería "caer" la nueva actividad

    // El problema con usar un algoritmo como Bubble Sort es que hace una enorme 
    // cantidad de operaciones innecesarias, pues el problema no es de ORDENACIÓN
    // sino de INSERCIÓN/BÚSQUEDA

    // Se podría hacer binsearch, pero con el tamaño que manejan los catálogos no merece la pena
    public int buscarIndex(int dia, int hora) {
        int index = 0;

        for (int i = 0; i < getNumActividadesDia(dia); i++) {
            if (hora > getActividadfromMatrix(dia, i).getInicio()) index = i + 1;
        }
        
        return index;
    }

    private boolean actividadesSolapan(int dia, int posicion, int inicio, int fin) {
            int horaInicialPosterior = MINUTOS_MAXIMO, horaFinalAnterior = MINUTOS_MINIMO;

            switch (getNumActividadesDia(dia)) {
                case 0:
                    // Se añade la actividad directamente
                    break;
                    
                case 1:
                    // Cuando solo hay una actividad hay que ajustar los índices para el array

                    if (posicion == 0) {
                        horaInicialPosterior = getActividadfromMatrix(dia, posicion).getInicio();
                    } else {
                        horaFinalAnterior = getActividadfromMatrix(dia, posicion - 1).getInicio();
                        horaFinalAnterior += getActividadfromMatrix(dia, posicion - 1).getDuracionMinutos();
                    }
                    break;

                default:
                    try {
                        horaInicialPosterior = getActividadfromMatrix(dia, posicion + 1).getInicio();

                        horaFinalAnterior = getActividadfromMatrix(dia, posicion - 1).getInicio();
                        horaFinalAnterior += getActividadfromMatrix(dia, posicion - 1).getDuracionMinutos();
                    
                    } catch (Exception exception) {} // Los valores de hora se quedan por defecto en MIN y MAX
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
                
                if (minutos == target.getInicio()) {
                    matrActividades[dia].eliminarActividad(target);
                    exitcode = true;
                }
            }
        }

        return exitcode;
    }

    public int getNumActividadesDia(int dia) {
        return matrActividades[dia].getNumActividades();
    }

    public Actividad[] obtenerActividadesDia(int dia) {
        return matrActividades[dia].getCatalogo();
    }

    @Override
    public String toString() {
        StringBuilder itinerario = new StringBuilder();
        Actividad actividad;
        int numActividades;

        int totalActividades = 0;
        double precio = 0;
        itinerario.append("-------------------------------------------------------------------\n");
        
        //Print de los giones + el dia cada dia
        for(int dia = 0; dia < numDias; dia++) {
            numActividades = getNumActividadesDia(dia);
            itinerario.append(String.format("Día %d\n",dia + 1));
            itinerario.append("-------------------------------------------------------------------\n");
            //Si no hay actividades en el dia = (No hay actividades)
            if(numActividades == 0) {
                itinerario.append("(No hay actividades)\n");
                itinerario.append("\n-------------------------------------------------------------------\n");
            }
            // Print de las actividades por cada dia
            else {
                for (int j = 0; j < numActividades; j++) {
                    actividad = getActividadfromMatrix(dia, j);
                    itinerario.append(String.format("%s %s\n", actividad.getInicio(), actividad.getNombre()));
                    itinerario.append("\n-------------------------------------------------------------------\n");
                    totalActividades++;
                    precio += actividad.getPrecio();
                }
            }

        }
        //Guion final y resumen de gastos
        itinerario.append("Resumen:\n");
        itinerario.append(String.format("- Días: %d\n", numDias));
        itinerario.append(String.format("- Actividades: %d\n", totalActividades));
        itinerario.append(String.format("- Precio: %s\n", Utilidades.formatearPrecio(precio)));

        return itinerario.toString();
    }

    public void guardarItinerario(String nombreArchivo) throws IOException {
        // Guarda el itinerario en un archivo de texto (formato compacto)
    }
}
