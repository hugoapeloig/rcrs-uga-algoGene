package sampleagent;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import rescuecore2.Constants;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.components.TCPComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;
import rescuecore2.connection.ConnectionException;
import rescuecore2.misc.CommandLineOptions;
import rescuecore2.registry.Registry;
import rescuecore2.standard.entities.StandardEntityFactory;
import rescuecore2.standard.entities.StandardPropertyFactory;
import rescuecore2.standard.messages.StandardMessageComponentFactory;
import rescuecore2.standard.messages.StandardMessageFactory;

/**
 * Launcher for sample agents. This will launch as many instances of each of the
 * sample agents as possible, all using one connection.
 */
public final class LaunchSampleAgents {

  private static final String FIRE_BRIGADE_FLAG = "-fb";
  private static final String POLICE_FORCE_FLAG = "-pf";
  private static final String AMBULANCE_TEAM_FLAG = "-at";

  private static final Logger LOG = Logger.getLogger(LaunchSampleAgents.class);
  private static CentrePreDisaster central;
  /**
   * Launch 'em!
   *
   * @param args
   *   The following arguments are understood: -p <port>, -h
   *   <hostname>, -fb <fire brigades>, -pf <police forces>, -at
   *   <ambulance teams>
   */
  public static void main(String[] args) {
    try {
      Registry.SYSTEM_REGISTRY.registerFactory(StandardEntityFactory.INSTANCE);
      Registry.SYSTEM_REGISTRY.registerFactory(StandardMessageFactory.INSTANCE);
      Registry.SYSTEM_REGISTRY
          .registerFactory(StandardMessageComponentFactory.INSTANCE);
      Registry.SYSTEM_REGISTRY
          .registerFactory(StandardPropertyFactory.INSTANCE);
      Config config = new rescuecore2.config.Config();
      args = CommandLineOptions.processArgs(args, config);
      int port = config.getIntValue(Constants.KERNEL_PORT_NUMBER_KEY,
          Constants.DEFAULT_KERNEL_PORT_NUMBER);
      String host = config.getValue(Constants.KERNEL_HOST_NAME_KEY,
          Constants.DEFAULT_KERNEL_HOST_NAME);
      int fb = -1;
      int pf = -1;
      int at = -1;
      for (int i = 0; i < args.length; ++i) {
        if (args[i].equals(FIRE_BRIGADE_FLAG)) {
          fb = Integer.parseInt(args[++i]);
        } else if (args[i].equals(POLICE_FORCE_FLAG)) {
          pf = Integer.parseInt(args[++i]);
        } else if (args[i].equals(AMBULANCE_TEAM_FLAG)) {
          at = Integer.parseInt(args[++i]);
        } else {
          LOG.warn("Unrecognised option: " + args[i]);
        }
      }   
      central = new CentrePreDisaster(this.worldInfo.getRaw().map, this.worldInfo.getRaw()); //On cr�� le centrePreDisaster //Regarder comment r�cuperer map et worldModel
      ComponentLauncher launcher = new TCPComponentLauncher(host, port, config);
      connect(launcher, fb, pf, at, config);
    } catch (IOException e) {
      LOG.error("Error connecting agents", e);
    } catch (ConfigException e) {
      LOG.error("Configuration error", e);
    } catch (ComponentConnectionException e) {
      LOG.error("Component connection error ", e);
    } catch (ConnectionException e) {
      LOG.error("Error connecting agents", e);
    } catch (InterruptedException e) {
      LOG.error("Error connecting agents", e);
    }
  }


  private static void connect(ComponentLauncher launcher, int fb, int pf,
      int at, Config config) throws InterruptedException,
      ComponentConnectionException, ConnectionException {
    int i = 0;
    try {
    	int pathNumber=0;
    	ArrayList<ArrayList<Integer>> paths = central.getPathsForFireFighter();
      while (fb-- != 0) {
        LOG.info("Connecting fire brigade " + (i++) + "...");
        launcher.connect(new SampleFireBrigade(paths.get(pathNumber)));
        pathNumber ++;
        LOG.info("success");
      }
    } catch (ComponentConnectionException e) {
      LOG.info("failed: " + e.getMessage());
    }
    try {
      while (pf-- != 0) {
        LOG.info("Connecting police force " + (i++) + "...");
        launcher.connect(new SamplePoliceForce());
        LOG.info("success");
      }
    } catch (ComponentConnectionException e) {
      LOG.info("failed: " + e.getMessage());
    }
    try {
    	int pathNumber=0;
    	ArrayList<ArrayList<Integer>> paths = central.getPathsForAmbulance();
      while (at-- != 0) {
        LOG.info("Connecting ambulance team " + (i++) + "...");
        launcher.connect(new SampleAmbulanceTeam(paths.get(pathNumber)));
        pathNumber ++;
        LOG.info("success");
      }
    } catch (ComponentConnectionException e) {
      LOG.info("failed: " + e.getMessage());
    }
    try {
      while (true) {
        LOG.info("Connecting centre " + (i++) + "...");
        launcher.connect(new SampleCentre());
        LOG.info("success");
      }
    } catch (ComponentConnectionException e) {
      LOG.info("failed: " + e.getMessage());
    }
    try {
      while (true) {
        LOG.info("Connecting dummy agent " + (i++) + "...");
        launcher.connect(new DummyAgent());
        LOG.info("success");
      }
    } catch (ComponentConnectionException e) {
      LOG.info("failed: " + e.getMessage());
    }
  }
}