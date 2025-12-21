package es.upm;

import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Viaje viaje = null;
        CatalogoActividades catalogo = null;
        InterfazUsuario interfaz = null;
        Scanner sc = null;

        //int maxRecursosPorActividad <- args[0]
        //int maxComentariosPorActividad <- args[1]
        //int maxActividadesEnCatalogo <- args[2]
        //int numDiasViaje <- args[3]
        //int maxActividadesPorDia <- args[4]
        //String nombreArchivoActividades <- args[5]

        try {
            catalogo = builder_Catalogo(args);

            if (args.length == 6) read_from_file(args, catalogo);

            viaje = builder_Viaje(args);
            interfaz = builder_Interfaz(args, catalogo, viaje);

            try {
                launcher(interfaz, sc);
            } catch (NumberFormatException ex) { throw new Exception(); }
            

        } catch (NumberFormatException ex) {
            System.out.println("Argumentos inválidos.");

        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Faltan argumentos.");

        } catch (IOException ex) {
            System.out.println("Error de carga de archivo.");

        } catch (Exception ex) {
            System.out.println("Error desconocido.");
        }
    }

    private static CatalogoActividades builder_Catalogo(String[] args) throws NumberFormatException {
        int maxActividadesEnCatalogo = Integer.parseInt(args[2]);
        return new CatalogoActividades(maxActividadesEnCatalogo);
    }

    private static Viaje builder_Viaje(String[] args) throws NumberFormatException {
        int numDiasViaje = Integer.parseInt(args[3]);
        int maxActividadesPorDia = Integer.parseInt(args[4]);

        return new Viaje(numDiasViaje, maxActividadesPorDia);
    }

    private static InterfazUsuario builder_Interfaz(String[] args, CatalogoActividades catalogo, Viaje viaje) throws NumberFormatException {
        int maxRecursosPorActividad = Integer.parseInt(args[0]);
        int maxComentariosPorActividad = Integer.parseInt(args[1]);
        
        return new InterfazUsuario(catalogo, viaje, maxRecursosPorActividad, maxComentariosPorActividad);
    }

    private static void read_from_file(String[] args, CatalogoActividades catalogo) throws IOException {
        int maxRecursosPorActividad = Integer.parseInt(args[0]);
        int maxComentariosPorActividad = Integer.parseInt(args[1]);

        String nombreArchivoActividades = args[5];

        catalogo.cargarActividades(nombreArchivoActividades, maxRecursosPorActividad, maxComentariosPorActividad);
    }

    private static void launcher(InterfazUsuario interfaz, Scanner sc) {
        sc = new Scanner(System.in);
        
        interfaz.iniciar(sc);
    }
}
