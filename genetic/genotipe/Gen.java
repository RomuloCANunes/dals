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
package genetic.genotipe;

/**
 * Represents one gen in a DNA sequence
 * @todo make a carachteristic to have remisive or dominant gens
 *
 * @author romulo
 */
public class Gen
{

    private boolean value;

    /**
     * Retrieves this gen value
     * @return
     */
    public boolean getValue()
    {
        return value;
    }

    /**
     * Creates a new gen with a random value
     */
    public Gen()
    {
        value = Math.round( Math.random() ) == 0;
    }

    /**
     * Creates a new gen with a predefined boolean value
     * @param value
     */
    public Gen(boolean value)
    {
        this.value = value;
    }

    /**
     * Parses a char into a gen
     * @param value
     * @return
     */
    public static Gen parseChar(char value)
    {
        return new Gen( value == '1' );
    }

    /**
     * Inverts the value of a gen
     */
    public void invertValue()
    {
        value = ! value;
    }

    /**
     * Compares this instance with value
     * @param value
     * @return
     */
    public boolean equals(boolean value)
    {
        return this.value == value;
    }

    /**
     * Compares this Gen with another
     * @param gen
     * @return
     */
    public boolean equals(Gen gen)
    {
        return this.equals( gen.getValue() );
    }

    /**
     * Returns a String representation of this instance
     * @return
     */
    @Override
    public String toString()
    {
        return value ? "1" : "0";
    }
}
