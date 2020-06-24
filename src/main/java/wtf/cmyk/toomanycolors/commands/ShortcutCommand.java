package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

public class ShortcutCommand implements CommandInterface {

    @Override
    public boolean onCommand(Player player, Command cmd, String commandLabel, String[] args) {
        player.sendMessage(MessageUtils.format("&f- &7shortcut &dset &e<placeholder> <#HEXCODE>"));
        player.sendMessage(MessageUtils.format("&f- &7shortcut &ddel &e<placeholder>"));
        player.sendMessage(MessageUtils.format("&f- &7shortcut &dlist"));
        return true;
    }
}
