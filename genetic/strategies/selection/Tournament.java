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
import genetic.util.Configuration;
import java.util.ArrayList;

/**
 * This class implements the Tournament algorithm to select a partner from a
 * given group of individuals. Only the best individual of all will be choosen.
 *
 * @author romulo
 */
public class Tournament implements ISelection {

    public Individual select(Individual selector, ArrayList<Individual> availablePartners) {
        Individual bestOne = null;

        for (Individual ind : availablePartners) {
            if (!ind.equals(selector)) {
                if (selector.getSimilarity(ind) >= Configuration.SIMILARITY_MINIMUM_TO_REPRODUCE) {
                    if (bestOne == null) {
                        bestOne = ind;
                    } else {
                        if( Individual.calculateSelectionScore(ind) > Individual.calculateSelectionScore(bestOne) ) {
                            bestOne = ind;
                        }
                    }
                }
            }
        }
        return bestOne;
    }
}
