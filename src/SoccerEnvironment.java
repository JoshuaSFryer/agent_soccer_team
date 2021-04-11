import jason.asSyntax.*;
import jason.environment.*;
import jason.mas2j.AgentParameters;
import jason.mas2j.MAS2JProject;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;
import java.util.logging.*;

import krislet.Krislet;
import krislet.Actions;

/**
 * The environment sets up the agents and manages them during the game
 */
public class SoccerEnvironment extends Environment {

	public static final int PORT = 6000;
	public static final String TEAM_NAME = "2JASON4JSON";
	public InetAddress ADDRESS;
	private HashMap<String, Krislet> agent_krislet_map;
	private Logger logger = Logger.getLogger("agent_soccer_team." + SoccerEnvironment.class.getName());

	/** Called before the MAS execution with the args informed in .mas2j */
	@Override
	public void init(String[] args) {
		super.init(args);

		try {
			ADDRESS = InetAddress.getByName("localhost");
		} catch (Exception e) {
			logger.info("Invalid hostname");
		}

		// args[0] is name of project file
		// Parse that file and get a list of agent names
		List<String> agent_names = getAgentNames(args[0]);

		// For each Krislet we want to spawn, create a Krislet
		agent_krislet_map = new HashMap<String, Krislet>();
		for (String name : agent_names) {
			Krislet krislet = null;
			try {
				krislet = new krislet.Krislet(ADDRESS, PORT, TEAM_NAME, name);
			} catch (SocketException e) {
				logger.info("Socket exception when instantiating Krislet");
			}
			agent_krislet_map.put(name, krislet);
			krislet.start(); // Actually start the agent
			logger.info("Agent Added: " + name);
		}

	}

	public List<String> getAgentNames(String project_filename) {
		List<String> agent_names = new ArrayList<String>();
		try {
			jason.mas2j.parser.mas2j parser = new jason.mas2j.parser.mas2j(new FileInputStream(project_filename));
			MAS2JProject project = parser.mas();
			// Get names from project
			for (AgentParameters ap : project.getAgents()) {
				String agName = ap.name;
				for (int cAg = 0; cAg < ap.getNbInstances(); cAg++) {
					String numberedAg = agName;
					if (ap.getNbInstances() > 1) {
						numberedAg += (cAg + 1);
					}
					agent_names.add(numberedAg);
				}
			}
		} catch (Exception e) {
			logger.info("Jason Project File parse error");
		}

		return agent_names;
	}

	public Krislet getKrisletByName(String agName) {
		return agent_krislet_map.get(agName);
	}

	@Override
	public Collection<Literal> getPercepts(String agName) {
		try {
			Krislet k = getKrisletByName(agName);

			// Sometimes a random agent call "df" tries to get its
			// percepts. If this happens, we pass it to the super handler.
			if (k != null) {
				k.updatePercepts();
				return k.getPercepts();
			} else {
				return super.getPercepts(agName);
			}
		} catch (Exception e) {
			logger.info("Exception occured" + e.getMessage());
			return new ArrayList<Literal>();
		}
	}

	public static final String FIND = "find";
	public static final String KICK = "kick";
	public static final String MOVETO = "moveto";
	public static final String WAIT = "wait";

	@Override
	public boolean executeAction(String agName, Structure action) {
		try {
			logger.info(agName + " is executing " + action);
			Krislet actor = getKrisletByName(agName);
			if (action.getFunctor().equals(FIND)) {
				actor.performAction(new Actions.FindAction(action.getTerm(0).toString()));
			} else if (action.getFunctor().equals(KICK)) {
				actor.performAction(new Actions.KickAction(action.getTerm(0).toString()));
			} else if (action.getFunctor().equals(MOVETO)) {
				actor.performAction(new Actions.MoveToAction(action.getTerm(0).toString()));
			} else if (action.getFunctor().equals(WAIT)) {
				actor.wait(Integer.parseInt(action.getTerm(0).toString()));
			} else {
				logger.info("executing: " + action + ", but not implemented!");
				return false;
			}
			actor.wait(1);
		} catch (Exception e) {
			logger.info("Exception occured" + e.getMessage());
		}

		return true; // the action was executed with success
	}

	/** Called before the end of MAS execution */
	@Override
	public void stop() {
		for (Krislet actor : agent_krislet_map.values()) {
			actor.bye();
		}
		super.stop();
	}
}
