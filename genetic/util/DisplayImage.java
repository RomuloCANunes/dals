package genetic.util;

import java.awt.Image;

/**
 * Class responsable for displaying an image in the map.
 *
 * @author romulo
 */
public class DisplayImage extends jLabelImage {
    private Image img = null;
    private int horizontalPosition, verticalPosition;

    /**
     * Creates a new instance and stores its location
     * @param horizontalPosition
     * @param verticalPosition
     */
    public DisplayImage(int horizontalPosition, int verticalPosition ) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
    }

    /**
     * Retrieve the current horizontal position of this DisplayImage, he index in a matrix
     * @return
     */
    public int getHorizontalPosition(){
        return horizontalPosition;
    }

    /**
     * Retrieve the current vertical position of this DisplayImage, the index in a matrix
     * @return
     */
    public int getVerticalPosition(){
        return verticalPosition;
    }
}
