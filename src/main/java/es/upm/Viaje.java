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
    private Actividad[][] matrizActividades; // Para ordenar las actividades de cada dia
    private String[][] horasInicio; // Array con las horas iniciales de cada actividad en un dia

    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {
            this.numDias = numDias;
            this.maxActividades = maxActividades;
            matrizActividades = new Actividad[numDias][maxActividades];
            horasInicio = new String[numDias][maxActividades];
        } else System.out.println("Estos valores no tienen sentido");
    }

    public int getNumDias() {return numDias;}

    public boolean diaValido(int dia){ return (dia >= 0 && dia < numDias);}

    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {
        // Comprobacion dia valido
        if(!diaValido(dia)){ return ERROR_DIA_INVALIDO;}

        // Comprobacion dia con espacio para ctividades
        int numActividades = getNumActividadesDia(dia);
        if(numActividades >= maxActividades){ return ERROR_DIA_COMPLETO;}

        //Commprobacion solapamiento
        int horaActividadInicio = Utilidades.horaAMinutos(horaInicio);
        int horaActividadFinal = horaActividadInicio + actividad.getDuracionMinutos();
        for(int i = 0; i < numActividades; i++){
            String horaVieja = horasInicio[dia][i];
            Actividad actividadVieja = matrizActividades[dia][i];
            int horaViejaInicio = Utilidades.horaAMinutos(horaVieja);
            int horaViejaFinal = horaViejaInicio + actividadVieja.getDuracionMinutos();
            // Si la horaInicio del existente es mayor que la final del nuevo, entonces nunca se solapan ( siempre menor )
            // Si la hotraFinal existente es menor que la inicial del nuevo, nunca se solapan ( siempre mayor )
            if(!(horaViejaInicio >= horaActividadFinal || horaViejaFinal <= horaActividadInicio)){return ERROR_SOLAPAMIENTO;}
        }
        //Añadir actividad y ordenarla
        matrizActividades[dia][numActividades] = actividad;
        horasInicio[dia][numActividades] = horaInicio;
        ordenarActividadesDia(dia);

        return EXITO;
    }

    private void ordenarActividadesDia(int dia) {
        if(diaValido(dia)){

           int numActividades = getNumActividadesDia(dia);
            // Si hay menos de dos actividades no hay que ordenar nada
            if(numActividades >= 2){
                //Metodo buble sort para asegurar que se hace el suficiente numero de ordenamientos
                for(int i = 0; i < numActividades - 1; i++){
                    for(int j = 0; j < numActividades - 1; j++){
                        int hora1 = Utilidades.horaAMinutos(horasInicio[dia][j]);
                        int hora2 = Utilidades.horaAMinutos(horasInicio[dia][j + 1]);
                        if(hora1 > hora2){
                            /*
                            Guardamos la primera actividad(desordenada) en actividadPrimera, luego hacemos que la actividad
                            en la posicion j+1 vaya a j y por ultimo que actividadPrimera vaya a la posicion j+1
                             */
                            Actividad actividadPrimera = matrizActividades[dia][j];
                            matrizActividades[dia][j] = matrizActividades[dia][j + 1];
                            matrizActividades[dia][j + 1] = actividadPrimera;
                            // Lo mismo con las horas
                            String horaPrimera = horasInicio[dia][j];
                            horasInicio[dia][j] = horasInicio[dia][j + 1];
                            horasInicio[dia][j + 1] = horaPrimera;
                        }

                    }
                }

            }
        }
    }

    public boolean eliminarActividad(int dia, String horaInicio) {
        if(!diaValido(dia)){return false;}
        int numActividades = getNumActividadesDia(dia);
        for(int i = 0; i < numActividades; i++){
            if(horasInicio[dia][i].equals(horaInicio)){

                // Eliminar la actividad
                matrizActividades[dia][i] = null;
                horasInicio[dia][i] = null;

                /*Eliminar hueco vacio, le restamos 1 a maxActividades porque como
                 estamos mirando el de la derecha y poniendolo a la izquierda si
                 mirasemos a la derecha del limite nos daria una excepcion
                 */
                for(int j = i; j < numActividades - 1; j++){
                    matrizActividades[dia][j] = matrizActividades[dia][j+1];
                    horasInicio[dia][j] =  horasInicio[dia][j+1];
                }

                //Limpiamos ultimo hueco vacio del array
                matrizActividades[dia][numActividades - 1] = null;
                horasInicio[dia][numActividades - 1] = null;
                return true;
            }

        }
        return false;
    }
    // Metodo para obtener actividades de un dia especifico ordenadas por hora
    public Actividad[] obtenerActividadesDia(int dia) {

        //Solo dias validos
        if (!diaValido(dia)) return new Actividad[0];

        // Si no hay actividades ese dia se devuelve array vacio
        int numActividades = getNumActividadesDia(dia);
        if (numActividades == 0) return new Actividad[0];

        //Ordenamos las actividades del dia
        ordenarActividadesDia(dia);

        // Meter en un array las actividades de un dia ya ordenados
        Actividad[] actividades = new Actividad[numActividades];
        for(int i = 0; i < numActividades; i++){
            actividades[i] = matrizActividades[dia][i];
        }
        return actividades;
    }

// Metodo para obtener el numero de actividades de un dia contcreto
    public int getNumActividadesDia(int dia) {
        if (!diaValido(dia)) return 0;
        int numActividades = 0;
        for(int i = 0; i < maxActividades; i++){
            if(matrizActividades[dia][i] != null) numActividades++;
        }
        return numActividades;
    }

    @Override
    public String toString() {

        return null; // @todo MODIFICAR PARA DEVOLVER LA REPRESENTACIÓN TEXTUAL
    }

    public void guardarItinerario(String nombreArchivo) throws IOException {
        // Guarda el itinerario en un archivo de texto (formato compacto)
    }
}
