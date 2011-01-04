package genetic.strategies.recombination;

import genetic.genotipe.DNA;
import genetic.genotipe.Gen;
import java.util.ArrayList;

/**
 *
 * @author romulo
 */
public class TwoPointsFolding implements IRecombination {

    /**
     *
     * @param father
     * @param mother
     * @return
     */
    public DNA mixGens(DNA father, DNA mother) {
        //clones the DNAs
        DNA fatherDNA = new DNA(father);
        DNA motherDNA = new DNA(mother);

        int sort1 = (int) Math.round( Math.random() * fatherDNA.getSize() );
        int sort2 = (int) Math.round( Math.random() * fatherDNA.getSize() );

        int position1 = Math.min(sort1, sort2);
        int position2 = Math.max(sort1, sort2);

        ArrayList<Gen> newGens = new ArrayList<Gen>();

        for( int i = 0; i < fatherDNA.getSize(); i++ )
        {
            if( i < position1 || i > position2 )
            {
                newGens.add(fatherDNA.getGenAt(i));
            }
            else
            {
                newGens.add(motherDNA.getGenAt(i));
            }
        }

        return new DNA(newGens);
    }
}
