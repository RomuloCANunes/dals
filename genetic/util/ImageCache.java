package genetic.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;

/**
 * This class loads and stores a cache of images for reuse
 *
 * @author romulo
 */
public class ImageCache {
    private Hashtable<String, Image> cache = new Hashtable<String, Image>();
    private static ImageCache instance = new ImageCache();

    private ImageCache() {
    }

    /**
     * Returns the singleton instance of the ImageCache class
     * @return
     */
    public static ImageCache getInstance() {
        return instance;
    }

    /**
     * Adds an Image to the cache
     * @param keyName
     * @param img
     */
    public void put(String keyName, Image img) {
        cache.put(keyName, img);
    }

    /**
     * Loads the image from filename and adds it into cache
     * @param name
     * @param filename
     * @throws IOException
     */
    public void put(String name, String filename) throws IOException {
        Image img = ImageIO.read(new File(filename));
        put(name, img);
    }

    /**
     * Loads the image from filename and adds it into cache using filename as key
     * @param filename
     * @throws IOException
     */
    public void put(String filename) throws IOException {
        put(filename,filename);
    }

    /**
     * Retrieves an Image from cache
     * @param keyName
     * @return
     */
    public Image get(String keyName) {
        return cache.get(keyName);
    }

    /**
     * Searches cache for a resized Image with keyName, if not found, retrieves
     * the original size, resize it, stores in cache, and returns instance
     *
     * @param keyName
     * @param width
     * @param height
     * @return
     */
    public Image getResized(String keyName, int width, int height) {
        String scaledName = keyName + "_" + width + "_" + height;
        if(cache.containsKey(scaledName)) {
            return cache.get(scaledName);
        }

        if(cache.containsKey(keyName)) {
            Image img = cache.get(keyName);
            Image scaledImage = resizeImage(img, width, height);
            put(scaledName, scaledImage);

            return scaledImage;
        }
        try {
            put(keyName);
            return getResized(keyName, width, height);
        } catch (IOException ex) {
        }
        return null;
    }

    /**
     * Resizes the image to fit width and height
     * @param img The image to be resized
     * @param width target width
     * @param height arget height
     * @return resized Image
     */
    private Image resizeImage(Image img, int width, int height) {
        BufferedImage scaledImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
        return scaledImg;
    }
}
