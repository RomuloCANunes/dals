package genetic.strategies.recombination;

/**
 *
 * @author romulo
 */
public class RecombinationFactory {
    private static IRecombination instance = null;

    /**
     * Retrieves a IRecombination instance. Each IRecombination class represents
     * a different strategy of genetic recombination (crossover).
     * @return
     */
    public static IRecombination getInstance()
    {
        if( instance == null )
        {
            //instance = new OnePointFolding();
            instance = new TwoPointsFolding();
        }
        return instance;
    }
}
