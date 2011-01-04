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
