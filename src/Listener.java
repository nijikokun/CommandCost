
import java.util.ArrayList;
import java.util.Map;

/*
 * Listener listens to what I tell it to do.
 *
 * @author Nijikokun
 */
public class Listener extends PluginListener {

    public ArrayList<String> using = new ArrayList();

    public boolean onCommand(Player player, String[] split) {
	if (CommandCost.commands.containsKey(split[0])) {
	    if(!using.contains(player.getName())) {
		int Cost = Integer.valueOf(CommandCost.commands.get(split[0]));

		if (CommandCost.i.getBalance(player.getName()) >= Cost) {
		    int Balance = CommandCost.i.getBalance(player.getName());

		    // Update player balance
		    CommandCost.i.setBalance(player.getName(), (Balance - Cost));

		    player.sendMessage("§b[§fCC§b] §fThank you for your payment.. of §b(§f"+Cost+"§b)§f!");
		    player.sendMessage("§b[§fCC§b] §fFunds left: §b" + (Balance - Cost));

		    if(!player.canUseCommand(split[0])) {
			String[] commands = player.getCommands();
			String[] commandt = { split[0] };

			player.setCommands(commandt);
			etc.getDataSource().modifyPlayer(player);
			using.add(player.getName());

			player.sendMessage("§b[§fCC§b] §fExecuting command for you..");
			player.command(CommandCost.implode(split, " "));

			player.setCommands(commands);
			etc.getDataSource().modifyPlayer(player);

			return true;
		    }
		} else {
		    player.sendMessage(Colors.Rose + "You do not have enough funds to use this command!");
		    return true;
		}
	    } else {
		using.remove(player.getName());
	    }
	}

	if (split[0].equalsIgnoreCase(
		"/cc") && player.canUseCommand("/cc")) {
	    // Check the length to make sure that its at least this.
	    if (split.length != 3) {
		player.sendMessage(Colors.Red + "Usage: /cc [command] [cost]"); return true;
	    }

	    if (player.canUseCommand(split[1])) {
		String command = split[1];
		int cost = Integer.valueOf(split[2]);

		if (cost < 1) {
		    player.sendMessage(Colors.Rose + "Cost for a command must be over 1!");
		    return true;
		}

		CommandCost.commands.put(command, String.valueOf(cost));
		CommandCost.Commands.setString(command, String.valueOf(cost));

		player.sendMessage("§b[§fCC§b] §fCommand §b[§f" + command + "§b] §fadded to list for §b(§f" + cost + "§b)§f!");
	    } else {
		player.sendMessage(Colors.Rose + "You yourself cannot use that command!");
		return true;
	    }

	    return true;
	}

	if (split[0].equalsIgnoreCase(
		"/cr") && player.canUseCommand("/cr")) {
	    // Check the length to make sure that its at least this.
	    if (split.length != 2) {
		player.sendMessage(Colors.Red + "Usage: /cr [command]"); return true;
	    }

	    if (player.canUseCommand(split[1])) {
		String command = split[1];

		CommandCost.commands.remove(command);
		CommandCost.Commands.removeKey(command);

		player.sendMessage("§b[§fCC§b] §fCommand §b[§f" + command + "§b] §fremoved from the list§f!");
	    } else {
		player.sendMessage(Colors.Rose + "You yourself cannot use that command!");
		return true;
	    }

	    return true;
	}

	if (split[0].equalsIgnoreCase(
		"/cl") && player.canUseCommand("/cl")) {
	    String commands = "";

	    if (!CommandCost.commands.isEmpty()) {
		for (String command : CommandCost.commands.keySet()) {
		    String cost = CommandCost.commands.get(command);

		    commands += command + " §b(§f" + cost + "§b)§f, ";
		}

		commands = commands.substring(0, commands.length() - 4);
		player.sendMessage("§b[§fCC§b] §fCommand List: " + commands);
	    } else {
		player.sendMessage("§b[§fCC§b] §fCommand List: §bNone yet§f!");
	    }

	    return true;
	}
	return false;
    }
}
