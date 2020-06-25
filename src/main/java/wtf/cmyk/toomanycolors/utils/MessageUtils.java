package wtf.cmyk.toomanycolors.utils;

import net.md_5.bungee.api.ChatColor;
import wtf.cmyk.toomanycolors.storage.StorageProvider;

import java.util.HashMap;
import java.util.UUID;

public class MessageUtils {
    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&',  "&7" +message);
    }

    public static String format(String message, StorageProvider provider, UUID player) {
        HashMap<String, String> placeholderMap = provider.getAllPlaceholders(player.toString());
        String m = message;
        for (String placeholder : placeholderMap.keySet()) {
            m = m.replace(
                    placeholder,
                    ChatColor.of(placeholderMap.get(placeholder)).toString()
            );
        }
        return m;
    }

    public static String formatWithPrefix(String message) {
        return ChatColor.translateAlternateColorCodes('&', "&dTMC&8 > &7" + message);
    }
}
