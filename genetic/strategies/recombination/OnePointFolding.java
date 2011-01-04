package genetic.strategies.recombination;

import genetic.genotipe.DNA;
import genetic.genotipe.Gen;
import java.util.ArrayList;

/**
 *
 * @author romulo
 */
public class OnePointFolding implements IRecombination {

    /**
     *
     * @param fatherDNA
     * @param motherDNA
     * @return
     */
    public DNA mixGens(DNA fatherDNA, DNA motherDNA) {
        //sorteia posicao de corte
        int position = (int) Math.round( Math.random() * fatherDNA.getSize() );

        ArrayList<Gen> newGens = new ArrayList<Gen>();
        for( int i = 0; i < fatherDNA.getSize(); i++ )
        {
            if( i < position )
            {
                newGens.add(fatherDNA.getGenAt(i));
            }
            else
            {
                newGens.add(motherDNA.getGenAt(i));
            }
        }

        DNA dna = new DNA(newGens);

        return dna;
    }
}
