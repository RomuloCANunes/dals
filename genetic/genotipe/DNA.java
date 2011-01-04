package genetic.genotipe;

import genetic.fenotipe.GenotipeMapping;
import genetic.strategies.recombination.RecombinationFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * DNA class handles a list of gens that is the genetic base of an individual.
 * This class is not just a Gen Collection, but also makes intra-cell actions
 * over the gens, like in real life. Mutation, duplication, crossover, etc.
 *
 * @author romulo
 */
public class DNA
{

    private ArrayList<Gen> gens = new ArrayList<Gen>();
    private int size = 10;

    /**
     * Creates a new DNA with a completelly random Gen sequence
     */
    public DNA()
    {
        GenotipeMapping mapping = new GenotipeMapping();
        size = mapping.getSize();
        gens = generateGensFromRandom();
    }

    /**
     * Creates a new DNA with a completelly random Gen sequence
     * @param size
     */
    public DNA(int size)
    {
        this.size = size;
        gens = generateGensFromRandom();
    }

    /**
     * Creates a new DNA based on two other DNAs
     * @param father
     * @param mother
     */
    public DNA(DNA father, DNA mother)
    {
        setGens( father.mixGens( mother ) );
        size = gens.size();
    }

    /**
     * Clones a DNA
     * @param dna
     */
    public DNA(DNA dna)
    {
        setGens( dna.getGens() );
        size = gens.size();
    }

    /**
     * Constructor with a defined gen sequence
     * @param genSequence
     */
    public DNA(ArrayList<Gen> genSequence)
    {
        setGens( genSequence );
        size = gens.size();
    }

    /**
     * Parses a string into a DNA
     * @param genSequence
     * @return
     */
    public static DNA parseString(String genSequence)
    {
        ArrayList<Gen> newGens = new ArrayList<Gen>();
        for( int i = 0; i < genSequence.length(); i ++ )
        {
            newGens.add( Gen.parseChar( genSequence.charAt( i ) ) );
        }
        return new DNA( newGens );
    }

    private void setGens(ArrayList<Gen> genSequence)
    {
        gens = genSequence;
    }

    private void setGens(DNA dna)
    {
        gens = dna.getGens();
    }

    /**
     * Retrieves the gen sequence of this DNA
     * @return
     */
    public ArrayList<Gen> getGens()
    {
        return gens;
    }

    /**
     * Retrieves the size of this DNA
     * @return
     */
    public int getSize()
    {
        return size;
    }

    /**
     * genrates a random gen sequence
     * @return
     */
    private ArrayList<Gen> generateGensFromRandom()
    {
        ArrayList<Gen> newGens = new ArrayList<Gen>();
        for( int i = 0; i < size; i ++ )
        {
            newGens.add( new Gen( Math.round( Math.random() ) == 0 ) );
        }
        return newGens;
    }

    /**
     * Generate a gen sequence based on the gen sequence of this DNA and the gen 
     * sequence of another DNA.
     *
     * @param partnerDNA
     * @return
     */
    public DNA mixGens(DNA partnerDNA)
    {
        return RecombinationFactory.getInstance().mixGens(this, partnerDNA);
    }

    /**
     * returns the state of an gen in the gen sequence at an specific position
     * @param i
     * @return
     */
    public Gen getGenAt(int i)
    {
        return gens.get( i );
    }

    private void setGenAt(int i, boolean gen)
    {
        setGenAt( i, new Gen( gen ) );
    }

    private void setGenAt(int i, Gen gen)
    {
        gens.remove( i );
        gens.add( i, gen );
    }

    private void invertGenAt(int i)
    {
        setGenAt( i, getGenAt( i ).equals( false ) );
    }

    /**
     * Resturns a String representation of this instance
     * @return
     */
    @Override
    public String toString()
    {
        int active = 0;
        String s = "";
        for( Gen g : gens )
        {
            s += g;
            if(g.equals(true)) {
               active++;
            }
        }
        return s + " (" + active + ")";
    }

    /**
     * apply mutation over the gens
     * @param mutationRatio probability of mutation occuring
     * @param mutationEffectPercent percentage of gens affected if occurring
     */
    public void mutate(double mutationRatio, double mutationEffectPercent)
    {
        // ocasiao de mutacao
        if( shallMutate( mutationRatio ) )
        {
            applyMutation( mutationEffectPercent );
        }
    }

    /**
     * 
     * @param mutationEffectPercent
     */
    private void applyMutation(double mutationEffectPercent)
    {
        int qtdGenToMutate = (int) Math.round( size * mutationEffectPercent );
        for( int i = 0; i < qtdGenToMutate; i ++ )
        {
            int position = (int) Math.floor( Math.random() * size );
            invertGenAt( position );
        }
    }

    /**
     * 
     * @param mutationRatio
     * @return
     */
    private boolean shallMutate(double mutationRatio)
    {
        return ( Math.random() < mutationRatio );
    }

    /**
     * returns the similarity of an DNA to another
     * @param dnaSample
     * @return
     */
    public double getSimilarity(DNA dnaSample)
    {
        int greatest = 0;
        int smallest = 0;
        smallest = Math.min( dnaSample.getSize(), getSize() );
        greatest = Math.max( dnaSample.getSize(), getSize() );

        double valMatch = greatest / smallest;
        double valFinal = 0;

        for( int i = 0; i < getSize() && i < smallest; i ++ )
        {
            if( getGenAt( i ).equals( dnaSample.getGenAt( i ) ) )
            {
                valFinal += valMatch;
            }
        }
        return valFinal / greatest;
    }

    /**
     * Returns a sub-list of gens contained in this DNA starting in fromIndex
     * (inclusive) and ending in toIndex (exclusive).
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public List<Gen> subList(int fromIndex, int toIndex)
    {
         return gens.subList(fromIndex, toIndex);
    }

    /**
     * Generates a hash code from this instance
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.gens != null ? this.gens.hashCode() : 0);
        return hash;
    }
}
