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
