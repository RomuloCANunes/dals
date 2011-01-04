package genetic.util;

/**
 * A simple counter class, with syncronized methods to prevent Thread concurrency
 * problems
 * @author romulo
 */
public class SynchronizedCounter {
    private int count = 0;

    /**
     * increments the counter
     */
    public synchronized void increment() {
        count++;
    }

    /**
     * decrements the counter
     */
    public synchronized void decrement() {
        count--;
    }

    /**
     * Retrieves the counter
     * @return
     */
    public int getCount() {
        return count;
    }
}
