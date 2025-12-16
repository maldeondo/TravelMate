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
            comentario = scanner.nextLine();
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
        Actividad seleccionada = buscarActividadPorNombre(scanner);

        editarActividad(scanner, seleccionada);
        
    }

    
    private Actividad buscarActividadPorNombre(Scanner scanner) {
        Actividad[] entrada = catalogo.buscarActividadPorNombre(Utilidades.leerCadena(scanner, "Actividad: "));

        return seleccionarActividad(scanner, entrada);
    }

    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        System.out.println("Actividades encontradas:");

        for (int i = 0; i < actividades.length; i++) {
            System.out.printf("%d %s", i, actividades[i].getNombre());
        }

        int repuesta = Utilidades.leerNumero(scanner, "Elige una actividad: ", 1, actividades.length + 1);

        return actividades[repuesta];
    }

    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        int errorcode = Actividad.EXITO;
        System.out.println(seleccionada);

        System.out.println("1. Añadir recurso\n2. Añadir comentario\n3. Eliminar actividad\n4. Volver");

        switch (Utilidades.leerNumero(scanner, "Elige una opción: ", 0, 4)) {
            case 1: // Agregar recurso
                errorcode = seleccionada.agregarRecurso(Utilidades.leerCadena(scanner, "null"));
                break;
        
            case 2: // Agregar comentario
                errorcode = seleccionada.agregarComentario(Utilidades.leerCadena(scanner, "null"));
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
                break;
        }
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
