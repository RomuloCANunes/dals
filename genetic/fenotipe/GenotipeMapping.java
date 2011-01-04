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
package genetic.fenotipe;

import java.util.Hashtable;

/**
 * Creates, in a simple way, the mapping of the Genotipe, defining what slices
 * of the gens are available, their location and size
 * @author romulo
 */
public class GenotipeMapping {

    private Hashtable<String, Slice> mapping = new Hashtable<String, Slice>();
    private int actualIndex = 0;
    private int size = 0;

    /**
     * creates a new complete mapping definig its slices
     */
    public GenotipeMapping() {
        add("preferenciaAlimentoA", 3);
        add("preferenciaAlimentoB", 3);
        add("tipoAlimentoFornecido", 1);
        add("alcanceVisao", 10);
        add("alcanceOlfato", 10);
        add("distanciaMaximaLocomocao", 10);
        add("intervaloEntreTurnos", 10);
        add("consumoAdicionalEnergia", 5);
        add("tempoMaturacaoSexual", 5);
        add("longevidade", 10);
    }

    /**
     * Adds a DNA slice in the mapping
     * @param name
     * @param sliceSize
     */
    private void add(String name, int sliceSize) {
        mapping.put(name, new Slice(actualIndex, sliceSize));
        actualIndex += sliceSize;
        size += sliceSize;
    }

    /**
     * Retrieves tha named slice
     * @param name
     * @return
     */
    public Slice getSlice(String name) {
        return mapping.get(name);
    }

    /**
     * Retrieves the default DNA size for the simulations
     * @return
     */
    public int getSize() {
        return size;
    }
}
