package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.TMC;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortcutDelCommand implements CommandInterface {
    private final TMC plugin = TMC.getInstance();
    @Override
    public boolean onCommand(Player player, Command command, String label, String[] args) {
        if(player.hasPermission("tmc.command.shortcut.del")) {
            if (args.length != 2) {
                player.sendMessage(MessageUtils.formatWithPrefix("Usage: /shortcut del <placeholder>"));
                return true;
            }
            if (!plugin.getProvider().hasPlaceholder(player.getUniqueId().toString(), args[1])) {
                player.sendMessage(MessageUtils.formatWithPrefix("Placeholder not found."));
                return true;
            }

            String color = plugin.getProvider().getHexColor(player.getUniqueId().toString(), args[1]);
            plugin.getProvider().delPlaceholder(player.getUniqueId().toString(), args[1]);
            player.sendMessage(MessageUtils.format("Deleted placeholder mapping &e" + args[1] + "&7 to &e" + color));
        } else {
            player.sendMessage(MessageUtils.formatWithPrefix("You do not have permission to run this command!"));
        }
        return true;
    }
}
