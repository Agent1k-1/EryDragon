package com.erydevs.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class Configuration {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public Configuration(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reload() {
        load();
    }

    public String getObmenCommand() {
        return config.getString("settings.obmen-command");
    }

    public boolean isEggSoundEnabled() {
        return config.getBoolean("egg-sound.enabled");
    }

    public String getEggSound() {
        return config.getString("egg-sound.sound");
    }

    public float getEggSoundVolume() {
        return (float) config.getDouble("egg-sound.volume");
    }

    public float getEggSoundPitch() {
        return (float) config.getDouble("egg-sound.pitch");
    }

    public List<String> getBlockedWorlds() {
        return config.getStringList("world-blocker");
    }

    public String getEggDragonMessage() {
        return config.getString("message.egg-dragon", "");
    }

    public String getEggErrorMessage() {
        return config.getString("message.egg-error", "");
    }

    public String getReloadConfigMessage() {
        return config.getString("message.reload-config", "");
    }

    public String getNoPermissionMessage() {
        return config.getString("message.no-permission", "");
    }

    public String getWorldBlockerMessage() {
        return config.getString("message.world-blocker", "");
    }

    public String getOnlyPlayerMessage() {
        return config.getString("message.only-player", "");
    }
}
