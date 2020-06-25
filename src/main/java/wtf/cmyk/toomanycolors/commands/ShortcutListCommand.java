package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.TMC;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

import java.util.HashMap;

public class ShortcutListCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandHandler handler,Player player, Command cmd, String commandLabel, String[] args) {
        HashMap<String, String> placeholderMap = handler.provider.getAllPlaceholders(player.getUniqueId().toString());
        if(placeholderMap.isEmpty()) {
            player.sendMessage(MessageUtils.formatWithPrefix("No placeholders :o"));
            return true;
        }
        placeholderMap.forEach((placeholder, hexCode) -> {
            player.sendMessage(MessageUtils.format(String.format("&f- &d%s &8-&e %s", placeholder, hexCode)));
        });
        return true;
    }
}
