package wtf.cmyk.toomanycolors.storage;

import org.bukkit.entity.Player;

public abstract class StorageProvider {
    public abstract void init();

    public abstract void setPlaceholder(String uuid, String placeholder, String hexColor);

    public abstract String getHexColor(String uuid, String placeholder);

    public abstract void close();
}
