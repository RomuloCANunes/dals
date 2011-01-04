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
