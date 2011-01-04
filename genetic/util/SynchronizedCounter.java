/*
 * This file is part of DALS - Distributed Artificial Life Simulation.
 * DALS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DALS.  If not, see <http://www.gnu.org/licenses/>.
 */
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
