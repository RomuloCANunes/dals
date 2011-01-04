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
package genetic.fenotipe;

import genetic.components.Food;
import genetic.genotipe.DNA;
import genetic.genotipe.Gen;
import java.util.Hashtable;
import java.util.List;

/**
 * This class makes the extraction of fenotipe values from a genotipe (DNA)
 *
 * @todo translate fenotipe names. Create constants of strings for them
 * @author romulo
 */
public class GenotipeToFenotipe {

    private DNA genotipe;
    private Hashtable<String, Integer> table;
    private GenotipeMapping mapping;
    /**
     * Qualitative value
     */
    public static int QUALITATIVE_HIGH = 2,
            QUALITATIVE_LOW = 0,
            QUALITATIVE_MEDIUM = 1;

    /**
     * Creates a new GenotipeToFenotipe instance
     * @param genotipe
     */
    public GenotipeToFenotipe(DNA genotipe) {
        this.genotipe = genotipe;
        table = new Hashtable<String, Integer>();
        mapping = new GenotipeMapping();
    }

    /**
     * Gets the number of active gens in a given characteristc. Stores this
     * count in a cache hashtable
     *
     * @param name
     * @param valueEachGen
     * @return
     */
    public int getFenotipe(String name, int valueEachGen ) {
        if (table.get(name) == null) {
            Slice slice = mapping.getSlice(name);
            List<Gen> gens = genotipe.subList(slice.getInitial(), slice.getEnd());
            int value = activeGenCount(gens) * valueEachGen;
            table.put(name, value);
        }
        return table.get(name);
    }

    /**
     * Returns the fenotipe size
     * @param name
     * @return size of the fenotipe, or -1 if fenotipe not found
     */
    private int getFenotipeSize(String name) {
        if (table.get(name) != null) {
            Slice slice = mapping.getSlice(name);
            return slice.getSize();
        }
        return -1;
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getPreferenceFoodA() {
        return getFenotipe("preferenciaAlimentoA", 3);
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getPreferenceFoodB() {
        return getFenotipe("preferenciaAlimentoB", 1);
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getFoodType() {
        return Food.TYPE_B;
        //return getFenotipe("tipoAlimentoFornecido", 1);
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getSightRange() {
        return getFenotipe("alcanceVisao", 1);
    }

    /**
     * Returns sight range qualitatively
     * @return qualitative sight range
     */
    public int getQualitativeSight() {
        return getQualitativeFenotipe("alcanceVisao");
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getOlfactoryRange() {
        return getFenotipe("alcanceOlfato", 1);
    }

    /**
     * Returns the olfactory range qualitatively
     * @return qualitative olfactory range
     */
    public int getQualitativeOlfactory() {
        return getQualitativeFenotipe("alcanceOlfato");
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getMaximumLocomotionRange() {
        return getFenotipe("distanciaMaximaLocomocao", 1);
    }

    /**
     * Returns the maximumLocomotion range qualitatively
     * @return qualitative maximunLocomotion range
     */
    public int getQualitativeLocomotion() {
        return getQualitativeFenotipe("distanciaMaximaLocomocao");
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getMetabolicPauseTime() {
        return getFenotipe("intervaloEntreTurnos", 1);
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getBasicEnergyConsume() {
        return getFenotipe("consumoAdicionalEnergia", 1);
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getSexualMaturityAge() {
        return getFenotipe("tempoMaturacaoSexual", 3);
    }

    /**
     * wrapprers the query to the genotipe
     * @return active gen count of respective genotipe
     */
    public int getLongevity() {
        //System.out.println("longevidade: " + getFenotipe("longevidade", 5));
        return getFenotipe("longevidade", 5);
    }

    /**
     * Count the number of active gens (true) int given gen List
     * @param gens
     * @return active gen count
     */
    private int activeGenCount(List<Gen> gens) {
        int active = 0;
        for (Gen g : gens) {
            if (g.getValue()) {
                active++;
            }
        }
        return active;
    }

    /**
     * Returns a fenotipe in a qualitative way, values can be:
     * GenotipeToFenotipe.QUALITATIVE_LOW,
     * GenotipeToFenotipe.QUALITATIVE_MEDIU or
     * GenotipeToFenotipe.QUALITATIVE_HIGH
     * 
     * @param name
     * @return
     */
    private int getQualitativeFenotipe(String name) {
        double actualValue = getFenotipe(name, 1);
        double maximumValue = getFenotipeSize(name);
        double relation = actualValue / maximumValue;

        //System.out.println("qualitative fenotipe " + name + ": val[" + actualValue + "] / max[" + maximumValue + "] = " + relation);
        if( relation < 0.33 ) {
            return QUALITATIVE_LOW;
        } else if ( relation < 0.67 ) {
            return QUALITATIVE_MEDIUM;
        } else {
            return QUALITATIVE_HIGH;
        }
    }
}
