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

    private static final int MINUTOS_MINIMO = 0;
    private static final int MINUTOS_MAXIMO = Utilidades.horaAMinutos("23:59");

    //Atributos
    private int numDias; // Numero de dias que dura el viaje
    private int maxActividades; // Maximo de actividades por dia
    //rev1
    private CatalogoActividades[] matrActividades;

    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {
            matrActividades = new CatalogoActividades[numDias];
            for (int i = 0; i < numDias; i++) {
                matrActividades[i] = new CatalogoActividades(maxActividades);
            }

            this.numDias = numDias;
            this.maxActividades = maxActividades;
        } else System.out.println("Estos valores no tienen sentido");
    }

    public int getNumDias() { return numDias; }

    public boolean diaValido(int dia) { return (dia >= 0 && dia < numDias); }

    private boolean catalogoLleno(int index) { return matrActividades[index].actividadesCompletas(); }

    private Actividad getActividadfromMatrix(int dia, int index) {
        return matrActividades[dia].getCatalogo()[index];
    }

    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {
        int exitcode = -1;
        int minutos = Utilidades.horaAMinutos(horaInicio);
        int potential;

        int horaFinalAnterior = 0;
        int horaInicialPosterior = 1000;

        if (!diaValido(dia)) exitcode = ERROR_DIA_INVALIDO;
        else if (catalogoLleno(dia)) exitcode = ERROR_DIA_COMPLETO;
        else {
            actividad.setHora(minutos);
            potential = matrActividades[dia].indexHora(actividad.getHora());
            
            switch (matrActividades[dia].getNumActividades()) {
                case 0:
                    matrActividades[dia].agregarActividad(actividad);

                    exitcode = EXITO;
                    break;      
                default:
                    exitcode = EXITO;

                    try {
                        horaInicialPosterior = getActividadfromMatrix(dia, potential + 1).getHora();
                    }
                    catch (IndexOutOfBoundsException exception) {
                        horaInicialPosterior = MINUTOS_MAXIMO;
                    }
                    catch (NullPointerException exception) {
                        horaInicialPosterior = MINUTOS_MAXIMO;
                    }

                    try {
                        horaFinalAnterior = getActividadfromMatrix(dia, potential - 1).getHora();
                        horaFinalAnterior += getActividadfromMatrix(dia, potential - 1).getDuracionMinutos();
                    }
                    catch (IndexOutOfBoundsException exception) {
                        horaFinalAnterior = MINUTOS_MINIMO;
                    }
                    catch (NullPointerException exception) {
                        horaFinalAnterior = MINUTOS_MINIMO;
                    }

                    if (minutos >= horaFinalAnterior || (minutos + actividad.getDuracionMinutos()) <= horaInicialPosterior){
                        exitcode = ERROR_SOLAPAMIENTO;
                    }

                    break;
            }
            /* 
            exitcode = EXITO;
            
            actividad.setHora(minutos);

            potential = matrActividades[dia].indexHora(actividad.getHora());
            
            if (potential == 0) {
                horaInicialPosterior = getActividadfromMatrix(dia, potential + 1).getHora();

            } 
            else if (potential == matrActividades[dia].getNumActividades()) {
                horaFinalAnterior = getActividadfromMatrix(dia, potential - 1).getHora();
                horaFinalAnterior += getActividadfromMatrix(dia, potential - 1).getDuracionMinutos();
            
            } 
            else {
                horaInicialPosterior = getActividadfromMatrix(dia, potential + 1).getHora();
                
                horaFinalAnterior = getActividadfromMatrix(dia, potential - 1).getHora();
                horaFinalAnterior += getActividadfromMatrix(dia, potential - 1).getDuracionMinutos();
            }   

            if (minutos < horaFinalAnterior || (minutos + actividad.getDuracionMinutos()) > horaInicialPosterior){
                exitcode = ERROR_SOLAPAMIENTO;
            }
            else {
                matrActividades[dia].insertarActividad(actividad, potential);
            } 
*/

        }
        return exitcode;
    }

    public boolean eliminarActividad(int dia, String horaInicio) {
        boolean exitcode = false;
        int minutos = Utilidades.horaAMinutos(horaInicio);
        Actividad target;

        if (diaValido(dia)) {
            for (int i = 0; i < matrActividades[dia].getNumActividades(); i++) {
                target = getActividadfromMatrix(dia, i);
                
                if (minutos == target.getDuracionMinutos()) {
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

        int totalActividades = 0;
        double precio = 0;
        itinerario.append("-------------------------------------------------------------------\n");
        
        //Print de los giones + el dia cada dia
        for(int dia = 0; dia < numDias; dia++) {
            int numActividades = matrActividades[dia].getNumActividades();
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
                    Actividad actividad = getActividadfromMatrix(dia, j);
                    itinerario.append(String.format("%s %s\n", actividad.getHora(), actividad.getNombre()));
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
