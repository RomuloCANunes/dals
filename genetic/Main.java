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

import genetic.components.Individual;
import genetic.net.XMPPConnectionHandler;
import genetic.util.Configuration;
import genetic.view.EnvironmentOverview;
import genetic.view.Simulation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.jivesoftware.smack.XMPPException;

/**
 * Class for system initialization
 * @author romulo
 */
public class Main {

    private static boolean showSimulation = false, startupTests = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Configuration.loadConfigurationFromFile(Configuration.DEFAULT_CONFIG_FILE);
        
        Environment env = Environment.getInstance(Configuration.ENVIRONEMNT_SIZE);

        if (!showSimulation) {
            EnvironmentOverview.main(args);
        } else {
            Simulation.main(args);
        }

        if (startupTests) {
        }
    }
}
