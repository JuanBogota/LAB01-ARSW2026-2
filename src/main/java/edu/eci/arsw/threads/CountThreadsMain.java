package edu.eci.arsw.threads;

/**
 * The CountThreadsMain class is the main entry point for the program that creates and starts multiple 
 * CountThread instances to count from a specified minimum to a maximum value.
 * min and max define the range of numbers to be counted, and numThreads specifies how many threads will be created to perform the counting.
 * @author Carlos Rojas
 * @author Juan Bogota
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        int min = 0;
        int max = 10;
        int numThreads = 3;

        int total = max - min + 1;
        int chunk = total / numThreads;

        CountThread[] threads = new CountThread[numThreads];
        for (int i = 0; i < numThreads; i++){
            int start = min + i * chunk;
            int end = (i == numThreads - 1) ? max : start + chunk - 1;
            threads[i] = new CountThread(start, end);
            threads[i].start();
        }
    }
    
}
