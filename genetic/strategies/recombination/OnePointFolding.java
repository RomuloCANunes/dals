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
