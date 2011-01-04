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
package genetic;

/**
 * This class is responsible for timed activities in an Environment. From time
 * to time, it activate the refill of food.
 * @author romulo
 */
public class EnvironmentMaintainer implements Runnable {

    private Environment environment;
    private int delayTime;
    private int count = 0;

    /**
     * Default constructor
     * @param environment the environment this maintainer will take care of
     */
    public EnvironmentMaintainer(Environment environment) {
        this.environment = environment;
    }

    /**
     * Loops forever: Process the maintenance and sleeps
     */
    public void run() {
        while (true) {
            if (environment.isPlaying()) {
                if (timeToFeed()) {
                    createFood();
                }
                count++;
            }
            delayTime = turnPauseMilisecs();
            //System.out.print("maintainer turn: " + delayTime);
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    private void createFood() {
        //System.out.println("adicionando comida " + environment.getGeneratedFoodAmount());
        environment.createFood(environment.getGeneratedFoodAmount());
    }

    private float turnsIntervalToFeed() {
        return 10;//environment.getMultiplierGeneratedFoodPace();
    }

    private boolean timeToFeed() {
        return (count % turnsIntervalToFeed()) == 0;
    }

    private int turnPauseMilisecs() {
        return (int) (100 * environment.getTimeSpeed());
    }

    /**
     * Calculates the value of a slider where value increses from right to left
     * @param maxVal
     * @param minVal
     * @param value
     * @return
     */
    protected float calculateSliderValueRightToLeft(float maxVal, float minVal, float value) {
        return maxVal - minVal - value - minVal;
    }
    
}
