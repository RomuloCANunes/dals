package genetic.fenotipe;

/**
 * A helper class to control slices
 * @author romulo
 */
public class Slice {
    private int initial, size, end;

    /**
     * Creates a new instance
     * @param initial
     * @param size
     */
    public Slice(int initial, int size)
    {
        this.initial = initial;
        this.size = size;
        this.end = initial + size;
    }

    /**
     * Returns the end position of the slice
     * @return
     */
    public int getEnd() {
        return end;
    }

    /**
     * Returns the inicial slice position
     * @return
     */
    public int getInitial() {
        return initial;
    }

    /**
     * Returns the slice size
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * This Sclice's String representation
     * @return
     */
    @Override
    public String toString() {
        return "Slice {" + initial + "," + end + "}";
    }
}
