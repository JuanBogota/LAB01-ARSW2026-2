/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
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
