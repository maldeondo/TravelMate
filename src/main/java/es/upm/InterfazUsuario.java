package es.upm;

import java.util.Scanner;

public class InterfazUsuario {

    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
        // Crea la interfaz de usuario con el catálogo y viaje proporcionados
    }

    public void iniciar(Scanner scanner) {
        while(!(scanner.hasNext("7"))) mostrarMenu();
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
