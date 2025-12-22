package es.upm;

import java.io.IOException;
import java.util.Scanner;

/**
 * Clase simple que usa los argumentos recibidos de la ejecución por terminal (o perfil de lanzamiento del IDE) para
 * crear los objetos necesarios de cada clase para el correcto funcionamiento. También maneja las excepciones tanto
 * relacionadas con los argumentos entrantes como las que se puedan producir internamente, escribiendo los
 * mensajes correspondientes.
 *
 * Los argumentos se esperan en el orden siguiente:
 *
 * int maxRecursosPorActividad <- args[0]
 * int maxComentariosPorActividad <- args[1]
 * int maxActividadesEnCatalogo <- args[2]
 * int numDiasViaje <- args[3]
 * int maxActividadesPorDia <- args[4]
 * String nombreArchivoActividades <- args[5] (opcional)
 *
 * @author Mario Aldeondo
 * @author Robert Voong
 * @version 1.0
 */
public class Main {
    /**
     * Función main que sirve como punto de entrada del programa, dados los argumentos.
     *
     * Declara e instancia los objetos de cada clase teniendo en cuenta las excepciones posibles, lanzando mensajes:
     *
     * Argumentos incorrectos o absurdos (NumberFormatException) -> "Argumentos inválidos."
     * Argumentos faltantes (IndexOutOfBoundsException) -> "Argumentos faltantes."
     * Error de entrada/salida a archivos (IOException) -> "Error de carga de archivo."
     * Excepciones no esperadas (Exception) -> "Error desconocido."
     *
     * En el proceso llama al resto de funciones para crear los objetos con los argumentos necesarios.
     *
     * @param args Array de Strings con los argumentos introducidos
     */
    public static void main(String[] args) {
        Viaje viaje = null;
        CatalogoActividades catalogo = null;
        InterfazUsuario interfaz = null;
        Scanner sc = null;

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

    /**
     * Método que construye el catálogo usando args[2].
     *
     * Si se introduce un valor que sí es numérico, pero es absurdo (ej: -1), lanza una excepción
     * NumberFormatException que capta la función main.
     *
     * @param args Array de Strings con los argumentos introducidos
     * @return Objeto creado de la clase CatalogoActividades
     * @throws NumberFormatException Excepción si se introducen valores no válidos
     */
    private static CatalogoActividades builder_Catalogo(String[] args) throws NumberFormatException {
        int maxActividadesEnCatalogo = Integer.parseInt(args[2]);
        return new CatalogoActividades(maxActividadesEnCatalogo);
    }

    /**
     * Método que construye el catálogo usando args[3] y args[4].
     *
     * Si se introduce un valor que sí es numérico, pero es absurdo (ej: -1), lanza una excepción
     * NumberFormatException que capta la función main.
     *
     * @param args Array de Strings con los argumentos introducidos
     * @return Objeto creado de la clase Viaje
     * @throws NumberFormatException Excepción si se introducen valores no válidos
     */
    private static Viaje builder_Viaje(String[] args) throws NumberFormatException {
        int numDiasViaje = Integer.parseInt(args[3]);
        int maxActividadesPorDia = Integer.parseInt(args[4]);

        return new Viaje(numDiasViaje, maxActividadesPorDia);
    }

    /**
     * Método que construye el catálogo usando args[0] y args[1].
     *
     * Si se introduce un valor que sí es numérico, pero es absurdo (ej: -1), lanza una excepción
     * NumberFormatException que capta la función main.
     *
     * @param args Array de Strings con los argumentos introducidos
     * @param catalogo Objeto catálogo previamente creado
     * @param viaje Objeto Viaje previamente creado
     * @return Objeto creado de la clase InterfazUsuario
     * @throws NumberFormatException Excepción si se introducen valores no válidos
     */
    private static InterfazUsuario builder_Interfaz(String[] args, CatalogoActividades catalogo, Viaje viaje) throws NumberFormatException {
        int maxRecursosPorActividad = Integer.parseInt(args[0]);
        int maxComentariosPorActividad = Integer.parseInt(args[1]);
        
        return new InterfazUsuario(catalogo, viaje, maxRecursosPorActividad, maxComentariosPorActividad);
    }

    /**
     * Método que construye el catálogo usando args[0], args[1] y args[5].
     *
     * Si se introduce un valor que sí es numérico, pero es absurdo (ej: -1), lanza una excepción
     * NumberFormatException que capta la función main. Si no se puede leer, ocurre lo mismo con una IOException.
     *
     * Este paso es opcional, solo ocurre si se introducen exactamente 6 argumentos en el lanzamiento del programa.
     *
     * @param args Array de Strings con los argumentos introducidos
     * @param catalogo Objeto catálogo previamente creado
     * @throws IOException Excepción si no se puede leer del archivo solicitado
     */
    private static void read_from_file(String[] args, CatalogoActividades catalogo) throws IOException {
        int maxRecursosPorActividad = Integer.parseInt(args[0]);
        int maxComentariosPorActividad = Integer.parseInt(args[1]);

        String nombreArchivoActividades = args[5];

        catalogo.cargarActividades(nombreArchivoActividades, maxRecursosPorActividad, maxComentariosPorActividad);
    }

    /**
     * Método que lanza finalmente interfaz.iniciar, que comienza con el funcionamiento
     * normal del programa. Aportando un Scanner de System.in.
     *
     * @param interfaz Objeto InterfazUsuario previamente creado
     * @param sc Objeto Scanner a instanciar en función de System.in
     */
    private static void launcher(InterfazUsuario interfaz, Scanner sc) {
        sc = new Scanner(System.in);
        
        interfaz.iniciar(sc);
    }
}
