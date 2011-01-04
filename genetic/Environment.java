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

import genetic.components.Food;
import genetic.components.Individual;
import genetic.components.Represetative2dObject;
import genetic.message.*;
import genetic.net.EnvironmentProtocol;
import genetic.util.ArrayListCube;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import genetic.net.EnvironmentServer;
import genetic.net.EnvironmentXMPPConnectionHandler;
import genetic.net.XMPPConnectionHandler;
import genetic.util.Configuration;
import genetic.util.SynchronizedCounter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;
import org.jivesoftware.smack.XMPPException;

/**
 * Environment is a place where the individuals lives on, it have some
 * environmental parameters that could affect the individuals living in, it
 * places food for individuals, and it respond to individuals sensors.
 * The environment is also responsable for tracking and controlling the
 * geographical position of individuals and foods
 *
 * @todo problem with individualCount, probably it will be necessary to use 
 * syncronize the counting act outside of Environment, so every Individual Thread
 * will activate count
 *
 * @author romulo
 */
public class Environment extends Observable {

    private float multiplierMutationChance = 1;
    private float multiplierMutationEffect = 1;
    private float multiplierGeneratedFoodPace = 5;
    private float multiplierLostEnergy = 1;
    private float multiplierFoodTypeAIncidence = 0.5F;
    private float multiplierFoodTypeBIncidence = 0.5F;

    private static int size = 10;
    private boolean playing = false;
    private float timeSpeed = 10.0F;
    private ArrayListCube<Represetative2dObject> cube = new ArrayListCube<Represetative2dObject>();
    private static Environment instance = new Environment();
    private EnvironmentServer server = null;
    private XMPPConnectionHandler connectionXMPP;
    private Hashtable<String, String> borders = new Hashtable<String, String>();
    private SynchronizedCounter individualCount = new SynchronizedCounter();
    private SynchronizedCounter foodCount = new SynchronizedCounter();
    private EnvironmentMaintainer maintainer = null;

    /**
     * Returns the singleton instance defining its size
     * @param size
     * @return
     */
    public static Environment getInstance(int size) {
        Environment.setSize(size);
        return instance;
    }

    /**
     * Returns the Environment singleton instance
     * @return
     */
    public static Environment getInstance() {
        return instance;
    }

    /**
     * Creates a new environment with width of size and heigh of size
     * @param size
     */
    private Environment() {
        /*
        try {
            server = new EnvironmentServer();
            Thread serverThread = new Thread(server);
            serverThread.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         * 
         */

        connectionXMPP = new EnvironmentXMPPConnectionHandler(this);
        try {
            connectionXMPP.connect();
        } catch (XMPPException ex) {
            ex.printStackTrace();
        }
        connectionXMPP.start();
    }

    /**
     * Defines the Environment size
     * @param size
     */
    public static void setSize(int size) {
        Environment.size = size;
    }

    /**
     * Retrieves the Environment size
     * @return
     */
    public int getSize() {
        return Environment.size;
    }

    /**
     * Returns if the environment is plalying or paused
     * @return playing status
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Retrieves the Environment time speed multiplier
     * @return
     */
    public float getTimeSpeed() {
        return timeSpeed;
    }

    /**
     * Retrieves the incidence of food A multiplier
     * @return
     */
    public float getMultiplierFoodTypeAIncidence() {
        return multiplierFoodTypeAIncidence;
    }

    /**
     * Retrieves the incidence of food B multiplier
     * @return
     */
    public float getMultiplierFoodTypeBIncidence() {
        return multiplierFoodTypeBIncidence;
    }

    /**
     * Returns the number of food to be created automatically
     * @return
     */
    public int getGeneratedFoodAmount() {
        return (int) ((size * size) / Configuration.ENVIRONMENT_AREA_DIVISOR_FOR_FOOD_AMOUNT);
    }

    /**
     * Retrieves the food generation pace multiplier
     * @return
     */
    public float getMultiplierGeneratedFoodPace() {
        return multiplierGeneratedFoodPace;
    }

    /**
     * Retrieves the lost energy multiplier
     * @return
     */
    public float getMultiplierLostEnergy() {
        return multiplierLostEnergy;
    }

    /**
     * Retrieves the mutation chance multiplier from environment
     * @return
     */
    public float getMultiplierMutationChance() {
        return multiplierMutationChance;
    }

    /**
     * Retrieves the mutation effect in DNAs
     * @return
     */
    public float getMultiplierMutationEffect() {
        return multiplierMutationEffect;
    }

    /**
     * Changes the time speed multiplier for the Environment
     * @param multiplier
     */
    public void setTimeSpeed(float multiplier) {
        timeSpeed = multiplier;
    }

    /**
     * Defines the incidence of Food A
     * @param foodTypeAIncidence
     */
    public void setMultiplierFoodTypeAIncidence(float foodTypeAIncidence) {
        this.multiplierFoodTypeAIncidence = foodTypeAIncidence;
    }

    /**
     * Defines the incidence of Food B
     * @param foodTypeBIncidence
     */
    public void setMultiplierFoodTypeBIncidence(float foodTypeBIncidence) {
        this.multiplierFoodTypeBIncidence = foodTypeBIncidence;
    }

    /**
     * Defines the Food generation pace multiplier
     * @param generatedFoodPace
     */
    public void setMultiplierGeneratedFoodPace(float generatedFoodPace) {
        this.multiplierGeneratedFoodPace = generatedFoodPace;
    }

    /**
     * Defines the energy lost multiplier
     * @param lostEnergyMultiplier
     */
    public void setMultiplierLostEnergy(float lostEnergyMultiplier) {
        this.multiplierLostEnergy = lostEnergyMultiplier;
    }

    /**
     * Defines the mutation chance multiplier
     * @param mutationChance
     */
    public void setMultiplierMutationChance(float mutationChance) {
        this.multiplierMutationChance = mutationChance;
    }

    /**
     * Defines the mutation effect multiplier
     * @param mutationEffect
     */
    public void setMultiplierMutationEffect(float mutationEffect) {
        this.multiplierMutationEffect = mutationEffect;
    }

    /**
     * Adds an individual to this environment
     * @param individual
     */
    public void addIndividual(Individual individual) {
        addObserver(individual);
        setRandomPosition(individual);
        add((Represetative2dObject) individual);
        startIndividual(individual);
    }

    /**
     * Adds an individual to this environment
     * @todo Sometimes newborn individuals are not observing the environment, so they do not respond to signals
     * @param individual
     * @param xPosition
     * @param yPosition
     */
    public void addIndividual(Individual individual, int xPosition, int yPosition) {
        //individual.incrementCounter(individualCount);
        addObserver(individual);
        individual.setLocation(xPosition, yPosition);
        add((Represetative2dObject) individual);
        startIndividual(individual);
    }

    /**
     * Adds an individual to this environment
     * @param individual
     * @param xPosition
     * @param yPosition
     */
    public void addIndividual(Individual individual, double xPosition, double yPosition) {
        addIndividual(individual, (int) xPosition, (int) yPosition);
    }

    /**
     * Adds a food to this environment
     * @param food
     */
    public void addFood(Food food) {
        foodCount.increment();
        setRandomPosition(food);
        add((Represetative2dObject) food);
    }

    /**
     * Adds a representative2dObject
     * @param item
     */
    private void add(Represetative2dObject item) {
        item.setEnvironment(this);
        //System.out.println("adicionando ao cubo o item: " + item + " " + item.getX() + " " + item.getY());
        cube.add(item.getX(), item.getY(), item);
        //System.out.println("cube.get " + item.getX() + " " + item.getY() + " " + cube.getLastInPosition((int)item.getX(), (int)item.getY()));
        setChanged();
        notifyObservers();
    }

    /**
     * Removes an individual from this environment
     * @param individual
     */
    public void remove(Individual individual) {
        //individual.decrementCounter(individualCount);
        remove((Represetative2dObject) individual);
    }

    /**
     * Removes a food from this environment
     * @param food
     */
    public void remove(Food food) {
        foodCount.decrement();
        remove((Represetative2dObject) food);
    }

    /**
     * Generic removal method that removes anything that can be stored in an
     * environment
     * @param item
     */
    private void remove(Represetative2dObject item) {
        cube.remove(item.getX(), item.getY(), item);
        item = null;
    }

    /**
     * Moves a Representative2dObject from his actual place to a new coordinate
     * @param item
     * @param destinationX
     * @param destinationY
     */
    public void move(Represetative2dObject item, int destinationX, int destinationY) {
        if (willEmigrate(destinationX, destinationY)) {
            emigrate((Individual) item, getEmigrationDirection(destinationX, destinationY));
        } else {
            destinationX = restrictToLimits(destinationX);
            destinationY = restrictToLimits(destinationY);

            int actualX = (int)item.getX();
            int actualY = (int)item.getY();

            if(destinationX != actualX || destinationY != actualY) {
                cube.remove(actualX, actualY, item);
                cube.add(destinationX, destinationY, item);
                cube.remove(actualX, actualY, item);
                item.setLocation(destinationX, destinationY);

                //System.out.println("movendo no env");
                setChanged();
                notifyObservers(new MoveMessage("move"));
            }
        }
    }

    /**
     * Moves a Representative2dObject from his actual place to a new coordinate
     * @param item
     * @param destinationX
     * @param destinationY
     */
    public void move(Represetative2dObject item, double destinationX, double destinationY) {
        move(item, (int) destinationX, (int) destinationY);
    }

    /**
     * If value is out of boundaries, fix it to keep within
     * @param value
     * @return
     */
    private int restrictToLimits(int value) {
        value = Math.max(value, 0);
        value = Math.min(value, size - 1);
        return (int) value;
    }

    /**
     * checks if value is within the geographical limits of this environment
     * @param value
     * @return
     */
    private boolean isWithinLimits(int value) {
        return value >= 0 && value < size;
    }

    /**
     * Identifies, based on destination coordinate, if it should emigrate
     * @todo besides verifying if its out of limits, it showl identify if in that direction exists a connection
     * @param destinationX
     * @param destinationY
     * @return
     */
    private boolean willEmigrate(int destinationX, int destinationY) {
        return !isWithinLimits(destinationX) || !isWithinLimits(destinationY);
    }

    /**
     * Sends the Individual to another environment
     * @param individual
     * @param direction
     */
    private void emigrate(Individual individual, String direction) {
        if (EnvironmentProtocol.isValidDirection(direction)) {
            try {
                sendMessageToDirection(direction, individual.serializeXML());
                individual.die();
            } catch (XMPPException ex) {
                System.out.println("Failed sending serialized Individual to " + direction);
            }
        }
    }

    /**
     * Identifies the direction of emigration based on given coordinates
     * @param destinationX
     * @param destinationY
     * @return direction to emigrate to
     */
    private String getEmigrationDirection(int destinationX, int destinationY) {
        if (destinationX >= size) {
            return EnvironmentProtocol.DIRECTION_EAST;
        } else if (destinationX < 0) {
            return EnvironmentProtocol.DIRECTION_WEST;
        } else if (destinationY >= size) {
            return EnvironmentProtocol.DIRECTION_SOUTH;
        } else {
            return EnvironmentProtocol.DIRECTION_NORTH;
        }
    }

    /**
     * Checks from origin, if target is in range. Returns true if the distance (
     * both vertical and horizontal) are lower than range.
     * @param origin
     * @param target
     * @param range
     * @return
     */
    private boolean isWithinRange(Represetative2dObject origin, Represetative2dObject target, int range) {
        return Math.abs(origin.getX() - target.getX()) <= range
                && Math.abs(origin.getY() - target.getY()) <= range;
    }

    /**
     * Searches in position x,y for any object
     * @param x
     * @param y
     * @return ArrayList<Representative2dObject> foundItens
     */
    public ArrayList<Represetative2dObject> getObjectsInPosition(int x, int y) {

        ArrayList<Represetative2dObject> foundItens = new ArrayList<Represetative2dObject>();
        foundItens.addAll(cube.getListInPosition(x, y));

        return foundItens;
    }

    /**
     * Searches in position x,y for any Representative2dObject
     * @param x
     * @param y
     * @return Represetative2dObject first object in this point
     */
    public Represetative2dObject getFirstObjectInPosition(int x, int y) {
        //System.out.println("1o obj da pos: " + x + ":" + y);
        if (!cube.isEmptyPosition(x, y)) {
            Represetative2dObject found = cube.getFirstInPosition(x, y);
            if(found instanceof Individual) {
                if(((Individual)found).isDead()) {
                    cube.remove(x, y, found);
                    return null;
                }
            }
            //System.out.println("achou-------------------------------- " + found);
            return found;
        }

        return null;
    }

    /**
     * Searches in position x,y for any Individual
     * @param x
     * @param y
     * @return
     */
    public Individual getIndividualInPosition(int x, int y) {
        if (!cube.isEmptyPosition(x, y)) {
            for (Represetative2dObject item : cube.getListInPosition(x, y)) {
                if(item instanceof Individual) {
                    if(!((Individual)item).isDead()) {
                        return (Individual)item;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Searches in position x,y for any Individual. If none found, retrieves the
     * first RepresentativeObject in position
     * @param x
     * @param y
     * @return
     */
    public Represetative2dObject getFirstIndividualOrObjectInPosition(int x, int y) {
        Individual ind = getIndividualInPosition(x, y);
        if( ind != null ) {
            return (Represetative2dObject)ind;
        }
        return getFirstObjectInPosition(x, y);
    }

    /**
     * Searches in range starting from position for any object from class filter
     * except for ignoreObject
     * @param position
     * @param range
     * @param classFilter
     * @return
     */
    private ArrayList search(Point position, int range, Class classFilter, Represetative2dObject ignoreObject) {
        int horiMin = (int) Math.max(position.getX() - range, 0);
        int vertMin = (int) Math.max(position.getY() - range, 0);
        int horiMax = (int) Math.min(position.getX() + range, size - 1);
        int vertMax = (int) Math.min(position.getX() + range, size - 1);

        ArrayList<Represetative2dObject> foundItens = new ArrayList<Represetative2dObject>();

        for (int i = horiMin; i < horiMax; i++) {
            for (int j = vertMin; j < vertMax; j++) {
                foundItens.addAll(searchInPosition(i, j, classFilter, ignoreObject));
            }
        }

        return foundItens;
    }

    /**
     * Searches in position x,y for any object from class filter except for ignoreObject
     * @param position
     * @param range
     * @param classFilter
     * @return
     */
    private ArrayList<Represetative2dObject> searchInPosition(int x, int y, Class classFilter, Represetative2dObject ignoreObject) {

        ArrayList<Represetative2dObject> foundItens = new ArrayList<Represetative2dObject>();

        if (!cube.isEmptyPosition(x, y)) {
            for (Represetative2dObject item : cube.getListInPosition(x, y)) {
                if (classFilter.isInstance(item)) {
                    if (!item.equals(ignoreObject)) {
                        foundItens.add(item);
                    }
                }
            }
        }

        return foundItens;
    }

    /**
     * Searches for food in the range starting from position (respects the
     * boundaries of the environment)
     * @param position
     * @param range
     * @param ignoreFood
     * @return
     */
    public ArrayList<Food> searchFood(Point position, int range, Food ignoreFood) {
        return search(position, range, Food.class, ignoreFood);
    }

    /**
     * Searches for individuals in the range starting from position (respects
     * the boundaries of the environment)
     * @param position
     * @param range
     * @param ignoreIndividual
     * @return
     */
    public ArrayList<Individual> searchIndividual(Point position, int range, Individual ignoreIndividual) {
        return search(position, range, Individual.class, ignoreIndividual);
    }

    /**
     * Places the item in some random position (inside the map)
     * @param item
     */
    private void setRandomPosition(Represetative2dObject item) {
        int x = calculateRandomPosition();
        int y = calculateRandomPosition();
        item.setLocation(x, y);
    }

    /**
     * creates one food in map
     */
    public void createFood() {
        int type = 0;
        if( Math.random() > 0.5 + getMultiplierFoodTypeAIncidence() ) {
            type = 1;
        }
        Food food = new Food(type, 20);
        addFood(food);
    }

    /**
     * Creates some food
     * @param quantity
     */
    public void createFood(int quantity) {
        for (int i = 0; i < quantity; i++) {
            createFood();
        }
    }

    /**
     * Creates a new random individual
     */
    public void createIndividual() {
        Individual individual = new Individual();
        addIndividual(individual);
    }

    /**
     * Starts the individual Thread
     * @param individual
     */
    public void startIndividual(Individual individual) {
        if (!individual.isThreadRunning()) {
            Thread t = new Thread(individual);
            t.start();
        }
    }

    /**
     * Creates some random individuals
     * @param quantity
     */
    public void createIndividual(int quantity) {
        for (int i = 0; i < quantity; i++) {
            createIndividual();
        }
    }

    /**
     * Retrieves the cube
     * @return
     */
    public ArrayListCube<Represetative2dObject> getCube() {
        return cube;
    }

    /**
     * Plays this Environment simulation, playing every individual that is in
     * suspended animation
     */
    public void play() {
        if (maintainer == null) {
            maintainer = new EnvironmentMaintainer(instance);
            Thread maintainerThread = new Thread(maintainer);
            maintainerThread.start();
        }

        playing = true;
        setChanged();
        notifyObservers(new PlayPauseMessage("play"));
    }

    /**
     * Pauses this Environment simulation putting every Individual in suspended
     * animation
     */
    public void pause() {
        playing = false;
        setChanged();
        notifyObservers(new PlayPauseMessage("pause"));
    }

    /**
     * Dumps to text the elements presents in this environmet, usefull for 
     * debugging.
     */
    public void dump() {
        System.out.println("----------dump-----------");
        cube.dump();
    }

    /**
     * Retrieves the number of Individuals in this Environment
     * @return
     */
    public int getIndividualCount() {
        return individualCount.getCount();
    }

    /**
     * Retrieves the Individual syncronized counter
     * @return
     */
    public SynchronizedCounter getIndividualCounter() {
        return individualCount;
    }

    /**
     * Retrieves the number of Foods in this Environment
     * @return
     */
    public int getFoodCount() {
        return foodCount.getCount();
    }

    /**
     * Removes any ghost Individual or Food that are still present in the Environment
     */
    public void maintenanceRemoval() {
        for (Object obj : cube) {
            if (Individual.class.isInstance(obj)) {
                Individual ind = (Individual) obj;
                if (ind.isTimeToDie()) {
                    ind.die();
                }
            } else if (Food.class.isInstance(obj)) {
                Food food = (Food) obj;
                if (food.getType() == Food.TYPE_EATEN) {
                    remove(food);
                }
            }
        }
    }

    /**
     * Calculates a random position from 0 to Environment.size
     * @return position
     */
    private int calculateRandomPosition() {
        return (int) Math.floor(Math.random() * size);
    }

    /**
     * Calculates a random position that is located next to the Environment border
     * @return position
     */
    private int calculateRandomPositionMarginal() {
        return (int) Math.min(2 + Math.floor(Math.random() * (size * 0.1)), size);
    }

    /**
     * Calculates the middle position in Environment (half of Environment size)
     * @return
     */
    private int calculateMiddlePosition() {
        return (int) size / 2;
    }

    /**
     * Receives an Individual and places it next to the northen border
     * @param ind
     */
    public void receiveNorthenImmigrant(Individual ind) {
        addIndividual(ind, calculateRandomPositionMarginal(), calculateMiddlePosition());
    }

    /**
     * Receives and Individual and places it next to the southern border
     * @param ind
     */
    public void receiveSouthernImmigrant(Individual ind) {
        addIndividual(ind, size - calculateRandomPositionMarginal(), calculateMiddlePosition());
    }

    /**
     * Receives an Individual and places it next to the western border
     * @param ind
     */
    public void receiveWesternImmigrant(Individual ind) {
        addIndividual(ind, calculateMiddlePosition(), calculateRandomPositionMarginal());
    }

    /**
     * Receives an Individual and places it next to the eastern border
     * @param ind
     */
    public void receiveEasternImmigrant(Individual ind) {
        addIndividual(ind, calculateMiddlePosition(), size - calculateRandomPositionMarginal());
    }

    /**
     * Defines an username to be the Environment to where an Individual is sent
     * when outpasses the respective border
     * @param direction
     * @param username
     */
    public void setBorderEnvironment(String direction, String username) {
        if (EnvironmentProtocol.isValidDirection(direction)) {
            borders.put(direction, username);
        }
    }

    /**
     * Retrives the username of given direction
     * @param direction
     * @return the username of the border in given direction
     */
    public String getBorderEnvironment(String direction) {
        return borders.get(direction);
    }

    /**
     * Removes an Environment from a border defined in the direction
     * @param direction
     */
    public void freeBorderEnvironmentByDirection(String direction) {
        if (EnvironmentProtocol.isValidDirection(direction)) {
            borders.remove(direction);
        }
    }

    /**
     * Sends a message to an user
     * @param user
     * @param message
     * @throws XMPPException
     */
    private void sendMessageToUser(String user, String message) throws XMPPException {
        if (borders.containsValue(user)) {
            connectionXMPP.sendMessage(user, message);
        }
    }

    /**
     * Sends a message to the user of Environment that is stored in given direction
     * @param direction
     * @param message
     * @throws XMPPException
     */
    private void sendMessageToDirection(String direction, String message) throws XMPPException {
        if (EnvironmentProtocol.isValidDirection(direction)) {
            if (borders.containsKey(direction)) {
                if (direction != null) {
                    String user = borders.get(direction);
                    sendMessageToUser(user, message);
                }
            }
        }
    }

    /**
     * @todo remove this method
     * @param individual
     */
    public void tmpWriteIndividual(Individual individual) {
        try {
            FileOutputStream out = new FileOutputStream("/home/romulo/individual");
            writeXMLSerializedIndividual(individual, out);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @todo remove this method
     * @param individual
     * @param out
     */
    public void writeXMLSerializedIndividual(Individual individual, OutputStream out) {
        BufferedWriter bwrt = new BufferedWriter(new OutputStreamWriter(out));
        try {
            bwrt.write(individual.serializeXML());
            bwrt.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
