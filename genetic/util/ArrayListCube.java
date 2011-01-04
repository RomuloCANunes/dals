package genetic.util;

import genetic.components.Represetative2dObject;
import genetic.util.ArrayListMatrix.ArrayListMatrixIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayListCube creates a tridimensional structure (a cube) using ArrayLists.
 * It relays upon ArrayListMatrix, so its base (rows and collumns) is
 * always in a rectangular shape (same number of cols, in every row,
 * at any time).
 * The base is flexible, so any attempt to get, set, or remove in it, will
 * automatically extend the base matrix to the given dimension, if it have not
 * that size already.
 * The deepness however, is not ensured to be the same at every row:col
 * position of the cube.  * So, for any row:col you could have a different
 * deepness.
 *
 * @param <E> The class type of this cube elements
 * @author romulo
 */
public class ArrayListCube<E> implements Iterable {

    private ArrayListMatrix<ArrayList> matrix = new ArrayListMatrix<ArrayList>();
    private int count = 0;

    /**
     * Adds a row, with the same number of collumns of every other row
     */
    public void addRow() {
        matrix.addRow();
    }

    /**
     * Adds a collumn in every row
     */
    public void addCol() {
        matrix.addCol();
    }

    /**
     * Returns the number of rows
     * @return
     */
    public int getNumRows() {
        return matrix.getNumRows();
    }

    /**
     * Returns the number of collumns
     * @return
     */
    public int getNumCols() {
        return matrix.getNumCols();
    }

    /**
     * Returns the deepness (number of <E>itens) stored in a row:col position
     * @param row
     * @param col
     * @return
     */
    public int getDeepness(int row, int col) {
        createPositionContainer(row, col);
        return matrix.get(row, col).size();
    }

    /**
     * Retrieves the count
     * @return
     */
    public int getCount() {
        return count;
    }
    
    /**
     * If the number of rows is lower than index, creates rows until it reaches
     * index
     *
     * @param index
     */
    private void addDeepnessUntilIdex(int row, int col, int index) {
        for (int i = getDeepness(row, col); i <= index; i++) {
            //System.out.print("+dep");
            addDeepness(row, col);
        }
    }

    private void addDeepness(int row, int col) {
        getListInPosition(row, col).add(null);
    }

    /**
     * Appends the <E>element to the end of the row:col list
     * @param row
     * @param col
     * @param element
     */
    public void add(int row, int col, E element) {
        createPositionContainer(row, col);
        matrix.get(row, col).add(element);
        count++;
    }

    private void createPositionContainer(int row, int col) {
        if (matrix.getNumRows() <= row || matrix.getNumCols() <= col) {
            //System.out.println("create position container para " + row + " " + col);
            matrix.set(row, col, new ArrayList<E>());
        }
        if (matrix.get(row, col) == null) {
            matrix.set(row, col, new ArrayList<E>());
        }
    }

    private void createPositionContainer(int row, int col, int dep) {
        createPositionContainer(row, col);
        if (getDeepness(row, col) <= dep) {
            //System.out.println("deepness insuficiente: " + dep);
            addDeepnessUntilIdex(row, col, dep);
        }
    }

    /**
     * Replaces the element at the specified row:col position in this list with
     * the specified <E>element.
     *
     * @param row
     * @param col
     * @param dep
     * @param element
     */
    public void set(int row, int col, int dep, E element) {
        if (!isEmptyPosition(row, col, dep)) {
            count++;
        }
        createPositionContainer(row, col, dep);
        matrix.get(row, col).set(dep, element);
    }

    /**
     * Retrieves a cube element
     * @param row
     * @param col
     * @param dep
     * @return
     */
    public E get(int row, int col, int dep) {
        createPositionContainer(row, col, dep);
        return (E) matrix.get(row, col).get(dep);
    }

    /**
     * Removes a cube element
     * @param row
     * @param col
     * @param dep
     */
    public void remove(int row, int col, int dep) {
        if (!isEmptyPosition(row, col, dep)) {
            count--;
        }
        createPositionContainer(row, col, dep);
        matrix.get(row, col).remove(dep);
    }

    /**
     * Removes the given element from the coordinate row:col if it exists there
     * @param row
     * @param col
     * @param element
     */
    public void remove(int row, int col, E element) {
        createPositionContainer(row, col);
        if (matrix.get(row, col).remove(element)) {
            count--;
        }
    }

    /**
     * Retrieves a list of elements in coordinate row:col
     * @param row
     * @param col
     * @return
     */
    public ArrayList<E> getListInPosition(int row, int col) {
        createPositionContainer(row, col);
        return matrix.get(row, col);
    }

    /**
     * Retrieves the first element in coordinate row:col
     * @param row
     * @param col
     * @return
     */
    public E getFirstInPosition(int row, int col) {
        createPositionContainer(row, col);
        ArrayList<E> positionList = matrix.get(row, col);
        for (E element : positionList) {
            if (element != null) {
                return element;
            }
        }
        return null;
    }

    /**
     * Retrieves the las element in coordinate row:col
     * @param row
     * @param col
     * @return
     */
    public E getLastInPosition(int row, int col) {
        createPositionContainer(row, col);
        ArrayList<E> location = matrix.get(row, col);
        return location.get(location.size() - 1);
    }

    /**
     * Verifies if the coordinate row:col is a valid one
     * @param row
     * @param col
     * @return
     */
    public boolean isValidPosition(int row, int col) {
        return matrix.isValidPosition(row, col);
    }

    /**
     * Verifies if a specific position in the cube is valid
     * @param row
     * @param col
     * @param dep
     * @return
     */
    public boolean isValidPosition(int row, int col, int dep) {
        if (matrix.isValidPosition(row, col)) {
            return getDeepness(row, col) >= dep;
        }
        return false;
    }

    /**
     * Returns if the coordinate row:col is empty
     * @param row
     * @param col
     * @return
     */
    public boolean isEmptyPosition(int row, int col) {
        if (isValidPosition(row, col)) {
            return getDeepness(row, col) == 0;
        }
        return true;
    }

    /**
     * Returns if coordinarte row:col:dep is empty
     * @param row
     * @param col
     * @param dep
     * @return
     */
    public boolean isEmptyPosition(int row, int col, int dep) {
        if (isValidPosition(row, col, dep)) {
            if (get(row, col, dep) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * double wrappers, to easily work with Point coordinates
     *
     */
    /**
     * Adds an element in coordinate row:col
     * @param row
     * @param col
     * @param element
     */
    public void add(double row, double col, E element) {
        add((int) row, (int) col, element);
    }

    /**
     * Gets the element in the coordinate row:col:dep
     * @param row
     * @param col
     * @param dep
     * @param element
     */
    public void set(double row, double col, double dep, E element) {
        set((int) row, (int) col, (int) dep, element);
    }

    /**
     * Retrieves the element in coordinate row:col:dep
     * @param row
     * @param col
     * @param dep
     * @return
     */
    public E get(double row, double col, double dep) {
        return get((int) row, (int) col, (int) dep);
    }

    /**
     * Removes the element in coordinate row:col:dep
     * @param row
     * @param col
     * @param element
     */
    public void remove(double row, double col, E element) {
        remove((int) row, (int) col, element);
    }

    /**
     * Retrieves the list with all elements in coordinate row:col
     * @param row
     * @param col
     * @return
     */
    public ArrayList<E> getListInPosition(double row, double col) {
        return getListInPosition((int) row, (int) col);
    }

    /**
     * Prints to System.out this object ans its elements
     */
    public void dump() {
        for (Object obj : this) {
            System.out.println(obj);
        }
    }

    /**
     * Retrieves this instance Iterator
     * @return
     */
    public Iterator iterator() {
        return new ArrayListCubeIterator(this);
    }

    private Iterator matrixIterator() {
        return matrix.iterator();
    }

    class ArrayListCubeIterator implements Iterator {

        private ArrayListCube toIterateInstance;
        private int nextPositionDepth = -1;
        private boolean hasNext = false;
        private ArrayListMatrixIterator matrixIterator;
        ArrayList<E> currentMatrixPosition;

        public ArrayListCubeIterator(ArrayListCube toIterateInstance) {
            this.toIterateInstance = toIterateInstance;
            matrixIterator = (ArrayListMatrixIterator) toIterateInstance.matrixIterator();
            hasNext = calculateNextPosition();
        }

        public boolean hasNext() {
            return hasNext;
        }

        public E next() {
            if (hasNext) {
                E element = (E) toIterateInstance.get(matrixIterator.getNextRowIndex(), matrixIterator.getNextColIndex(), nextPositionDepth);
                if (element != null) {
                    nextPositionDepth++;
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
         * Calculates the next position in the cube that contains a not NULL
         * value.
         * @return true if there is a next not null element
         */
        private boolean calculateNextPosition() {
            // reached the end of the matrix
            if (matrixIterator.getNextRowIndex() == -1 && matrixIterator.getNextColIndex() == -1) {
                nextPositionDepth = -1;
                return false;
            }

            if (!toIterateInstance.isValidPosition(matrixIterator.getNextRowIndex(), matrixIterator.getNextColIndex())) {
                matrixIterator.next();
                nextPositionDepth = 0;
                return calculateNextPosition();
            }

            nextPositionDepth = Math.max(nextPositionDepth, 0);
            // depth out of bounds
            if (nextPositionDepth >= toIterateInstance.getDeepness(matrixIterator.getNextRowIndex(), matrixIterator.getNextColIndex())) {
                if (!matrixIterator.hasNext()) {
                    nextPositionDepth = -1;
                    return false;
                }
                matrixIterator.next();
                nextPositionDepth = 0;
                return calculateNextPosition();
            }

            E element = (E) toIterateInstance.get(matrixIterator.getNextRowIndex(), matrixIterator.getNextColIndex(), nextPositionDepth);
            // element found
            if (element != null) {
                return true;
            } else {
                //none element found, go to next position
                nextPositionDepth++;
                return calculateNextPosition();
            }
        }
    }
}
