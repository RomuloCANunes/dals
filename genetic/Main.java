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
