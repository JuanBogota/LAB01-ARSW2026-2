package edu.eci.arsw.threads;

/**
 * The CountThread class is a subclass of Thread that counts from a given starting integer to an ending integer.
 * It takes two integer parameters, a and b, which represent the starting and ending values of the count, respectively.
 * The run() method is overridden to perform the counting operation, printing each integer in the specified range to the console.
 * @author Juan Bogota
 * @author Carlos Rojas
 * @param a the starting integer of the count
 * @param b the ending integer of the count
 */
public class CountThread extends Thread{
    private final int a;
    private final int b;

    public CountThread(int a, int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        for (int i = a; i <= b; i++){
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }    

}
