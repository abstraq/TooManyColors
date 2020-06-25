package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

public class ShortcutDelCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandHandler handler, Player player, Command command, String label, String[] args) {
        if(player.hasPermission("tmc.command.shortcut.del")) {
            if (args.length != 2) {
                player.sendMessage(MessageUtils.formatWithPrefix("Usage: /shortcut del <placeholder>"));
                return true;
            }
            if (!handler.provider.hasPlaceholder(player.getUniqueId().toString(), args[1])) {
                player.sendMessage(MessageUtils.formatWithPrefix("Placeholder not found."));
                return true;
            }

            String color = handler.provider.getHexColor(player.getUniqueId().toString(), args[1]);
            handler.provider.delPlaceholder(player.getUniqueId().toString(), args[1]);
            player.sendMessage(MessageUtils.format("Deleted placeholder mapping &e" + args[1] + "&7 to &e" + color));
        } else {
            player.sendMessage(MessageUtils.formatWithPrefix("You do not have permission to run this command!"));
        }
        return true;
    }
}
