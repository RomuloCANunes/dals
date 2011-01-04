package genetic.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Creates a Matrix structure based on ArrayLists. This matrix self ensures
 * it'll always have a rectangular shape (same number of cols, in every row,
 * at any time).
 * Every empty place is filled with NULL.
 * Any attempt to get, set, or remove, will automatically extend the matrix to
 * the given dimension, if it have not that size already.
 *
 * @param <E>
 * @author romulo
 */
public class ArrayListMatrix<E> implements Iterable {

    ArrayList<ArrayList> rows = new ArrayList<ArrayList>();

    /**
     * Adds a row, with the same number of collumns of every other row
     */
    public void addRow() {
        ArrayList<E> collumns = new ArrayList<E>();
        rows.add(collumns);
        for (int i = 0; i < getNumCols(); i++) {
            collumns.add(null);
        }
    }

    /**
     * Adds a collumn in every row
     */
    public void addCol() {
        if (getNumRows() == 0) {
            addRow();
        }
        for (ArrayList<E> row : rows) {
            row.add(null);
        }
    }

    /**
     * Returns the number of rows
     * @return
     */
    public int getNumRows() {
        return rows.size();
    }

    /**
     * Returns the number of cols
     * @return 
     */
    public int getNumCols() {
        if (getNumRows() > 0) {
            return rows.get(0).size();
        }
        return 0;
    }

    /**
     * If he number of collumns is lower than index, creates collumns until it
     * reaches index
     *
     * @param index
     */
    private void addColsUntilIdex(int index) {
        for (int i = getNumCols(); i <= index; i++) {
            //System.out.print("+col");
            addCol();
        }
    }

    /**
     * If the number of rows is lower than index, creates rows until it reaches
     * index
     *
     * @param index
     */
    private void addRowsUntilIdex(int index) {
        for (int i = getNumRows(); i <= index; i++) {
            //System.out.print("+row");
            addRow();
        }
    }

    /**
     * If number of rows is lower than row, creates rows until it reaches this
     * value. If number of collumns is lower than col, creates collumns until
     * it reaches this value.
     *
     * @param row
     * @param col
     */
    public void createValidPosition(int row, int col) {
        //System.out.print("createValidPosition");
        addRowsUntilIdex(row);
        addColsUntilIdex(col);
    }

    /**
     * Adds an <E>element to the matrix in place row:col
     *
     * @param row
     * @param col
     * @param element
     */
    public void set(int row, int col, E element) {
        createValidPosition(row, col);
        rows.get(row).set(col, element);
    }

    /**
     * Retrieves the <E>element stored in place row:col, or null if this place
     * is empty.
     *
     * @param row
     * @param col
     * @return
     */
    public E get(int row, int col) {
        createValidPosition(row, col);
        return (E) rows.get(row).get(col);
    }

    /**
     * Removes the <E>element stored in place row:col (replaces it with null).
     *
     * @param row
     * @param col
     */
    public void remove(int row, int col) {
        createValidPosition(row, col);
        rows.get(row).set(col, null);
    }

    /**
     * Verifies if the given position already exists
     * @param row
     * @param col
     * @return
     */
    public boolean isValidPosition(int row, int col) {
        return getNumRows() >= row && getNumCols() >= col;
    }

    /**
     * Double Wrappers
     */
    /**
     * Adds an <E>element to the matrix in place row:col
     *
     * @param row
     * @param col
     * @param element
     */
    public void set(double row, double col, E element) {
        set((int) row, (int) col, element);
    }

    /**
     * Retrieves the <E>element stored in place row:col, or null if this place
     * is empty.
     *
     * @param row
     * @param col
     * @return
     */
    public E get(double row, double col) {
        return get((int) row, (int) col);
    }

    /**
     * Removes the <E>element stored in place row:col (replaces it with null).
     * 
     * @param row
     * @param col
     */
    public void remove(double row, double col) {
        remove((int) row, (int) col);
    }

    /**
     * Returns a String representation of this instance
     * @return
     */
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < getNumRows(); i++) {
            output += "row " + i + ":";
            for (int j = 0; j < getNumCols(); j++) {
                output += " | " + get(i, j);
            }
            output += " |\n";
        }
        return output;
    }

    /**
     * Retrieves this instance iterator
     * @return
     */
    public Iterator iterator() {
        return new ArrayListMatrixIterator(this);
    }

    class ArrayListMatrixIterator implements Iterator {

        private ArrayListMatrix toIterateInstance;
        private int nextPositionRow = -1, nextPositionCol = -1;
        private int actualPositionRow = -1, actualPositionCol = -1;
        private boolean hasNext = false;

        public ArrayListMatrixIterator(ArrayListMatrix toIterateInstance) {
            this.toIterateInstance = toIterateInstance;
            hasNext = calculateNextPosition();
        }

        public boolean hasNext() {
            return hasNext;
        }

        public E next() {
            if( hasNext ) {
                E element = (E)toIterateInstance.get(nextPositionRow, nextPositionCol);
                if( element != null ) {
                    actualPositionRow = nextPositionRow;
                    actualPositionCol = nextPositionCol;
                    calculateNextPositionAdvanceCollumn();
                    hasNext = calculateNextPosition();
                    return element;
                }
            }
            throw new NoSuchElementException("No such element.");
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Calculates the next position in the matrix that contains a not NULL
         * value.
         * @return true if there is a next not null element
         */
        private boolean calculateNextPosition() {
            nextPositionRow = Math.max(nextPositionRow, 0);
            // row out of bounds, no next valid position
            if (nextPositionRow >= toIterateInstance.getNumRows()) {
                nextPositionRow = -1;
                nextPositionCol = -1;
                actualPositionRow = -1;
                actualPositionCol = -1;
                return false;
            }

            ArrayList<E> actualRow = (ArrayList<E>) toIterateInstance.rows.get(nextPositionRow);
            // null row, go to next row
            if (actualRow == null) {
                calculateNextPositionAdvanceRow();
                return calculateNextPosition();
            }

            nextPositionCol = Math.max(nextPositionCol, 0);
            // collumn out of bounds, go o next row
            if (nextPositionCol >= actualRow.size()) {
                calculateNextPositionAdvanceRow();
                return calculateNextPosition();
            }

            // searching in actualRow for some valid collumn
            while (nextPositionCol < actualRow.size()) {
                if (actualRow.get(nextPositionCol) != null) {
                    return true;
                }
                calculateNextPositionAdvanceCollumn();
            }
            // no valid collumns in this row, go to next row
            calculateNextPositionAdvanceRow();
            return calculateNextPosition();
        }

        private void calculateNextPositionAdvanceRow() {
            nextPositionRow++;
            nextPositionCol = 0;
        }

        private void calculateNextPositionAdvanceCollumn() {
            nextPositionCol++;
        }

        public int getNextRowIndex() {
            return nextPositionRow;
        }

        public int getNextColIndex() {
            return nextPositionCol;
        }
    }
}
