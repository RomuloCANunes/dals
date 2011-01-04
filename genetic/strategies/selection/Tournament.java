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
