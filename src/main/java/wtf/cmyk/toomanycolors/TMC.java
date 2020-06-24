package wtf.cmyk.toomanycolors;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.cmyk.toomanycolors.commands.*;
import wtf.cmyk.toomanycolors.storage.SQLiteProvider;
import wtf.cmyk.toomanycolors.storage.StorageProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public final class TMC extends JavaPlugin {
    private static TMC instance;
    private StorageProvider provider;
    private CommandHandler commandHandler;

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
        provider = new SQLiteProvider();
        commandHandler = new CommandHandler();
        provider.init();

        commandHandler.register("help", new ShortcutCommand());
        commandHandler.register("set", new ShortcutSetCommand());
        commandHandler.register("del", new ShortcutDelCommand());
        commandHandler.register("list", new ShortcutListCommand());
        getCommand("shortcut").setExecutor(commandHandler);
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

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
