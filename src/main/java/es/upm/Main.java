package es.upm;

public class Main {
    public static void main(String[] args) {
        Viaje viaje;
        CatalogoActividades catalogo;

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

            // FIXME MANAGE ALL EXCEPTIONS IN A NESTED TRY-CATCH BLOCK
            viaje = builder_Viaje(numDiasViaje, maxActividadesPorDia);

        } catch (NumberFormatException ex) {
            System.out.println("Argumentos inválidos.");

        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Faltan argumentos.");
        }
    }

    private static Viaje builder_Viaje(int numDias, int maxActividades) {
        return new Viaje(numDias, maxActividades);
    }

    private static void launcher() {

    }
}
