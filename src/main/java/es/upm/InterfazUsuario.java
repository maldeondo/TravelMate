package es.upm;

import java.util.Scanner;

public class InterfazUsuario {

    public static final String intr1msg = "Introduce los recursos (una linea por recurso, escribe 'fin' para terminar): ";
    public static final String intr2msg = "Introduce los comentarios (una linea por comentario, escribe 'fin' para terminar):";

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
        while(!(scanner.hasNextInt(7))) menuPrincipal(scanner);
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
        menu.append("--- Menú Principal ___");
        menu.append("1. Agregar Actividad");
        menu.append("2. Consultar/Editar Actividad");
        menu.append("3. Guardar Actividades");
        menu.append("4. Cargar Actividades");
        menu.append("5. Planificar Viaje");
        menu.append("6. Guardar Itinerario");
        menu.append("7. Salir");
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

        System.out.println(intr1msg);
        while (!((input = Utilidades.leerCadena(scanner, "")).equals("fin")) && !full) {
            switch (actividad.agregarRecurso(input)) {
                case 1:
                    System.out.println("Valor inválido.");
                    break;
                case 2:
                    System.out.println("Recursos completos.");
                    full = true;
                    break;
                default:
                    System.out.println("Éxito.");
            }
        }

        full = false;

        System.out.println(intr2msg);
        while (!((input = Utilidades.leerCadena(scanner, "")).equals("fin")) && !full) {
            switch (actividad.agregarRecurso(input)) {
                case 1:
                    System.out.println("Valor inválido.");
                    break;
                case 3:
                    System.out.println("Comentarios completos.");
                    full = true;
                    break;
                default:
                    System.out.println("Éxito.");
            }
        }

        switch (catalogo.agregarActividad(actividad)) {
            case 2:
                System.out.println("No se pueden añadir más actividades.");
                break;
            case 1:
                System.out.println("Actividad nula.");
                break;
            default:
                System.out.println("¡Actividad agregada exitosamente!");
        }
    }

    private void consultarActividad(Scanner scanner) {
        // Busca una actividad y permite editarla
    }

    
    private Actividad buscarActividadPorNombre(Scanner scanner) {
        // Busca actividades por nombre y permite seleccionar una
        return null; // @todo MODIFICAR PARA DEVOLVER LA ACTIVIDAD SELECCIONADA
    }

    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        // Muestra un listado numerado de actividades y permite elegir una
        return null; // @todo MODIFICAR PARA DEVOLVER LA ACTIVIDAD SELECCIONADA
    }

    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        // Muestra la actividad y permite añadir recursos, comentarios o eliminarla
    }

    private void guardarActividades(Scanner scanner) {
        // Lee el nombre del archivo y guarda las actividades del catálogo
    }

    private void cargarActividades(Scanner scanner) {
        // Lee el nombre del archivo y carga actividades al catálogo
    }

    private void planificarViaje(Scanner scanner) {
        // Muestra el itinerario actual y permite agregar actividades a días específicos
    }

    private void guardarItinerario(Scanner scanner) {
        // Lee el nombre del archivo y guarda el itinerario del viaje
    }
}
