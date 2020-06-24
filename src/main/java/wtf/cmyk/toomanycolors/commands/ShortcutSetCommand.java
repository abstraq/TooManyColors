package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.TMC;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortcutSetCommand implements CommandInterface {
    private final TMC plugin = TMC.getInstance();
    @Override
    public boolean onCommand(Player player, Command command, String label, String[] args) {
        if(player.hasPermission("tmc.command.shortcut.add")) {
            if (args.length != 3) {
                player.sendMessage(MessageUtils.formatWithPrefix("Usage: /shortcut add <placeholder> <#HEXCODE>"));
                return true;
            }
            if (plugin.getConfig().getStringList("blacklistedColors").contains(args[2])) {
                player.sendMessage(MessageUtils.formatWithPrefix("This color is blacklisted by administration."));
                return true;
            }
            if(args[1].matches("&([a-fA-F]|[0-9]|[k-oK-O]|r|R)")) {
                player.sendMessage(MessageUtils.formatWithPrefix("This placeholder is reserved."));
                return true;
            }
            int shortcutLimit = plugin.getConfig().getInt("defaultShortcutLimit");
            if( shortcutLimit != -1 && !plugin.getProvider().hasPlaceholder(player.getUniqueId().toString(), args[1])) {
                if(plugin.getProvider().getTotalPlaceholders(player.getUniqueId().toString()) == shortcutLimit) {
                    player.sendMessage(MessageUtils.formatWithPrefix("You have reached the placeholder limit, Overwrite or delete one of your existing placeholders."));
                    return true;
                }
            }
            Pattern pattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
            Matcher matcher = pattern.matcher(args[2]);
            if (!matcher.find()) {
                player.sendMessage(MessageUtils.formatWithPrefix("Not a valid hex color code, you must prefix your hex colors with #."));
                return true;
            }

            plugin.getProvider().setPlaceholder(player.getUniqueId().toString(), args[1], matcher.group());
            player.sendMessage(MessageUtils.format("Created placeholder mapping &e" + args[1] + "&7 to &e" + args[2]));

        } else {
            player.sendMessage(MessageUtils.formatWithPrefix("You do not have permission to run this command!"));
        }
        return true;
    }
}
