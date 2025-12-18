package es.upm;

public class Main {
    public static void main(String[] args) {
        int maxRecursosPorActividad;
        int maxComentariosPorActividad;
        int maxActividadesEnCatalogo;
        int numDiasViaje;
        int maxActividadesPorDia;

        try {
            maxRecursosPorActividad = Integer.parseInt(args[0]);
            maxComentariosPorActividad = Integer.parseInt(args[1]);
            maxActividadesEnCatalogo = Integer.parseInt(args[2]);
            numDiasViaje = Integer.parseInt(args[3]);
            maxActividadesPorDia = Integer.parseInt(args[4]);

            builder(numDiasViaje, maxActividadesPorDia);

        } catch (NumberFormatException ex) {
            System.out.println("Argumentos inválidos.");

        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Faltan argumentos.");
        }
    }

    private static void builder() {
        Viaje viaje = new Viaje(0, 0)
        
    }

    private static void launcher() {

    }
}
