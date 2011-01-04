/*
 * This file is part of DALS - Distributed Artificial Life Simulator.
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JLabel;

/**
 * Class responsable for displaying an image in the map.
 *
 * @author romulo
 */
public class jLabelImage extends JLabel {
    private Image img = null;
    private int horizontalPosition, verticalPosition;

    /**
     * Defines the image to be used
     * @param img
     */
    public void setImage(Image img) {
        this.img = img;
    }

    /**
     * Removes the actual defined image
    */
    public void removeImage() {
        this.img = null;
    }

    /**
     * concatenates the imageName with de default image directory
     * @param imageName
     * @return
     */
    public static String getFullPathName(String imageName) {
        return Configuration.DEFAULT_IMAGE_DIRECTORY + imageName;
    }

    /**
     * Paints this JLabelImage
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if( img != null ) {
            Graphics2D g2 = (Graphics2D)g;
            //System.out.println("imagem: " + img);
            g2.drawImage(img, 1, 1, null);
        }
    }
}
