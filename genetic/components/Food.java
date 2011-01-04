package genetic.components;

/**
 * Food is a Representative2dObject that can be eaten by an individual
 * @author romulo
 */
public class Food extends Represetative2dObject {

    private int energeticValue = 10;
    private int type = Food.TYPE_A;
    /**
     * Available types of food
     */
    public static final int TYPE_A = 0, TYPE_B = 1, TYPE_EATEN = -1;

    /**
     * Creates a new Food instance with a defined type and energetic value
     * @param type
     * @param energeticValue
     */
    public Food(int type, int energeticValue) {
        this.energeticValue = energeticValue;
        this.type = type;
    }

    /**
     * Retrieves the energetic value of this food
     * @return
     */
    public int getEnergeticValue() {
        return energeticValue;
    }

    /**
     * Generates the image name of the food based on its type
     * @return
     */
    @Override
    public String getImageName() {
        return "food_" + type + ".png";
    }

    /**
     * Returns the food type
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Changes the amount of energy of this foods
     * @param energeticValue
     */
    public void setEnergeticValue(int energeticValue) {
        this.energeticValue = energeticValue;
    }

    /**
     * Process occurred when a food is eaten.
     */
    public void eaten() {
        environment.remove(this);
        turnToInvalidFood();
    }

    /**
     * Transforms this food in crap.
     */
    public void turnToInvalidFood() {
        this.type = TYPE_EATEN;
        energeticValue = 0;
    }
}
