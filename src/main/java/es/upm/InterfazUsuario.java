package es.upm;

import java.util.Scanner;

public class InterfazUsuario {

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
        while(!(scanner.hasNextInt(7))) mostrarMenu();
    }

    private void menuPrincipal(Scanner scanner) {
        // Ejecuta el bucle del menú principal hasta que el usuario decida salir
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
       int recursosMax = 0;
       int comentariosMax = 0;

        String nombre = Utilidades.leerCadena(scanner,"Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner,"Descripción: ");
        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0, 1000);
        int duracion = Utilidades.leerNumero(scanner,"Duración (minutos): ", 0, 1440);
        Actividad actividad = new Actividad(nombre, maxRecursos, maxComentarios);
        actividad.setDescripcion(descripcion);
        actividad.setPrecio(precio);
        actividad.setDuracionMinutos(duracion);
        String comentario = Utilidades.leerCadena(scanner,"Introduce los recursos (una linea por recurso, escribe 'fin' para terminar): ");
        boolean check = true;
        while (check){
            if(comentario.equals("fin")) break;
            int error = actividad.agregarRecurso(comentario);
            switch(error){
                case 1:
                    System.out.println("Valor Invalido");
                    break;
                case 2:
                    System.out.println("No se pueden añadir más recursos.");
                    check = false;
                    break;
            }
        }
        System.out.println("Introduce los comentarios (una linea por comentario, escribe 'fin' para terminar):");
        boolean check1 = true;
        while (check1){
            String comentario = scanner.nextLine();
            if(comentario.equals("fin")) break;
            int error = actividad.agregarComentario(comentario);
            switch(error){
                case 1:
                    System.out.println("Valor Invalido");
                    break;
                    case 3:
                        System.out.println("No se pueden añadir más comentarios.");
                        check1 = false;
                        break;
            }
        }
        int agregar = catalogo.agregarActividad(actividad);
                if(agregar == 0 ) System.out.println("¡Actividad agregada exitosamente!");
                else System.out.println("No se pueden añadir más actividades.");

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
