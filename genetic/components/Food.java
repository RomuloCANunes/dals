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
