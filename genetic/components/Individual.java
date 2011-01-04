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
package genetic.components;

import com.thoughtworks.xstream.XStream;
import genetic.genotipe.DNA;
import genetic.fenotipe.GenotipeToFenotipe;
import genetic.message.ObservableMessage;
import genetic.message.PlayPauseMessage;
import genetic.strategies.selection.SelectionFactory;
import genetic.util.Configuration;
import genetic.util.SynchronizedCounter;
import java.awt.Point;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Controls all aspects that conceptually are referred to an individual, 
 * integrates it in the population, environment and his DNA
 * 
 * @author romulo
 */
public class Individual extends Food implements Runnable, Observer, Serializable {

    private DNA dna;
    private int childrens = 0, selectedAsPartner = 0;
    private double mutationRatio = Configuration.MUTATION_DEFAULT_CHANCE;
    private double mutationEffectPercent = Configuration.MUTATION_DEFAULT_EFFECT;
    private int age = 0;
    private boolean dead = false, playing = false;
    private int energy = 100, burntEnergy;
    private GenotipeToFenotipe fenotipeMapping;
    private boolean threadRunning = false;
    private double id = Math.random();

    /**
     * Default method to create a new random individual
     */
    public Individual() {
        super(Food.TYPE_B,100);
        dna = new DNA();
        postInitilize();
    }

    /**
     *
     * @param genSequence
     */
    public Individual(String genSequence) {
        super(Food.TYPE_B,100);
        dna = DNA.parseString(genSequence);
        postInitilize();
    }

    /**
     * Creates a new Individual cloned from a DNA
     * @param father
     * @param mother
     */
    public Individual(Individual father, Individual mother) {
        super(Food.TYPE_B,mother.getEnergy() / 2);
        setEnergy( mother.getEnergy() / 2 );
        mother.setEnergy(mother.getEnergy() / 2);
        //System.out.println("copulating: " + father + " " + mother);
        dna = new DNA(father.getDNA(), mother.getDNA());
        if( mother.isTimeToDie() ) {
            mother.die();
        }
        postInitilize();
    }

    /**
     * Creates a new Individual cloned from a DNA
     * @param dna
     */
    public Individual(DNA dna) {
        super(Food.TYPE_B,100);
        this.dna = dna;
        postInitilize();
    }

    /**
     * Do actions of initialization after a base individual creation
     */
    public final void postInitilize() {
        fenotipeMapping = new GenotipeToFenotipe(dna);
    }

    /**
     * Executes a turn of this individual, the turn is the time when an individual
     * can interact with the environment
     */
    public void turn() {
        if(isPlaying()) {
            feed();
            reproduce();
            move();
            maintainance();
        } else {
            if( environment.isPlaying() ) {
                die();
            }
        }
        metabolicPause();
    }

    /**
     * Process the actions related to an individual alimentation
     */
    private void feed() {
        if (isHungry()) {
            ArrayList<Food> nearFoods = environment.searchFood(this, fenotipeMapping.getSightRange(), this);
            if (nearFoods.size() > 0) {
                Food choosenFood = chooseFood(nearFoods);
                if(choosenFood != null) {
                    if(Individual.class.isInstance(choosenFood)) {
                        killAndEat((Individual)choosenFood);
                    } else {
                        eat(choosenFood);
                    }
                }
            }
        }
    }

    /**
     * Selects from a given FoodList the food instance it prefers
     * @param foods
     * @return
     */
    private Food chooseFood(ArrayList<Food> foods) {
        int prefA = fenotipeMapping.getPreferenceFoodA();
        int prefB = fenotipeMapping.getPreferenceFoodB();
        int total = prefA + prefB;

        int preference = Food.TYPE_B;
        if( Math.round(Math.random() * total) <= prefA ) {
            preference = Food.TYPE_A;
        }

        for (Food foodInstance : foods) {
            if( preference == foodInstance.getType() ) {
                return foodInstance;
            }
        }
        return null;
    }

    /**
     * eats the food aquiring it's energy and destroying it
     * @param food
     */
    private void eat(Food food) {
        setEnergy( food.getEnergeticValue() + getEnergy() );
        food.eaten();
    }

    /**
     * kills it's prey and eats it
     * @param prey
     */
    private void killAndEat(Individual prey) {
        setEnergy(prey.getEnergeticValue() + getEnergy());
        prey.turnToInvalidFood();
        //System.out.println("DIE Bitch!! " + this + " is haunting");
        prey.die();
    }

    /**
     * Process the actions related to an individual reproduction
     */
    private void reproduce() {
        //System.out.println("trying to reproduce");
        if (isSexualyMature()) {
            ArrayList<Individual> nearIndividuals = environment.searchIndividual(this, fenotipeMapping.getOlfactoryRange(), this);
            if( nearIndividuals.size() > 0 ) {
                //System.out.println("there are " + nearIndividuals.size() + " nearby individuals");
                Individual newborn = choosePartnerAndProcriate(nearIndividuals);
                if( newborn != null ) {
                    if(!newborn.isTimeToDie()) {
                        environment.addIndividual(newborn, this.getX(), this.getY());
                        //System.out.println("Birth: " + newborn);
                        //System.out.println("birth");
                    } else {
                        newborn = null;
                    }
                }
            }
        }
    }

    /**
     * Process the actions related to an individual maintenance
     */
    private void maintainance() {
        growUp();
        spendEnergy();
        if (isTimeToDie()) {
            die();
        }
    }

    /**
     * The individual makes a pause, the duration of the pause is a fenotipe so
     * different individuals have different pause times.
     */
    public void metabolicPause() {
        int delayTime = (int)((fenotipeMapping.getMetabolicPauseTime() + 1) * 10 * environment.getTimeSpeed());
        //System.out.println("adormecendo por " + delayTime + " milisecs: " + (fenotipeMapping.getMetabolicPauseTime() + 1) + " * 100 * " + environment.getTimeSpeed());
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException ex) {
            return;
        }
    }

    /**
     * Returns a random distance, based on the maximum movimentation limit of the
     * individual, based on his genotipe.
     *
     * @return
     */
    private int randomMovementDistance() {
        return (int) Math.round(Math.random() * getMovimentationLimit());
    }

    /**
     *
     * @return
     */
    private int randomMovementDirection() {
        if (Math.round(Math.random()) == 1) {
            return -1;
        }
        return 1;
    }

    /**
     * Process the actions related to an individual alimentation
     */
    private void move() {
        int distance = randomMovementDistance() * randomMovementDirection();
        double destinationX = getX();
        double destinationY = getY();

        if (Math.round(Math.random()) == 1) {
            destinationX += distance;
        } else {
            destinationY += distance;
        }
        burntEnergy += Math.abs(distance);
        environment.move(this, destinationX, destinationY);
    }

    /**
     * makes the individual spen energy according to the actions it have done in
     * this turn
     */
    private void spendEnergy() {
        int burntEnergyEnvironmental = (int)Math.round(burntEnergy * environment.getMultiplierLostEnergy());
        setEnergy( getEnergy() - burntEnergyEnvironmental );
        setEnergeticValue(burntEnergyEnvironmental);
        //System.out.println("spent " + burntEnergy + " points of energy, " + energy + " energy points left");
        burntEnergy = 0;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "dna [" + dna + "] energy [" + getEnergy() + "] age [" + getAge() + "] pos[" + getX() + ":" + getY() + "]";
    }

    /**
     * Each individual can have a different image based on his DNA
     * @return the name of the image that represents this individual characteristics
     */
    @Override
    public String getImageName() {
        String imageName = "individual/individual_";
        imageName += fenotipeMapping.getFoodType();
        imageName += fenotipeMapping.getQualitativeLocomotion();
        imageName += fenotipeMapping.getQualitativeOlfactory();
        imageName += fenotipeMapping.getQualitativeSight();
        imageName += ".png";
        //System.out.println("imagem: " + imageName);
        return imageName;
    }

    /**
     * Returns the DNA sequence
     *
     * @return sequence
     */
    public DNA getDNA() {
        return dna;
    }

    /**
     * Defines the DNA of this Individual
     *
     * @param dna
     */
    public void setDNA(DNA dna) {
        this.dna = dna;
    }

    /**
     * Identifies, based on genotipe and actual age, if an individual is sexually
     * mature
     *
     * @return
     */
    private boolean isSexualyMature() {
        return age > fenotipeMapping.getSexualMaturityAge();
    }

    /**
     * Returns the movimentation max limit based on the individual's genotipe
     *
     * @return
     */
    private int getMovimentationLimit() {
        return fenotipeMapping.getMaximumLocomotionRange();
    }

    /**
     * Returns true if this is a dead individual
     * @return
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Returns true if this individual is playing, or false if this individual
     * is paused
     * @return
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Identifies if this Individual instance Thread is running
     *
     * @return
     */
    public boolean isThreadRunning() {
        return threadRunning;
    }

    /**
     * Idetifies, based on energy and genotipe, if this individual shall feed in
     * this moment
     *
     * @return
     */
    private boolean isHungry() {
        if (energy < 30) {
            return true;
        } else {
            if (Math.random() < Configuration.NOTHUNGRY_DEFAULT_FEED_PROBABILITY) {
                return true;
            }
        }
        return false;
    }

    /**
     * informs if is time to die or not for this individual
     * @return
     */
    public boolean isTimeToDie() {
        //System.out.println("time to die? age: " + age + " longevidade " + fenotipeMapping.getLongevity() );
        return (age > fenotipeMapping.getLongevity() || energy <= 0);
    }

    /**
     * Returns the age of the individual
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the actual energy of the individual
     * @return
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Defines the current energy of this individual
     * @param energy
     */
    private void setEnergy(int energy) {
        this.energy = energy;
        setEnergeticValue(energy);
    }

    /**
     * returns the similarity of an individual to another
     * @param ind
     * @return
     */
    public double getSimilarity(Individual ind) {
        return getSimilarity(ind.getDNA());
    }

    /**
     * returns the similarity of an individual to another
     * @param dna
     * @return
     */
    public double getSimilarity(DNA dna) {
        return this.dna.getSimilarity(dna);
    }

    /**
     * Retrieves the number of times this Individual has conquered a partner and
     * procriated
     * @return
     */
    public int getChildrens() {
        return childrens;
    }

    /**
     * Retrieves the number of times this Individual has been choosed as someother
     * Individuals partner to procriate
     * @return
     */
    public int getSelectedAsPartner() {
        return selectedAsPartner;
    }

    /**
     * The distance range of this Individual vision
     * @return
     */
    public int getSightRange() {
        return fenotipeMapping.getSightRange();
    }

    /**
     * The distance range of this Individual olfactory
     * @return
     */
    public int getOlfactoryRange() {
        return fenotipeMapping.getOlfactoryRange();
    }

    /**
     * Sexual procriation generates a new individual based on the parents
     * sequence
     *
     * todo: Theres a need to create new forms of reproduction, creating more
     * than one individual, reversing the combination of the gens in folding,
     * etc
     *
     * @param partner
     * @return children
     */
    public Individual procriateSexually(Individual partner) {
        if( partner != null ) {
            //System.out.println("procriando com " + partner);
            Individual ind = new Individual(this, partner);
            double similarity = this.getSimilarity(partner);
            if (similarity >= Configuration.SIMILARITY_THRESHOLD_TO_MUTATION) {
                //System.out.println("similaridade alta entre {" + this + "} e {" + partner + "} - sim[" + similarity + "]");
                ind.mutate(1, Configuration.MUTATION_DEFAULT_EFFECT);
            }
            childrens++;
            //System.out.println(ind + " - has just born!");
            return ind;
        }
        return null;
    }

    /**
     * Assexual procriation generates a new individual based in its predecessor
     * only, if theres no mutation, they'll be the same (a clone)
     *
     * @return
     */
    public Individual procriateAssexually() {
        return procriateSexually(this);
    }

    /**
     * Selects an individual as partner based on energy and similarity
     * 
     * @param availablePartners
     * @return partner
     */
    public Individual choosePartner(ArrayList<Individual> availablePartners) {
        return SelectionFactory.getInstance().select(this, availablePartners);
    }

    /**
     * Increments the selectedAsPartner counter
     */
    public void selectedAsPartner() {
        selectedAsPartner++;
    }

    /**
     * Calculates the score of an individual in selection race
     * @param ind
     * @return
     */
    public static int calculateSelectionScore(Individual ind) {
        return ind.getEnergy();
    }

    /**
     * The individual looks at the population, choose an partner and procriate 
     * with it
     *
     * @param availablePartners
     * @return
     */
    public Individual choosePartnerAndProcriate(ArrayList<Individual> availablePartners) {
        return procriateSexually(choosePartner(availablePartners));
    }

    /**
     * makes the individual get older
     */
    public void growUp() {
        age++;
        //System.out.println("now " + age + " turns old.");
    }

    /**
     * Mutates this Individual DNA based on default mutationRatio
     */
    public void mutate() {
        dna.mutate(calculateMutationChance(),calculateMutationEffect());
    }

    /**
     * Calculates the mutation chance based on default chance, multiplied by
     * Environmental modifier
     * @return the chance of an mutation occurs
     */
    private double calculateMutationChance() {
        return mutationRatio * environment.getMultiplierMutationChance();
    }

    /**
     * Calculates the mutation effect based on default effect, multiplied by
     * Environmental modifier
     * @return the effect of a mutation, the percentual of gens it will change
     */
    private double calculateMutationEffect() {
        return mutationEffectPercent * environment.getMultiplierMutationEffect();
    }

    /**
     * Mutates this Individual DNA with passed ratio and effect
     * @param mutationRatio
     * @param mutationEffectPercent
     */
    public void mutate(double mutationRatio, double mutationEffectPercent) {
        dna.mutate(mutationRatio, mutationEffectPercent);
    }

    /**
     * make this individual to cease living
     */
    public void die() {
        dead = true;
        //System.out.println("RIP: " + this);
        //System.out.println("death " + hashCode());
        environment.deleteObserver(this);
        environment.remove(this);
        playing = false;
    }

    /**
     * Runnable method of an individual, executes turns until death
     */
    public void run() {
        //System.out.println("Thread running: " + this);
        playing = environment.isPlaying();
        threadRunning = true;
        environment.getIndividualCounter().increment();
        while (! dead ) {
            turn();
        }
        environment.getIndividualCounter().decrement();
        threadRunning = false;
    }

    /**
     * Pauses this individual. It does not die, but stays freezed like in
     * suspended animation
     */
    public void pause() {
        playing = false;
    }

    /**
     * Plays this individual, enabling him to live normally
     */
    public void play() {
        playing = true;
    }

    /**
     * Observer update
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        //System.out.println("individual update.");
        if( arg instanceof PlayPauseMessage ) {
            ObservableMessage m = (ObservableMessage)arg;
            if(m.getMessage().equals("play") ) {
                play();
                //System.out.println("mensagem recebida: play");
            } else
            {
                //System.out.println("mensagem recebida: stop");
                pause();
            }
        }
    }

    /**
     * Generates a hashcode for this Instance
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.dna != null ? this.dna.hashCode() : 0) + this.age + this.energy + (int)(this.id * 1000);
        return hash;
    }

    /**
     * Serializes this instance in a XML formatted String
     * @return the individual serialized as XML
     */
    public String serializeXML() {
        XStream xstream = new XStream();
        xstream.omitField(Represetative2dObject.class, "environment");
        xstream.omitField(Individual.class, "fenotipeMapping");
        xstream.omitField(Individual.class, "threadRunning");
        xstream.omitField(Point.class, "x");
        xstream.omitField(Point.class, "y");
        xstream.omitField(int.class, "burntEnergy");
        xstream.omitField(Individual.class, "playing");

        return xstream.toXML(this);
    }

    /**
     * Unserializes an Individual from given InputStream
     * @param in
     * @return the unserialized Individual instance
     */
    public static Individual unserializeXML(InputStream in) {
        XStream xstream = new XStream();
        Individual individual = (Individual)xstream.fromXML(in);
        return individual;
    }

    /**
     * Unserializes an Individual from given String
     * @param xml
     * @return the unserialized Individual instance
     */
    public static Individual unserializeXML(String xml) {
        XStream xstream = new XStream();
        Individual individual = (Individual)xstream.fromXML(xml);
        return individual;
    }

    /**
     * increments the given counter
     * @param counter
     */
    public void incrementCounter(SynchronizedCounter counter) {
        counter.increment();
    }

    /**
     * decrements the given counter
     * @param counter
     */
    public void decrementCounter(SynchronizedCounter counter) {
        counter.decrement();
    }
}
