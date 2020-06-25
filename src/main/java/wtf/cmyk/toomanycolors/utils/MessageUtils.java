package wtf.cmyk.toomanycolors.utils;

import net.md_5.bungee.api.ChatColor;
import wtf.cmyk.toomanycolors.storage.StorageProvider;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class MessageUtils {
    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&',  "&7" +message);
    }

    public static String format(String message, StorageProvider provider, UUID player) {
        HashMap<String, String> placeholderMap = provider.getAllPlaceholders(player.toString());
        String m = message;
        for (String placeholder : placeholderMap.keySet()) {
            m = m.replaceAll(
                    "(?<!\\\\)" + Pattern.quote(placeholder),
                    ChatColor.of(placeholderMap.get(placeholder)).toString()
            );
            m = m.replace( "\\" + placeholder, placeholder);
        }
        return m;
    }

    public static String formatWithPrefix(String message) {
        return ChatColor.translateAlternateColorCodes('&', "&dTMC&8 > &7" + message);
    }
    public static String convertHex(String hex) {
        if(hex.length() == 7) return hex;
        StringBuilder full = new StringBuilder("#");
        for (int i = 1; i < hex.length(); i++)
        {
            full.append(hex, i, i + 1).append(hex, i, i + 1);
        }
        return full.toString();
    }
}
