package es.upm;

import java.util.Scanner;

public class InterfazUsuario {

    private static final String PEDIR_REC = "Introduce los recursos (una línea por recurso, escribe 'fin' para terminar): ";
    private static final String PEDIR_COM = "Introduce los comentarios (una línea por comentario, escribe 'fin' para terminar):";
    private static final String PEDIR_TXT = "Introduce el texto de la actividad a buscar (-FIN- para volver): ";

    private static final String PEDIR_MOD_REC = "Introduce el recurso a añadir: ";
    private static final String PEDIR_MOD_COM = "Introduce el comentario a añadir: ";

    private CatalogoActividades catalogo;
    private Viaje viaje;
    private int maxRecursos = 0;
    private int maxComentarios = 0;

    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
       this.catalogo = catalogo;
       this.viaje = viaje;
       this.maxRecursos = maxRecursos;
       this.maxComentarios = maxComentarios;
    }

    public void iniciar(Scanner scanner) {
        menuPrincipal(scanner);
    }

    private void menuPrincipal(Scanner scanner) {
        int respuesta;
        
        do {
            mostrarMenu();

            respuesta = Utilidades.leerNumero(scanner, "", 1, 7);

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

    private void mostrarMenu() {
        StringBuilder menu = new StringBuilder();

        menu.append("--- Menú Principal ___\n");
        menu.append("1. Agregar Actividad\n");
        menu.append("2. Consultar/Editar Actividad\n");
        menu.append("3. Guardar Actividades\n");
        menu.append("4. Cargar Actividades\n");
        menu.append("5. Planificar Viaje\n");
        menu.append("6. Guardar Itinerario\n");
        menu.append("7. Salir\n");
        menu.append("Elige una opción:");

        System.out.println(menu.toString());
    }

    private void agregarActividad(Scanner scanner) {
        String nombre = Utilidades.leerCadena(scanner,"Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner,"Descripción: ");
        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0, 1000);
        int duracion = Utilidades.leerNumero(scanner,"Duración (minutos): ", 0, 1440);

        Actividad actividad = new Actividad(nombre, maxRecursos, maxComentarios);
        actividad.setDescripcion(descripcion);
        actividad.setPrecio(precio);
        actividad.setDuracionMinutos(duracion);

        String input;
        boolean full = false;

        System.out.println(PEDIR_REC);
        while (!((input = Utilidades.leerCadena(scanner, "")).equals("fin")) && !full) {
            switch (actividad.agregarRecurso(input)) {
                case Actividad.ERROR_VALOR_INVALIDO:
                    System.out.println("Valor inválido.");
                    break;
                case Actividad.ERROR_RECURSOS_COMPLETOS:
                    System.out.println("Recursos completos.");
                    full = true;
                    break;
                default: // Actividad.EXITO
                    System.out.println("Éxito.");
            }
            recursos = scanner.nextLine();
        }

        full = false;

        System.out.println(PEDIR_COM);
        while (!((input = Utilidades.leerCadena(scanner, "")).equals("fin")) && !full) {
            switch (actividad.agregarComentario(input)) {
                case Actividad.ERROR_VALOR_INVALIDO:
                    System.out.println("Valor inválido.");
                    break;
                case Actividad.ERROR_COMENTARIOS_COMPLETOS:
                    System.out.println("Comentarios completos.");
                    full = true;
                    break;
                default: // Actividad.EXITO
                    System.out.println("Éxito.");
            }
            comentarios = scanner.nextLine();
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

    private void consultarActividad(Scanner scanner) {
        Actividad seleccionada = buscarActividadPorNombre(scanner);

        editarActividad(scanner, seleccionada);
        
    }

    
    private Actividad buscarActividadPorNombre(Scanner scanner) {
        Actividad[] entrada = catalogo.buscarActividadPorNombre(Utilidades.leerCadena(scanner, PEDIR_TXT));

        return seleccionarActividad(scanner, entrada);
    }

    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        System.out.println("Actividades encontradas:");

        for (int i = 0; i < actividades.length; i++) {
            System.out.printf("%d. %s\n", i + 1, actividades[i].getNombre());
        }

        int repuesta = Utilidades.leerNumero(scanner, "Elige una actividad: ", 1, actividades.length + 1);

        return actividades[repuesta - 1];
    }

    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        int exitcode;
        int errorcode = Actividad.EXITO;
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

            default: // Actividad.EXITO
                if (exitcode == 1) System.out.println("Recurso añadido exitosamente.");
                else System.out.println("Comentario añadido correctamente.");

                break;
        }
    }

    private void guardarActividades(Scanner scanner) {
        String archivo = Utilidades.leerCadena(scanner,"Archivo donde guardar las actividades: ");
        try{
            catalogo.guardarActividades(archivo);
            System.out.printf("Actividades guardadas en %s",archivo);
        }catch(Exception e){
            System.out.println("Error al guardar el archivo.");
        }
    }

    private void cargarActividades(Scanner scanner) {
        String archivo = Utilidades.leerCadena(scanner,"Archivo de donde cargar las actividades");
        try{
            catalogo.cargarActividades(archivo,maxRecursos,maxComentarios);
            System.out.printf("Actividades cargadas desde %s",archivo);
        }catch(Exception e){
            System.out.println("Error al cargar el archivo");
        }
    }

    private void planificarViaje(Scanner scanner) {

        System.out.println("Planificación del viaje:");
        //En el ejemplo el formato que pone es sin el resumen de toString() pero entonces
        //hay que hacer un copia y pega de la mitad del codigo de toString()
        System.out.println(viaje.toString());
        int dia = Utilidades.leerNumero(scanner,"Introduce el dia del viaje (1-"+viaje.getNumDias()+"): ",1,viaje.getNumDias() );
        String hora = Utilidades.leerHora(scanner,"Introduce la hora de inicio (HH:MM): ");
        //Mensaje final que depende de si la actividad se ha agregado o no
        int error = viaje.agregarActividad(dia,buscarActividadPorNombre(scanner),hora);
        switch(error){
            case 0:
                System.out.printf("Actividad planificada para el dia %d a las %s\n", dia, hora);
                break;
            case 1:
                System.out.println("Día inválido");
                break;
            case 2:
                System.out.println("No se pueden agregar más actividades a este día.");
                break;
            case 3:
                System.out.println("La actividad se solapa con otra actividad ya planificada.");
                break;
        }

    }

    private void guardarItinerario(Scanner scanner) {
        String archivo = Utilidades.leerCadena(scanner, "Archivo donde guardar el itinerario: ");
        try{
            viaje.guardarItinerario(archivo);
            System.out.printf("Itinerario guardado en %s\n", archivo);
        }catch(Exception e){
            System.out.println("Error al guardar el archivo.");
        }
    }
}
