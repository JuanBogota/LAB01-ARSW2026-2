package edu.eci.arsw.blacklistvalidator;

/**
 * Test harness for Part III of the lab assignment: Performance Evaluation.
 * This class measures the execution time of {@link HostBlackListsValidator#checkHost}
 * while varying the number of threads (N) used to parallelize the black list
 * search for a given host IP address.
 * The following five experiments are executed sequentially, pausing for user
 * input between each one to allow time for monitoring tools (e.g. VisualVM)
 * to capture CPU and memory usage:
 *   N = 1 thread (sequential baseline)
 *   N = number of available processor cores
 *   N = twice the number of available processor cores
 *   N = 50 threads
 *   N = 100 threads
 *
 * @author Juan Daniel Bogotá
 * @author Carlos Rojas
 * @version 1.0
 * @since 2026-08-07
 */
public class MainPerformance {

    public static void main(String[] args) {

        int nucleos = Runtime.getRuntime().availableProcessors();
        System.out.println("Núcleos en esta máquina: " + nucleos);

        int[] valoresN = {
            1,
            nucleos,
            nucleos * 2,
            50,
            100
        };

        String ip = "202.24.34.55";

        System.out.println("\n Iniciando experimentos");
        System.out.println("Presiona Enter para continuar con cada experimento\n");

        HostBlackListsValidator hblv = new HostBlackListsValidator();

        for (int n : valoresN) {
            esperarEnter("Listo para correr con N=" + n + " hilos. Presiona Enter");

            long start = System.currentTimeMillis();
            hblv.checkHost(ip, n);
            long end = System.currentTimeMillis();

            long tiempoMs = end - start;
            System.out.println(" N=" + n + " hilos -> Tiempo: " + tiempoMs + " ms\n");
        }

        System.out.println("=== Experimentos finalizados ===");
    }

    private static void esperarEnter(String mensaje) {
        System.out.println(mensaje);
        try {
            System.in.read();
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
        }
    }
}