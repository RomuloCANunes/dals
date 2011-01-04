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
