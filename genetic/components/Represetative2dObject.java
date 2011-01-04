package genetic.components;

import genetic.Environment;
import java.awt.Point;

/**
 * A representative object is any item (individual or food) that can be stored,
 * controlled, and displayed in an Environment
 * @author romulo
 */
public class Represetative2dObject extends Point {
    /**
     * the environment this object belongs to
     */
    protected Environment environment = null;

    /**
     * Default constructor - this constructor do not bind the created object to
     * an Envoronment so, in order to interact with environment is mandatory to
     * use the setEnvironment method
     * @see setEnvironment
     */
    public Represetative2dObject() {
    }

    /**
     * Binds the representative2dObject with the Environment, necessary to let
     * the individual interact with it
     *
     * @todo change this thight-coupled interaction to an observable, loosely
     * coupled interaction
     *
     * @param environment
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Retrieves this object image name
     * @return
     */
    public String getImageName() {
        return "unknow.png";
    }
}
