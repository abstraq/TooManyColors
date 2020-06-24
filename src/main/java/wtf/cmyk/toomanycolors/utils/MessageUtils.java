package wtf.cmyk.toomanycolors.utils;

import org.bukkit.ChatColor;

public class MessageUtils {
    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&',  "&7" +message);
    }
    public static String formatWithPrefix(String message) {
        return ChatColor.translateAlternateColorCodes('&', "&dTMC&8 > &7" + message);
    }
}
