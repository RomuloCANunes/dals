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
package genetic.controller;

import genetic.Environment;
import genetic.view.EnvironmentConfiguration;
import genetic.view.EnvironmentConnections;
import genetic.view.EnvironmentDetail;
import genetic.view.Simulation;

/**
 * @todo detail action not implemented yet
 * @author romulo
 */
public class EnvironmentOverviewController {
    private String[] empty = new String[0];

    /**
     * Displays details about the environment
     */
    public void detailAction() {
        Environment env = Environment.getInstance();
        EnvironmentDetail detail = new EnvironmentDetail(env.getIndividualCount(), env.getFoodCount());
        detail.setVisible(true);
    }

    /**
     * Displays the running simulation
     */
    public void watchSimulation() {
        Simulation.main(empty);
    }

    /**
     * Displays the Environment connections
     */
    public void connections() {
        EnvironmentConnections.main(empty);
    }

    /**
     * Displays the Environment configurations
     */
    public void configuration() {
        EnvironmentConfiguration.main(empty);
    }

}
