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
package world;

/**
 *
 * @author romulo
 */
public class MonitoredEnvironment {
    private String username;
    private boolean playing = false;
    private float timeSpeed = 11;
    private int individualCount = 0, foodCount = 0;

    /**
     * Default constructor
     * @param username
     */
    public MonitoredEnvironment(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the food count
     * @return
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * Defines the food count
     * @param foodCount
     */
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * Retrieves the individual count
     * @return
     */
    public int getIndividualCount() {
        return individualCount;
    }

    /**
     * defines the individual count
     * @param individualCount
     */
    public void setIndividualCount(int individualCount) {
        this.individualCount = individualCount;
    }

    /**
     * retrieves the playing status
     * @return
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * defines the playing status
     * @param playing
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * Retrieves the time speed
     * @return
     */
    public float getTimeSpeed() {
        return timeSpeed;
    }

    /**
     * defines the time speed
     * @param timeSpeed
     */
    public void setTimeSpeed(float timeSpeed) {
        this.timeSpeed = timeSpeed;
    }
}
