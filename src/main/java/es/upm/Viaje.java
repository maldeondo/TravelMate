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
    private int numDias; // Numero de dias que dura el viaje
    private int maxActividades; // Maximo de actividades por dia
    private int actividadesTotales; // Actividades totales en todo el viaje
    private Actividad[][] matrizActividades; // Para ordenar las actividades de cada dia
    private String[] horasInicio; // Array con las horas iniciales de cada actividad en un dia

    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {
            this.numDias = numDias;
            this.maxActividades = maxActividades;
            actividadesTotales = numDias * maxActividades;
            matrizActividades = new Actividad[numDias][maxActividades];
            horasInicio = new String[maxActividades];
        } else System.out.println("Estos valores no tienen sentido");
    }

    public int getNumDias() {return numDias;}

    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {

        return 0;
    }


    // No he visto esta funcion porque no sale en el enunciado pero su funcion
    // se hace en obtenerActividad
    private void ordenarActividadesDia(int dia) {
        // Ordena las actividades de un día por hora de inicio (método de burbuja)
    }

    public boolean eliminarActividad(int dia, String horaInicio) {
        for(int i = 0; i < maxActividades; i++){
            if(horasInicio[i] != null && horasInicio[i].equals(horaInicio)){

                // Eliminar la actividad
                matrizActividades[dia][i] = null;
                horasInicio[i] = null;

                /*Eliminar hueco vacio, le restamos 1 a maxActividades porque como
                 estamos mirando el de la derecha y poniendolo a la izquierda si
                 mirasemos a la derecha del limite nos daria una excepcion
                 */
                for(int j = i; j < maxActividades - 1; j++){
                    matrizActividades[dia][j] = matrizActividades[dia][j+1];
                    horasInicio[j] =  horasInicio[j+1];
                }

                //Limpiamos ultimo hueco vacio del array
                matrizActividades[dia][maxActividades - 1] = null;
                horasInicio[maxActividades - 1] = null;
            }
            return true;
        }
        return false;
    }
    // Metodo para obtener actividades de un dia especifico ordenadas por hora
    public Actividad[] obtenerActividadesDia(int dia) {

        //Solo dias validos
        if (dia < 0 || dia > numDias) return new Actividad[0];

        // Si no hay actividades ese dia se devuelve array vacio
        int numActividades = getNumActividadesDia(dia);
        if (numActividades == 0) return new Actividad[0];

        // Meter en un array las actividades de un dia
        Actividad[] actividades = new Actividad[numActividades];
        String[] horasOrden = new String[numActividades];
        int posicion = 0;
        for (int i = 0; i < numActividades; i++){
            actividades[posicion] = matrizActividades[dia][i];
            horasOrden[posicion] = horasInicio[i];
            posicion++;
        }

        // Intercambiar actividades que no esten en orden por tiempo
        // Se necesitan dos for porque con uno no son suficientes comprobaciones
        // for desde 0 porque son valores del array y -1 numActividades porque
        // numActividades empieza desde 1
        for(int i = 0; i < numActividades - 1; i++){
            for(int j = 0; j < numActividades - 1; j++){
                int hora1 = Utilidades.horaAMinutos(horasOrden[j]);
                int hora2 = Utilidades.horaAMinutos(horasOrden[j + 1]);

                if(hora1 > hora2){
                    /* actividadPrimera apunta al primer valor del array, el primer valor
                    apunta al segundo y por ultimo el segundo apunta al actividadPrimera
                    apuntaba al primer array y asi se cambian los valores
                     */
                    Actividad actividadPrimera = actividades[j];
                    actividades[j] = actividades[j + 1];
                    actividades[j + 1] = actividadPrimera;
                    // Lo mismo con las horas
                    String horaPrimera = horasOrden[j];
                    horasOrden[j] = horasOrden[j + 1];
                    horasOrden[j + 1] = horaPrimera;
                }
            }
        }
        return actividades;
    }

// Metodo para obtener el numero de actividades de un dia contcreto
    public int getNumActividadesDia(int dia) {
        int numActividades = 0;
        for(int i = 0; i < maxActividades; i++){
            if(matrizActividades[dia][i] != null) numActividades++;
        }
        return numActividades;
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
