package es.upm;

import java.io.IOException;
import java.util.Scanner;

/**
 * InterfazUsuario es una clase que encapsula toda la interaccion con el usuario, muestra un menu
 * para elegir entre las diferentes opciones que ofrece el programa. La clase se apoya en todas las demas
 * clases para ejecutar la accion pedida por el usuario. En esta clase son tratadas todas las excepciones
 * lanzadas por metodos en otras clases.
 *
 * @author Mario Aldeondo
 * @author Robert Voong
 * @version 1.0
 */
public class InterfazUsuario {

    /**
     * Constante que contiene un string con el mensaje para pedirle los recursos al usuario
     */
    private static final String PEDIR_REC = "Introduce los recursos (una línea por recurso, escribe 'fin' para terminar): ";
    /**
     * Constante que contiene un string con el mensaje para pedirle los comentarios al usuario
     */
    private static final String PEDIR_COM = "Introduce los comentarios (una línea por comentario, escribe 'fin' para terminar):";
    /**
     * Constante que contiene un string con el mensaje para pedir el nombre de una actividad
     */
    private static final String PEDIR_TXT = "Introduce el texto de la actividad a buscar (-FIN- para volver): ";

    /**
     * Constante que contiene un string con el mensaje para indicar que se añadan recursos a la actividad
     */
    private static final String PEDIR_MOD_REC = "Introduce el recurso a añadir: ";
    /**
     * Constante que contiene un string con el mensaje para indicar que se añadan comentarios a la actividad
     */
    private static final String PEDIR_MOD_COM = "Introduce el comentario a añadir: ";

    /**
     * Objeto de la clase CatalogoActividades
     */
    private CatalogoActividades catalogo;
    /**
     * Objeto de la clase Viaje
     */
    private Viaje viaje;
    /**
     * Numero maximo de recursos de una actividad
     */
    private int maxRecursos = 0;
    /**
     * Numero maximo de comentarios de una actividad
     */
    private int maxComentarios = 0;

    /**
     * Constructor de la clase InterfazUsuario para que siempre se inicialice con un objeto de la clase
     * CatalogoActividades y un objeto de la clase Viaje asi como un numero maximo de recursos y comentarios
     *
     * @param catalogo Objeto de la clase CatalogoActividades
     * @param viaje Objeto de la clase Viaje
     * @param maxRecursos Numero maximo de recursos
     * @param maxComentarios Numero maxio de comentarios
     */
    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
        this.catalogo = catalogo;
        this.viaje = viaje;

        if (maxRecursos >= 0 && maxComentarios >= 0) {
            this.maxRecursos = maxRecursos;
            this.maxComentarios = maxComentarios;
        } else throw new NumberFormatException();
    }

    /**
     * Metodo que es llamado desde el main para iniciar el menu principal,
     * contiene un objeto de la clase Scanner para leer la opcion del usuario
     *
     * @param scanner Objeto de la clase Scanner
     */
    public void iniciar(Scanner scanner) {
        menuPrincipal(scanner);
    }

    /**
     * Metodo que muestra el menu y en base a la opcion leida de teclado
     * efectua la accion correspondiente
     *
     * @param scanner Objeto de la clase Scanner
     */
    private void menuPrincipal(Scanner scanner) {
        int respuesta;
        
        do {
            mostrarMenu();

            respuesta = Utilidades.leerNumero(scanner, "Elige una opción: ", 1, 7);

            switch (respuesta) {
                case 1:
                    agregarActividad(scanner);
                    break;
            
                case 2:
                    consultarActividad(scanner);
                    break;
                
                case 3:
                    guardarActividades(scanner);
                    break;

                case 4:
                    cargarActividades(scanner);
                    break;

                case 5:
                    planificarViaje(scanner);
                    break;

                case 6:
                    guardarItinerario(scanner);
                    break;

                default:
                    break;
            }

        } while (respuesta != 7);
    }

    /**
     * Metodo usado para mostrar el menu de opciones en el formato especificado
     */
    private void mostrarMenu() {
        StringBuilder menu = new StringBuilder();

        menu.append("\n--- Menú Principal ___\n");
        menu.append("1. Agregar Actividad\n");
        menu.append("2. Consultar/Editar Actividad\n");
        menu.append("3. Guardar Actividades\n");
        menu.append("4. Cargar Actividades\n");
        menu.append("5. Planificar Viaje\n");
        menu.append("6. Guardar Itinerario\n");
        menu.append("7. Salir\n");

        System.out.print(menu.toString());
    }

    /**
     * Metodo usado para fijar las caracteristicas de una actividad y agregarla al itinerario
     * de actividades, si la actividad se agrega al itinerario se manda un mensaje de exito
     * y si no aparece un mensaje indicando el tipo de error producido
     *
     * @param scanner Objeto de la clase Scanner
     */
    private void agregarActividad(Scanner scanner) {
        String nombre = Utilidades.leerCadena(scanner,"Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner,"Descripción: ");
        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0, 1000);
        int duracion = Utilidades.leerNumero(scanner,"Duración (minutos): ", 1, Viaje.MINUTOS_MAXIMO);

        Actividad actividad = new Actividad(nombre, maxRecursos, maxComentarios);
        actividad.setDescripcion(descripcion);
        actividad.setPrecio(precio);
        actividad.setDuracionMinutos(duracion);

        String input;
        boolean full = false;

        System.out.print(PEDIR_REC);
        while (!((input = Utilidades.leerCadena(scanner, "")).equals("fin")) && !full) {
            switch (actividad.agregarRecurso(input)) {
                case Actividad.ERROR_VALOR_INVALIDO:
                    System.out.println("Valor inválido.");
                    break;

                case Actividad.ERROR_RECURSOS_COMPLETOS:
                    System.out.println("Recursos completos.");
                    full = true;
                    break;

                case Actividad.EXITO:
                    System.out.println("Éxito.");
                    break;

                default:
                    break;
            }
        }

        full = false;

        System.out.print(PEDIR_COM);
        while (!((input = Utilidades.leerCadena(scanner, "")).equals("fin")) && !full) {
            switch (actividad.agregarComentario(input)) {
                case Actividad.ERROR_VALOR_INVALIDO:
                    System.out.println("Valor inválido.");
                    break;

                case Actividad.ERROR_COMENTARIOS_COMPLETOS:
                    System.out.println("Comentarios completos.");
                    full = true;
                    break;

                case Actividad.EXITO:
                    System.out.println("Éxito.");
                    break;

                default:
                    break;
            }
        }

        switch (catalogo.agregarActividad(actividad)) {
            case CatalogoActividades.ERROR_DEMASIADOS:
                System.out.println("No se pueden añadir más actividades.");
                break;

            case CatalogoActividades.ERROR_ACTIVIDAD_NULL:
                System.out.println("Actividad nula.");
                break;

            default: // CatalogoActividades.EXITO
                System.out.println("¡Actividad agregada exitosamente!");
        }
    }

    /**
     * Lee la entrada del usuario con un objeto de la clase Scanner y busca actividades con el
     * mismo nombre que el proporcionado, a continuacion se muestra una lista con las actividades
     * que coinciden con el nombre de busqueda y se le pide al usuario que eliga una de ellas.
     * Una vez seleccionada la actividad a editar se podra añadir un recurso o comentario
     * o incluso borrar la actividad. Tras realizar la accion un mensaje que depende del exito
     * o fracaso de la operacion nos indica el resultado de la operacion, los funcionalidades de
     * buscar la actividad y editarla estan encapsuladas en otros metodos
     *
     * @param scanner Objeto de la clase Scanner
     */
    private void consultarActividad(Scanner scanner) {
        Actividad seleccionada = buscarActividadPorNombre(scanner);

        if (seleccionada != null) editarActividad(scanner, seleccionada);
    }


    /**
     * Metodo que encapsula la funcionalidad de buscar una actividad por nombre, se utiliza el metodo
     * buscarActividadPorNombre de la clase catalogo para encontrar las actividades que coinciden en nombre con
     * el proporcionado y luego se le pasa el array de actividades al metodo seleccionarActividad para que el
     * usuario escoja una de las coincidencias,la actividad seleccionada es lo que devuelve el metodo.
     *
     * @param scanner Objeto de la clase Scanner
     * @return Actividad resultante de la búsqueda
     */
    private Actividad buscarActividadPorNombre(Scanner scanner) {
        String busqueda = Utilidades.leerCadena(scanner, PEDIR_TXT);
        Actividad[] entrada = {};
        Actividad resultado = null;

        if (!(busqueda).equals("-FIN-")) {
            entrada = catalogo.buscarActividadPorNombre(busqueda);

            if (entrada.length != 0) resultado = seleccionarActividad(scanner, entrada);
            else System.out.println("Búsqueda sin resultados.");

        } else System.out.println("Búsqueda cancelada.");
        
        return resultado;
    }

    /**
     * Metodo que se usa para seleccionar una actividad de entre una lista de actividades, para esto el metodo
     * recibe un array de actividades y se le pide al usuario que escoga una indicando el indice de la que desee
     * el metodo retorna la actividad seleccionada.
     *
     * @param scanner Objeto de la clase Scanner
     * @param actividades Array de actividades
     * @return Actividad seleccionada
     */
    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        int respuesta;
        
        System.out.println("Actividades encontradas:");

        for (int i = 0; i < actividades.length; i++) {
            System.out.printf("%d. %s\n", i + 1, actividades[i].getNombre());
        }

        respuesta = Utilidades.leerNumero(scanner, "Elige una actividad: ", 1, actividades.length + 1);

        return actividades[respuesta - 1];
    }

    /**
     * Metodo usado para editar ciertos elementos de una actividad como los recursos,, comentarios o
     * eliminar la actividad al completo, para esto el metodo recibe como parametro un objeto de la clase
     * Actividad y en funcion de el exito o fracaso de la operacion se imprimen por pantalla distintos mensajes.
     *
     * @param scanner Objeto de la clase Scanner
     * @param seleccionada Objeto de la clase Actividad
     */
    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        int exitcode;
        int errorcode = -1;
        System.out.println(seleccionada);

        System.out.println("1. Añadir recurso\n2. Añadir comentario\n3. Eliminar actividad\n4. Volver");

        exitcode = Utilidades.leerNumero(scanner, "Elige una opción: ", 0, 4);
        switch (exitcode) {
            case 1: // Agregar recurso
                errorcode = seleccionada.agregarRecurso(Utilidades.leerCadena(scanner, PEDIR_MOD_REC));
                break;
        
            case 2: // Agregar comentario
                errorcode = seleccionada.agregarComentario(Utilidades.leerCadena(scanner, PEDIR_MOD_COM));
                break;
            
            case 3: // Eliminar actividad
                catalogo.eliminarActividad(seleccionada);
                System.out.println("Actividad eliminada.");
                break;
            
            default: // Volver
                break;
        }

        switch (errorcode) {
            case Actividad.ERROR_RECURSOS_COMPLETOS:
                System.out.println("Recursos llenos, no se pueden añadir.");
                break;
        
            case Actividad.ERROR_COMENTARIOS_COMPLETOS:
                System.out.println("Comentarios llenos, no se puede añadir.");
                break;

            case Actividad.ERROR_VALOR_INVALIDO:
                System.out.println("Valor inválido.");
                break;

            case Actividad.EXITO:
                if (exitcode == 1) System.out.println("Recurso añadido exitosamente.");
                else System.out.println("Comentario añadido correctamente.");

                break;

            default:
                break;
        }
    }

    /**
     * Metodo usado para guardar las actividades, se le pide al usuario que proporcione el nombre
     * del archivo donde desea guardar las actividades y el metodo usa funciones de la clase
     * CatalogoActividades para guardar las actividades. En este metodo se tratan las excepciones
     * lanzadas desde CatalogoActividades.
     *
     * @param scanner Objeto de la clase Scanner
     */
    private void guardarActividades(Scanner scanner) {
        String archivo = Utilidades.leerCadena(scanner,"Archivo donde guardar las actividades: ");
        try {
            catalogo.guardarActividades(archivo);
            System.out.printf("Actividades guardadas en %s",archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo.");
        }
    }

    /**
     * Metodo que pide al usuario el archivo del cual se quieren cargar las actividades y se apoya
     * en  el metodo de la clase CatalogoActividades para cargar la actividad seleccionada con el maximo numero
     * de recursos y comentarios establecidos en la clase InterfazUsuario, se trata en este metodo la excepcion lanzada
     * en la clase CatalogoActividades.
     *
     * @param scanner Objeto de la clase Scanner
     */
    private void cargarActividades(Scanner scanner) {
        String archivo = Utilidades.leerCadena(scanner, "Archivo de donde cargar las actividades: ");
        try {
            catalogo.cargarActividades(archivo, maxRecursos, maxComentarios);
            System.out.printf("Actividades cargadas desde %s", archivo);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo");
        }
    }

    /**
     * Metodo que muestra por pantalla el itinerario actual del viaje y permite al usuario
     * planificar una fecha y dia para una actividad ya existente, despues de escoger fecha y hora
     * se elige la actividad con el metodo buscarActividadPorNombre y en base al exito o fracaso se
     * muestra un mensaje con el resultado de la operacion.
     *
     * @param scanner Objeto de la clase Scanner
     */
    private void planificarViaje(Scanner scanner) {
        Actividad actividad;
        String hora;
        int dia;

        System.out.println("Planificación del viaje:");
        
        //En el ejemplo el formato que pone es sin el resumen de toString() pero entonces
        //hay que hacer un copia y pega de la mitad del codigo de toString()
        System.out.println(viaje);

        dia = Utilidades.leerNumero(scanner, "Introduce el día del viaje (1-" + viaje.getNumDias() + "): ", 1, viaje.getNumDias());

        hora = Utilidades.leerHora(scanner, "Introduce la hora de inicio (HH:MM): ");
        
        //Mensaje final que depende de si la actividad se ha agregado o no

        actividad = buscarActividadPorNombre(scanner);

        if (actividad != null) {
            switch (viaje.agregarActividad(dia + 1, actividad, hora)) {
                case Viaje.ERROR_DIA_INVALIDO:
                    System.out.println("Día inválido");
                    break;

                case Viaje.ERROR_DIA_COMPLETO:
                    System.out.println("No se pueden agregar más actividades a este día.");
                    break;

                case Viaje.ERROR_SOLAPAMIENTO:
                    System.out.println("La actividad se solapa con otra actividad ya planificada.");
                    break;

                case Viaje.EXITO:
                    System.out.printf("Actividad planificada para el día %d a las %s\n", dia, hora);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Metodo que se usa para guardar el itinerario del viaje, se pedira por pantalla el nombre
     * del archivo en el que se guardar el itinerario, la accion de guardar esta encapsulada dentro del
     * metodo guardarItinerario de la clase Viaje. En este metodo se trata la excepcion lanzada en la clase Viaje
     *
     * @param scanner Objeto de la clase Scanner
     */
    private void guardarItinerario(Scanner scanner) {
        String archivo = Utilidades.leerCadena(scanner, "Archivo donde guardar el itinerario: ");
        try{
            viaje.guardarItinerario(archivo);
            System.out.printf("Itinerario guardado en %s\n", archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo.");
        }
    }
}
