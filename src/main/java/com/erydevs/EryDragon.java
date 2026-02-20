package com.erydevs;

import com.erydevs.blocker.WorldBlocker;
import com.erydevs.commands.DragonCommand;
import com.erydevs.commands.ReloadCommand;
import com.erydevs.config.Configuration;
import com.erydevs.config.MenuConfig;
import com.erydevs.listeners.InventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class EryDragon extends JavaPlugin {

    private Configuration configuration;
    private MenuConfig menuConfig;

    @Override
    public void onEnable() {
        loadConfigs();
        WorldBlocker.init(this);
        registerCommands();
        registerListeners();
    }

    private void loadConfigs() {
        configuration = new Configuration(this);
        configuration.load();

        menuConfig = new MenuConfig(this);
        menuConfig.load();
    }

    private void registerCommands() {
        getCommand("erydragon").setExecutor(new DragonCommand(this));
        getCommand("erydragon").setTabCompleter(new ReloadCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public MenuConfig getMenuConfig() {
        return menuConfig;
    }

    @Override
    public void onDisable() {
    }
}
