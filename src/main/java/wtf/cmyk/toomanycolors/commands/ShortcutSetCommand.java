package wtf.cmyk.toomanycolors.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class ShortcutSetCommand implements CommandInterface {
    @Override
    public boolean onCommand(CommandHandler handler, Player player, Command command, String label, String[] args) {
        if(player.hasPermission("tmc.command.shortcut.set")) {
            if (args.length != 3) {
                player.sendMessage(MessageUtils.formatWithPrefix("Usage: /shortcut set <placeholder> <#HEXCODE>"));
                return true;
            }
            if (handler.plugin.getConfig().getStringList("blacklistedColors").contains(args[2])) {
                player.sendMessage(MessageUtils.formatWithPrefix("This color is blacklisted by administration."));
                return true;
            }
            if(args[1].matches("&([a-fA-F]|[0-9]|[k-oK-O]|r|R|x|X)")) {
                player.sendMessage(MessageUtils.formatWithPrefix("This placeholder is reserved."));
                return true;
            }
            int shortcutLimit = handler.plugin.getConfig().getInt("defaultShortcutLimit");

            Pattern pattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
            Matcher matcher = pattern.matcher(args[2]);
            if (!matcher.find()) {
                player.sendMessage(MessageUtils.formatWithPrefix("Not a valid hex color code, you must prefix your hex colors with #."));
                return true;
            }
            if(!player.hasPermission("tmc.command.shortcut.set.unlimited")) {
                ArrayList<Integer> nodes = new ArrayList<>();
                for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
                    if(permission.getPermission().startsWith("tmc.command.shortcut.set.")) {
                        try {
                            nodes.add(parseInt(permission.getPermission().replace("tmc.command.shortcut.set.", "")));
                        } catch (NumberFormatException ignored) { }
                    }
                }
                if(!nodes.isEmpty()) {
                    nodes.sort(Collections.reverseOrder());
                    int maxHomes = nodes.get(0);
                    if (handler.provider.getTotalPlaceholders(player.getUniqueId().toString()) == maxHomes) {
                        player.sendMessage(MessageUtils.formatWithPrefix("You have reached the placeholder limit, Overwrite or delete one of your existing placeholders."));
                        return true;
                    }
                } else {
                    if (shortcutLimit != -1 && !handler.provider.hasPlaceholder(player.getUniqueId().toString(), args[1])) {
                        if (handler.provider.getTotalPlaceholders(player.getUniqueId().toString()) == shortcutLimit) {
                            player.sendMessage(MessageUtils.formatWithPrefix("You have reached the placeholder limit, Overwrite or delete one of your existing placeholders."));
                            return true;
                        }
                    }
                }
            }
            handler.provider.setPlaceholder(player.getUniqueId().toString(), args[1], matcher.group());
            player.sendMessage(MessageUtils.format("Created placeholder mapping &e" + args[1] + "&7 to &e" + args[2]));

        } else {
            player.sendMessage(MessageUtils.formatWithPrefix("You do not have permission to run this command!"));
        }
        return true;
    }
}
