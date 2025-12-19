package es.upm;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Viaje viaje = null;
        CatalogoActividades catalogo = null;
        InterfazUsuario interfaz = null;
        Scanner sc = null;

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
            
            try {
                catalogo = builder_Catalogo(maxActividadesEnCatalogo);
                viaje = builder_Viaje(numDiasViaje, maxActividadesPorDia);
                interfaz = builder_Interfaz(catalogo, viaje, maxRecursosPorActividad, maxComentariosPorActividad);

                launcher(interfaz, sc);

            } catch (Error ex) { throw new NumberFormatException(); }

            // TODO MANAGE SIXTH ARGUMENT
            if (args.length == 5) {

            }
            

        } catch (NumberFormatException ex) {
            System.out.println("Argumentos inválidos.");

        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Faltan argumentos.");
        }
    }

    private static CatalogoActividades builder_Catalogo(int maxActividadesEnCatalogo) throws Error {
        return new CatalogoActividades(maxActividadesEnCatalogo);
    }

    private static Viaje builder_Viaje(int numDias, int maxActividades) throws Error {
        return new Viaje(numDias, maxActividades);
    }

    private static InterfazUsuario builder_Interfaz(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
        return new InterfazUsuario(catalogo, viaje, maxRecursos, maxComentarios);
    }

    private static void launcher(InterfazUsuario interfaz, Scanner sc) {
        sc = new Scanner(System.in);
        
        interfaz.iniciar(sc);
    }
}
