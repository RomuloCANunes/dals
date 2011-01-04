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
