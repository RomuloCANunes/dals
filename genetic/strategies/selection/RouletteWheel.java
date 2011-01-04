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
package genetic.strategies.selection;

import genetic.util.Configuration;
import genetic.components.Individual;
import java.util.ArrayList;

/**
 * This class implements the roulette wheel algorithm to select a partner from a
 * given group of individuals. Any individual can be choosed, but heir chance to
 * be selected are as better as it's selectionScore.
 * @author romulo
 */
public class RouletteWheel implements ISelection {

    public Individual select(Individual selector, ArrayList<Individual> availablePartners) {
        int total = 0;
        ArrayList<Individual> compatiblePartners = new ArrayList<Individual>();

        for (Individual ind : availablePartners) {
            if (!ind.equals(selector)) {
                if( selector.getSimilarity(ind) >= Configuration.SIMILARITY_MINIMUM_TO_REPRODUCE) {
                    total += Individual.calculateSelectionScore(ind);
                    compatiblePartners.add(ind);
                }
            }
        }
        int choosen = (int) Math.round(Math.random() * total);

        int position = 0;
        for (Individual ind : compatiblePartners) {
            position += Individual.calculateSelectionScore(ind);
            if (position >= choosen) {
                ind.selectedAsPartner();
                return ind;
            }
        }

        return null;
    }

}
