/*
 * This file is part of DALS - Distributed Artificial Life Simulator.
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
package genetic.strategies.selection;

import genetic.components.Individual;
import java.util.ArrayList;

/**
 *
 * @author romulo
 */
public interface ISelection {

    /**
     * Selects an individual from a list of availablePartners
     * @param selector
     * @param availablePartners
     * @return the choosen individual
     */
    public Individual select(Individual selector, ArrayList<Individual> availablePartners);
}
