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
