package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

import java.util.HashMap;

interface CommandInterface {
    boolean onCommand(Player player, Command cmd, String commandLabel, String[] args);
}

public class CommandHandler implements CommandExecutor {
    private static final HashMap<String, CommandInterface> subCommands = new HashMap<>();

    public void register(String name, CommandInterface command) {
        subCommands.put(name.toLowerCase(), command);
    }

    public boolean exists(String name) {
        return subCommands.containsKey(name.toLowerCase());
    }

    public CommandInterface getExecutor(String name) {
        return subCommands.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if (args.length == 0) {
                getExecutor("help").onCommand((Player) sender, command, label, args);
                return true;
            }

            if (exists(args[0])) {
                getExecutor(args[0].toLowerCase()).onCommand((Player) sender, command, label, args);
            } else {
                getExecutor("help").onCommand((Player) sender, command, label, args);
                return true;
            }
        } else {
            sender.sendMessage(MessageUtils.formatWithPrefix("You must be a player to use this command."));
            return true;
        }
        return true;
    }
}
