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
