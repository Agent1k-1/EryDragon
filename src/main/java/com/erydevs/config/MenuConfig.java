package com.erydevs.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuConfig {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public MenuConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        configFile = new File(plugin.getDataFolder(), "dragon-gui.yml");

        if (!configFile.exists()) {
            plugin.saveResource("dragon-gui.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reload() {
        load();
    }

    public String getMenuName() {
        return config.getString("name", "");
    }

    public int getMenuSize() {
        return config.getInt("size", 54);
    }

    public List<Map<String, Object>> getPanels() {
        List<?> panelsList = config.getList("panels");
        if (panelsList != null) {
            List<Map<String, Object>> panels = new ArrayList<>();
            for (Object obj : panelsList) {
                if (obj instanceof Map) {
                    panels.add((Map<String, Object>) obj);
                }
            }
            return panels;
        }
        return new ArrayList<>();
    }

    public String getPanelMaterial(Map<String, Object> panel) {
        Object material = panel.get("material");
        return material != null ? material.toString() : "";
    }

    public String getPanelName(Map<String, Object> panel) {
        Object name = panel.get("name");
        return name != null ? name.toString() : "";
    }

    public List<String> getPanelLore(Map<String, Object> panel) {
        Object lore = panel.get("lore");
        if (lore instanceof List) {
            List<String> loreList = new ArrayList<>();
            for (Object obj : (List<?>) lore) {
                loreList.add(obj.toString());
            }
            return loreList;
        }
        return new ArrayList<>();
    }

    public List<Integer> getPanelSlots(Map<String, Object> panel) {
        Object slots = panel.get("slots");
        if (slots instanceof List) {
            List<Integer> slotsList = new ArrayList<>();
            for (Object obj : (List<?>) slots) {
                try {
                    slotsList.add(Integer.parseInt(obj.toString()));
                } catch (NumberFormatException ignored) {
                }
            }
            return slotsList;
        }
        return new ArrayList<>();
    }

    public String getButtonName(int id) {
        return config.getString("knops-settings." + id + ".name", "");
    }

    public List<String> getButtonLore(int id) {
        return config.getStringList("knops-settings." + id + ".lore");
    }

    public String getButtonMaterial(int id) {
        return config.getString("knops-settings." + id + ".material", "");
    }

    public int getButtonSlot(int id) {
        return config.getInt("knops-settings." + id + ".slot");
    }

    public List<String> getButtonActions(int id) {
        return config.getStringList("knops-settings." + id + ".action");
    }

    public String getEggName(int id) {
        String name = config.getString("egg-settings." + id + ".name");
        return name != null ? name : "";
    }

    public List<String> getEggLore(int id) {
        List<String> lore = config.getStringList("egg-settings." + id + ".lore");
        return lore != null ? lore : new ArrayList<>();
    }

    public int getEggSlot(int id) {
        return config.getInt("egg-settings." + id + ".slot", 0);
    }

    public int getEggAmount(int id) {
        return config.getInt("egg-settings." + id + ".amount", 0);
    }

    public String getEggMaterial(int id) {
        String material = config.getString("egg-settings." + id + ".material");
        return material != null ? material : "";
    }
}
