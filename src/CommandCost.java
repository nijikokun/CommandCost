//import statements
import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

/**
 * CommandCost
 *
 * @author: Nijikokun
 */
public class CommandCost extends Plugin {
    /**
     * Listener for the plugin system.
     */
    private static final Listener l = new Listener();
    
    /*
     * Loggery Foggery
     */
    public static final Logger log = Logger.getLogger("Minecraft");

    /*
     * Plugin information
     */
    private String name = "CommandCost";
    private String version = "0.1";
    private String directory = "CommandCost";
    
    /*
     * Plugin data
     */
    public static iProperty Commands;
    public static iData i;

    /*
     * Commands that we have already saved.
     */
    public static Map<String, String> commands;

    /**
     * Initializes the plugin.
     */
    public void initialize() {
	log.info("["+name+"] v" + version + " initialized.");
	etc.getLoader().addListener(PluginLoader.Hook.COMMAND, l, this, PluginListener.Priority.HIGH);
    }

    /**
     * Enables the plugin.
     */
    public void enable() {
	(new File(directory)).mkdir();
	Commands = new iProperty(directory + "commands.list");

	// iData
	if(!iData.iExist()) {
	    log.severe("[" + name + "] iConomy 0.9.5+ must be running/configured to continue...");
	} else {
	    i = new iData();
	}

	try {
	    commands = Commands.returnMap();
	} catch (Exception ex) {
	    log.info("[" + name + "] Either no commands, or invalid command list, or just something went wrong.");
	    log.info("[" + name + "] If this is your first time running this, ignore the line above.");
	}

	// Commands
	etc.getInstance().addCommand("/cc", " [command] [cost] - To give a command an amount to pay, also updates!");
	etc.getInstance().addCommand("/cr", " [command] - To remove a command from the list!");
	etc.getInstance().addCommand("/cl", " - Lists commands & costs!");
    }

    /**
     * Disables the plugin.
     */
    public void disable() {
	etc.getInstance().removeCommand("/cc");
	etc.getInstance().removeCommand("/cr");
	etc.getInstance().removeCommand("/cl");
    }

    public static String implode(String[] ary, String delim) {
	String out = "";

	for (int i = 0; i < ary.length; i++) {
	    out += ary[i] + delim;
	}

	return out;
    }
}
