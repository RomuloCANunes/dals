package genetic.strategies.recombination;

import genetic.genotipe.DNA;

/**
 *
 * @author romulo
 */
public interface IRecombination {

    /**
     * 
     * @param fatherDNA
     * @param motherDNA
     * @return
     */
    public DNA mixGens(DNA fatherDNA, DNA motherDNA);
}
