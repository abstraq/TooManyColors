package wtf.cmyk.toomanycolors;

import org.bukkit.plugin.java.JavaPlugin;
import wtf.cmyk.toomanycolors.storage.SQLiteProvider;
import wtf.cmyk.toomanycolors.storage.StorageProvider;

public final class TMC extends JavaPlugin {
    private static TMC instance;
    private StorageProvider provider;

    @Override
    public void onEnable() {
        instance = this;
        provider = new SQLiteProvider();
        provider.init();
        getLogger().info("Successfully enabled TooManyColors!");
    }

    @Override
    public void onDisable() {
        instance = null;
        provider.close();
        getLogger().info("Successfully disabled TooManyColors!");
    }

    public static TMC getInstance() {
        return instance;
    }

    public StorageProvider getProvider() {
        return provider;
    }
}
