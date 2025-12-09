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
    //rev1
    private CatalogoActividades[] matrActividades;

    public Viaje(int numDias, int maxActividades) {
        if (numDias > 0 && maxActividades > 0 ) {
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

    private Actividad getActividadfromMatrix(int dia, int index) {
        return matrActividades[dia].getCatalogo()[index];
    }

    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {
        int exitcode = -1;
        int inicio = Utilidades.horaAMinutos(horaInicio);
        int fin = inicio + actividad.getDuracionMinutos();
        int target;

        if (!diaValido(dia)) exitcode = ERROR_DIA_INVALIDO;
        else if (catalogoLleno(dia)) exitcode = ERROR_DIA_COMPLETO;
        else {
            exitcode = EXITO;

            actividad.setHora(inicio);
            target = matrActividades[dia].indexHora(actividad.getHora());
            
            if (!(actividadesSolapan(dia, target, inicio, fin))) matrActividades[dia].insertarActividad(actividad, target);
            else exitcode = ERROR_SOLAPAMIENTO;
        }

        return exitcode;
    }

    private boolean actividadesSolapan(int dia, int index, int inicio, int fin) {
            int horaInicialPosterior = MINUTOS_MAXIMO, horaFinalAnterior = MINUTOS_MINIMO;

            switch (matrActividades[dia].getNumActividades()) {
                case 0:
                    break;
                    
                case 1:
                    if (index == 0) {
                        horaInicialPosterior = getActividadfromMatrix(dia, index).getHora();
                    } else {
                        horaFinalAnterior = getActividadfromMatrix(dia, index - 1).getHora();
                        horaFinalAnterior += getActividadfromMatrix(dia, index - 1).getDuracionMinutos();
                    }

                    break;

                default:
                    try {
                        horaInicialPosterior = getActividadfromMatrix(dia, index + 1).getHora();
                    }
                    catch (Exception exception) {}

                    try {
                        horaFinalAnterior = getActividadfromMatrix(dia, index - 1).getHora();
                        horaFinalAnterior += getActividadfromMatrix(dia, index - 1).getDuracionMinutos();
                    }
                    catch (Exception exception) {}
                    
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
                
                if (minutos == target.getHora()) {
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
