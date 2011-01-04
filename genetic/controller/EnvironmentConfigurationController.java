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
package genetic.controller;

import genetic.Environment;

/**
 *
 * @author romulo
 */
public class EnvironmentConfigurationController {
    private Environment environment;

    /**
     * Default constructor
     * @param environment
     */
    public EnvironmentConfigurationController(Environment environment) {
        this.environment = environment;
    }
    /**
     * Saves the configurations
     * @param foodByType 
     * @param mutationEffect
     * @param foodGenerated
     * @param energySpent
     * @param mutationProbability
     */
    public void save(int foodByType, int energySpent, int foodGenerated, int mutationProbability, int mutationEffect) {
        int maxVal = 10;
        int minVal = 0;
        environment.setMultiplierFoodTypeAIncidence(calculateSliderPercentualMiddlepointBalance(maxVal, minVal, foodByType));
        environment.setMultiplierFoodTypeBIncidence(calculateSliderPercentualMiddlepointBalance(maxVal, minVal, foodByType) * -1);
        environment.setMultiplierGeneratedFoodPace(1 - calculateSliderPercentualMiddlepointBalance(maxVal, minVal, foodGenerated));
        environment.setMultiplierLostEnergy(1 + calculateSliderPercentualMiddlepointBalance(maxVal, minVal, energySpent));
        environment.setMultiplierMutationChance(1 + calculateSliderPercentualMiddlepointBalance(maxVal, minVal, mutationProbability));
        environment.setMultiplierMutationEffect(1 + calculateSliderPercentualMiddlepointBalance(maxVal, minVal, mutationEffect));
    }

    /**
     * calculates the range of a slider
     * @param minVal
     * @param maxVal
     * @return
     */
    protected float range(float minVal, float maxVal) {
        return maxVal - minVal;
    }

    /**
     * calculates the value of slider considering it starts in middle of the
     * slider, it increses to right and decreases to left
     * @param maxVal
     * @param minVal
     * @param value
     * @return
     */
    protected float calculateSliderValueMiddlepointBalance(float maxVal, float minVal, float value) {
        float middlePoint = range(minVal, maxVal) / 2;
        return value - middlePoint;
    }

    /**
     * calculates the value of a slider where value increses from left to right
     * @param minVal
     * @param value
     * @return
     */
    protected float calculateSliderValueLeftToRight(float minVal, float value) {
        return value - minVal;
    }

    /**
     * calculates the value of a slider where value increses from right to left
     * @param maxVal
     * @param minVal
     * @param value
     * @return
     */
    protected float calculateSliderValueRightToLeft(float maxVal, float minVal, float value) {
        return range(minVal, maxVal) - value - minVal;
    }

    /**
     * calculates the percentual relative from a slider where value increases
     * from right to left
     * @param maxVal
     * @param minVal
     * @param value
     * @return
     */
    protected float calculateSliderPercentualRightToLeft(float maxVal, float minVal, float value) {
        return calculateSliderValueRightToLeft(maxVal, minVal, value) / range(minVal, maxVal);
    }

    /**
     * calcullates the percentual relative from a slider where value increases
     * from left to right
     * @param maxVal
     * @param minVal
     * @param value
     * @return
     */
    protected float calculateSliderPercentualLeftToRight(float maxVal, float minVal, float value) {
        return calculateSliderValueLeftToRight(minVal, value) / range(minVal, maxVal);
    }

    /**
     * calculates the percentual relative from a slider where value increases
     * from left to right
     * @param maxVal
     * @param minVal
     * @param value
     * @return
     */
    protected float calculateSliderPercentualMiddlepointBalance(float maxVal, float minVal, float value) {
        return calculateSliderValueMiddlepointBalance(maxVal, minVal, value) / range(minVal, maxVal);
    }

}
