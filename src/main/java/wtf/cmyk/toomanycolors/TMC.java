package wtf.cmyk.toomanycolors;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.cmyk.toomanycolors.commands.*;
import wtf.cmyk.toomanycolors.listeners.ChatListener;
import wtf.cmyk.toomanycolors.storage.SQLiteProvider;
import wtf.cmyk.toomanycolors.storage.StorageProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class TMC extends JavaPlugin {
    private static TMC instance;
    private StorageProvider provider;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        try {
            ConfigUpdater.update(this, "config.yml", configFile, new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadConfig();
        provider = new SQLiteProvider(this);
        CommandHandler commandHandler = new CommandHandler(this, provider);
        provider.init();

        commandHandler.register("help", new ShortcutCommand());
        commandHandler.register("set", new ShortcutSetCommand());
        commandHandler.register("del", new ShortcutDelCommand());
        commandHandler.register("list", new ShortcutListCommand());
        getCommand("shortcut").setTabCompleter(commandHandler);
        getCommand("shortcut").setExecutor(commandHandler);
        getServer().getPluginManager().registerEvents(new ChatListener(provider), this);
        getLogger().info("Successfully enabled TooManyColors!");
    }

    @Override
    public void onDisable() {
        instance = null;
        provider.close();
        getLogger().info("Successfully disabled TooManyColors!");
    }

    public HashMap<String, String> fetchPlaceholders(String uuid) {
        if(provider.isAccessible()) {
            return provider.getAllPlaceholders(uuid);
        }
        return new HashMap<>();
    }
}
