package wtf.cmyk.toomanycolors.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import wtf.cmyk.toomanycolors.storage.StorageProvider;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

public class ReplacementListener implements Listener {
    private final StorageProvider provider;

    public ReplacementListener(StorageProvider storageProvider) {
        this.provider = storageProvider;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setMessage(MessageUtils.format(e.getMessage(), provider, e.getPlayer().getUniqueId()));
    }
}
