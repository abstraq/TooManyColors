package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.TMC;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

interface CommandInterface {
    boolean onCommand(Player player, Command cmd, String commandLabel, String[] args);
}

public class CommandHandler implements TabExecutor {
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();
        if(sender instanceof Player) {
            if (args.length == 1) {
                List<String> sub = Arrays.asList("help", "set", "del", "list");
                if(args[0].equals("")) {
                    suggestions.addAll(sub);
                } else {
                    suggestions.addAll(sub.stream().filter(s -> s.startsWith(args[0].toLowerCase())).collect(Collectors.toList()));
                }
                return suggestions;
            }

            if (args.length > 0 && exists(args[0])) {
                HashMap<String, String> placeholderMap = TMC.getInstance().getProvider().getAllPlaceholders(((Player) sender).getUniqueId().toString());
                if (args[0].equalsIgnoreCase("del")) {
                    suggestions.addAll(placeholderMap.keySet());
                    return suggestions;
                }
            }
        }
        return suggestions;
    }
}
