package es.upm;

import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Clase simple que usa los argumentos recibidos de la ejecución por terminal (o perfil de lanzamiento del IDE) para
 * crear los objetos necesarios de cada clase para el correcto funcionamiento. También maneja las excepciones tanto
 * relacionadas con los argumentos entrantes como las que se puedan producir internamente, escribiendo los
 * mensajes correspondientes.
 * Los argumentos se esperan en el orden siguiente:
 * int maxRecursosPorActividad -> args[0]
 * int maxComentariosPorActividad -> args[1]
 * int maxActividadesEnCatalogo -> args[2]
 * int numDiasViaje -> args[3]
 * int maxActividadesPorDia -> args[4]
 * String nombreArchivoActividades -> args[5] (opcional)
 *
 * @author Mario Aldeondo
 * @author Robert Voong
 * @version 1.0
 */
public class Main {
    public static final String HELP_L = "--help";
    public static final String HELP_S = "-h";
    public static final String VERSION_L = "--version";
    public static final String VERSION_S = "-v";

    public static final String HELP_B = 
    "Uso: java -jar TravelMate.jar 1 2 3 4 5 6\n" +
    "  1 -> Máximo de recursos por actividad\n" +
    "  2 -> Máximo de comentarios por actividad\n" +
    "  3 -> Máximo de actividades en catálogo\n" +
    "  4 -> Número de días del viaje\n" +
    "  5 -> Máximo de actividades por día\n" +
    "  6 -> (Opcional) Nombre del archivo de actividades\n" +
    "Opciones:\n" +
    "  -h o --help -> Muestra esta información\n" +
    "  -v o --version -> Muestra la versión de TravelMate\n" +
    "Reporte de fallos en https://github.com/maldeondo/TravelMate\n";

    public static final String VERSION_B = 
        "TravelMate v0.9.1 (pre-release)\n" +
        "Copyright (c) 2026 [Mario Aldeondo @maldeondo] and [Robert Voong @mantaimpermeable]\n" +
        "https://github.com/maldeondo/TravelMate\n";

    public static final String ERR_B = 
        "TravelMate: %s\n" +
        "Escribe -h o --help para más información.\n";

    /**
     * Función de entrada que, antes de empezar el proceso, omite los elementos sobrantes de los argumentos
     * en caso de que existan (a partir de 6).
     *
     * @param args Array de Strings con los argumentos introducidos
     */
    public static void main(String[] args) {
        args = Arrays.copyOf(args, 6);
        process(args);
    }

    /**
     * Función process que sirve como punto de entrada real del programa, dados los argumentos.
     * Comprueba si se han pasado las flags -h o -v para lanzar los mensajes y llama a initialize 
     * para comenzar la ejecución. También lanza los mensajes de error correspondientes mediante ERR_B.
     *
     * @param args Array de Strings con los argumentos introducidos
     */
    private static void process(String[] args) {
        try {

            if (args[0].equals(HELP_L) || args[0].equals(HELP_S)) {
                System.out.print(HELP_B);

            } else if (args[0].equals(VERSION_L) || args[0].equals(VERSION_S)) {
                System.out.print(VERSION_B);

            } else initialize(args);

        } catch (NumberFormatException ex) {
            System.out.printf(ERR_B, "Argumentos inválidos.");

        } catch (IndexOutOfBoundsException ex) {
            System.out.printf(ERR_B, "Argumentos inválidos.");

        } catch (NullPointerException ex) {
            System.out.printf(ERR_B, "Argumentos inválidos.");

        } catch (IOException ex) {
            System.out.printf(ERR_B, "Error de carga de archivo.");

        } catch (Exception ex) {
            System.out.printf(ERR_B, "Error desconocido.");
        }
    }

    /**
     * Declara e instancia los objetos de cada clase teniendo en cuenta las excepciones posibles. 
     * En el proceso llama al resto de funciones para crear los objetos con los argumentos necesarios,
     * incluyendo comprobar si se quieren cargar actividades desde un archivo.
     *
     * @param args Array de Strings con los argumentos introducidos
     * @throws NumberFormatException Excepción por valores absurdos al instanciar los objetos
     * @throws IOException Excepción por errores de carga y lectura de archivo
     * @throws Exception Excepción general que indica fallo interno del software (bug)
     */
    private static void initialize(String[] args) throws NumberFormatException, IOException, Exception {
        Viaje viaje = null;
        CatalogoActividades catalogo = null;
        InterfazUsuario interfaz = null;
        Scanner sc = null;

        catalogo = builder_Catalogo(args);

            if (args[5] != null) read_from_file(args, catalogo);

            viaje = builder_Viaje(args);
            interfaz = builder_Interfaz(args, catalogo, viaje);

            try {
                System.out.print(VERSION_B);
                launcher(interfaz, sc);
            } catch (Exception ex) { throw new Exception(); }
    }

    /**
     * Método que construye el catálogo usando args[2].
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
     * Si se introduce un valor que sí es numérico, pero es absurdo (ej: -1), lanza una excepción
     * NumberFormatException que capta la función main. Si no se puede leer, ocurre lo mismo con una IOException.
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
