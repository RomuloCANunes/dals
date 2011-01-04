package genetic.strategies.selection;

/**
 *
 * @author romulo
 */
public class SelectionFactory {
    private static ISelection instance = null;

    /**
     * Retrieves a ISelection instance. Each ISelection class represents
     * a different strategy to an Individual choose it procreational partner from
     * a given group of Individuals.
     * @return
     */
    public static ISelection getInstance()
    {
        if( instance == null )
        {
            instance = new RouletteWheel();
        }
        return instance;
    }
}
