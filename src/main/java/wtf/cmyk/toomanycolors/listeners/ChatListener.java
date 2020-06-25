package wtf.cmyk.toomanycolors.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import wtf.cmyk.toomanycolors.TMC;

import java.util.HashMap;

public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        HashMap<String, String> placeholderMap = TMC.getInstance().getProvider().getAllPlaceholders(e.getPlayer().getUniqueId().toString());
        for (String placeholder : placeholderMap.keySet())

            message = message.replace(placeholder, ChatColor.of(placeholderMap.get(placeholder)).toString());
        e.setMessage(message);
    }
}
