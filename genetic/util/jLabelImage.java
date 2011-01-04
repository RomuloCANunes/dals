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
