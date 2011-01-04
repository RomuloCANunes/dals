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
package genetic.fenotipe;

/**
 * A helper class to control slices
 * @author romulo
 */
public class Slice {
    private int initial, size, end;

    /**
     * Creates a new instance
     * @param initial
     * @param size
     */
    public Slice(int initial, int size)
    {
        this.initial = initial;
        this.size = size;
        this.end = initial + size;
    }

    /**
     * Returns the end position of the slice
     * @return
     */
    public int getEnd() {
        return end;
    }

    /**
     * Returns the inicial slice position
     * @return
     */
    public int getInitial() {
        return initial;
    }

    /**
     * Returns the slice size
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * This Sclice's String representation
     * @return
     */
    @Override
    public String toString() {
        return "Slice {" + initial + "," + end + "}";
    }
}
