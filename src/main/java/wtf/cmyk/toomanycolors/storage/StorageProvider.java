package wtf.cmyk.toomanycolors.storage;

import org.bukkit.entity.Player;
import wtf.cmyk.toomanycolors.TMC;

import java.util.HashMap;

public abstract class StorageProvider {

    public StorageProvider(TMC instance) { }

    public abstract boolean isAccessible();

    public abstract void init();

    public abstract Boolean hasPlaceholder(String uuid, String placeholder);

    public abstract void setPlaceholder(String uuid, String placeholder, String hexColor);

    public abstract void delPlaceholder(String uuid, String placeholder);

    public abstract String getHexColor(String uuid, String placeholder);

    public abstract HashMap<String, String> getAllPlaceholders(String uuid);

    public abstract Integer getTotalPlaceholders(String uuid);

    public abstract void close();
}
