package edu.eci.arsw.blacklistvalidator;

/**
 * Esta clase será una clase de pruebas para la parte III del laboratorio, en la que se medirá el tiempo de ejecución de la búsqueda de listas negras para un host dado, variando el número de hilos utilizados.
 * MainPerformance
 */
public class MainPerformance {

    public static void main(String[] args) {

        int nucleos = Runtime.getRuntime().availableProcessors();
        System.out.println("Núcleos disponibles en esta máquina: " + nucleos);

        // Los 5 valores de N que pide la Parte III
        int[] valoresN = {
            1,
            nucleos,
            nucleos * 2,
            50,
            100
        };

        String ip = "202.24.34.55"; // IP dispersa según el enunciado

        System.out.println("\n=== Iniciando experimentos ===");
        System.out.println("Presiona Enter para continuar con cada experimento (dale tiempo a VisualVM de conectarse)\n");

        HostBlackListsValidator hblv = new HostBlackListsValidator();

        for (int n : valoresN) {
            esperarEnter("Listo para correr con N=" + n + " hilos. Presiona Enter...");

            long start = System.currentTimeMillis();
            hblv.checkHost(ip, n);
            long end = System.currentTimeMillis();

            long tiempoMs = end - start;
            System.out.println(">>> N=" + n + " hilos -> Tiempo: " + tiempoMs + " ms\n");
        }

        System.out.println("=== Experimentos finalizados ===");
    }

    private static void esperarEnter(String mensaje) {
        System.out.println(mensaje);
        try {
            System.in.read();
            // Limpia el buffer por si quedan caracteres (Enter = \r\n en algunos sistemas)
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // Ignorar, solo es una pausa manual
        }
    }
}