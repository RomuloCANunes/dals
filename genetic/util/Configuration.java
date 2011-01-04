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
package genetic.util;

import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is store every global configuration of the system, in a way it
 * could be globally acessible throught public static properties.
 *
 * @author romulo
 */
public class Configuration {
    private static Configuration instance = new Configuration();
    /**
     * The default image directory of the system
     */
    public static final String DEFAULT_IMAGE_DIRECTORY = System.getProperty("user.dir") + "/images/";
    /**
     * The default configuration file
     */
    public static final String DEFAULT_CONFIG_FILE = System.getProperty("user.dir") + "/config/adisiva.conf";
    /**
     * The minimum acceptable value of similarity btween Individuals to allow them
     * to procriate
     */
    public static final double SIMILARITY_MINIMUM_TO_REPRODUCE = 0.5;
    /**
     * The maximum similarity btween two Individuals that do not generates a
     * sure mutation. Similarities under this value, have their mutation probability
     * normal. Similarities above this value, will mutate for sure.
     */
    public static final double SIMILARITY_THRESHOLD_TO_MUTATION = 0.8;
    /**
     * The defaul range effect of mutation in a DNA. 1 for 100% of gens, 0.5 for 50%
     */
    public static final double MUTATION_DEFAULT_EFFECT = 0.1;
    /**
     * The default chance of ocurring a mutation during reproduction
     */
    public static final double MUTATION_DEFAULT_CHANCE = 0.02;
    /**
     * Even when an Individual is not hungry, every turn it can feed, this constant
     * defines the probability of this to happen
     */
    public static final double NOTHUNGRY_DEFAULT_FEED_PROBABILITY = 0.1;
    /**
     * The area of Environment will be divided by this value to discover the
     * amount of food it should deliver every turn.
     */
    public static final int ENVIRONMENT_AREA_DIVISOR_FOR_FOOD_AMOUNT = 30;

    /**
     * The SO port to be used by the EnvironmentServer
     */
    public static int ENVIRONMENT_SOCKET_SERVER_PORT = 9666;

    /**
     * The XMPP server host
     */
    public static String XMPP_HOSTNAME = "";

    /**
     * The user to log in XMPP server
     */
    public static String XMPP_USERNAME = "";

    /**
     * The password to log in XMPP server
     */
    public static String XMPP_PASSWORD = "";

    /**
     * The password to log in XMPP server
     */
    public static int ENVIRONEMNT_SIZE = 15;

    
    /**
     * defines the hostname for XMPP connection
     * @param hostname
     */
    public static void setXMPPHostname(String hostname) {
        //System.out.println("hostname set: " + hostname);
        XMPP_HOSTNAME = hostname;
    }

    /**
     * defines the username for XMPP connection
     * @param username
     */
    public static void setXMPPUsername(String username) {
        //System.out.println("username set: " + username);
        XMPP_USERNAME = username;
    }

    /**
     * defines the password for XMPP connection
     * @param password
     */
    public static void setXMPPPassword(String password) {
        //System.out.println("password set: " + password);
        XMPP_PASSWORD = password;
    }

    /**
     * defines the port for use in socket server
     * @param port
     */
    public static void setSocketServerPort(int port) {
        ENVIRONMENT_SOCKET_SERVER_PORT = port;
    }

    /**
     * defines the environment size of simulation
     * @param port
     */
    public static void setEnvironmentSize(int size) {
        ENVIRONEMNT_SIZE = size;
    }

    /**
     * Reads the file from given filename, and extracts the configuration from it
     * @param filename
     */
    public static void loadConfigurationFromFile(String filename) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            ConfigurationSerializable loadedConf = unserializeXML(fis);
            setSocketServerPort(loadedConf.getEnvironmentSocketServer());
            setXMPPHostname(loadedConf.getXmppHostname());
            setXMPPPassword(loadedConf.getXmppPassword());
            setXMPPUsername(loadedConf.getXmppUsername());
            setEnvironmentSize(loadedConf.getEnvironmentSize());
        } catch (FileNotFoundException ex) {
            System.out.println("Configuration file " + filename + " not found.");
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static ConfigurationSerializable unserializeXML(InputStream in) {
        XStream xstream = new XStream();
        ConfigurationSerializable conf = (ConfigurationSerializable)xstream.fromXML(in);
        return conf;
    }

    /**
     * Serializes this instance to a XML String
     * @return
     */
    public String serializeXML() {
        XStream xstream = new XStream();
        ConfigurationSerializable serializable = new ConfigurationSerializable();
        serializable.setEnvironmentSocketServer(ENVIRONMENT_SOCKET_SERVER_PORT);
        serializable.setXmppPassword(XMPP_PASSWORD);
        serializable.setXmppUsername(XMPP_USERNAME);
        serializable.setXmppHostname(XMPP_HOSTNAME);
        serializable.setEnvironmentSize(ENVIRONEMNT_SIZE);
        return xstream.toXML(serializable);
    }

    class ConfigurationSerializable {
        public int environmentSocketServer;
        public String xmppPassword;
        public String xmppUsername;
        public String xmppHostname;
        public int environmentSize;

        public int getEnvironmentSocketServer() {
            return environmentSocketServer;
        }

        public String getXmppPassword() {
            return xmppPassword;
        }

        public String getXmppUsername() {
            return xmppUsername;
        }

        public String getXmppHostname() {
            return xmppHostname;
        }

        public int getEnvironmentSize() {
            return environmentSize;
        }

        public void setEnvironmentSocketServer(int environmentSocketServer) {
            this.environmentSocketServer = environmentSocketServer;
        }

        public void setXmppPassword(String xmppPassword) {
            this.xmppPassword = xmppPassword;
        }

        public void setXmppUsername(String xmppUsername) {
            this.xmppUsername = xmppUsername;
        }

        public void setXmppHostname(String xmppuHostname) {
            this.xmppHostname = xmppuHostname;
        }

        public void setEnvironmentSize(int environmentSize) {
            this.environmentSize = environmentSize;
        }
    }
}
