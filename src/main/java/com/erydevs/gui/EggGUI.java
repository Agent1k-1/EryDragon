package com.erydevs.gui;

import com.erydevs.EryDragon;
import com.erydevs.config.MenuConfig;
import com.erydevs.utils.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class EggGUI {

    public static void openGUI(Player player, EryDragon plugin) {
        MenuConfig menuConfig = plugin.getMenuConfig();

        String menuName = HexUtils.hexToRGB(menuConfig.getMenuName());
        int menuSize = menuConfig.getMenuSize();

        Inventory inventory = Bukkit.createInventory(null, menuSize, menuName);

        fillPanel(inventory, menuConfig);
        fillButtons(inventory, menuConfig);
        fillEggs(inventory, menuConfig);

        player.openInventory(inventory);
    }

    private static void fillPanel(Inventory inventory, MenuConfig menuConfig) {
        List<Map<String, Object>> panels = menuConfig.getPanels();

        for (Map<String, Object> panel : panels) {
            String panelName = HexUtils.hexToRGB(menuConfig.getPanelName(panel));
            List<String> panelLore = menuConfig.getPanelLore(panel);
            String panelMaterial = menuConfig.getPanelMaterial(panel);
            List<Integer> panelSlots = menuConfig.getPanelSlots(panel);

            ItemStack panelItem = createItem(panelMaterial, panelName, panelLore);

            if (panelItem != null) {
                for (int slot : panelSlots) {
                    inventory.setItem(slot, panelItem.clone());
                }
            }
        }
    }

    private static void fillButtons(Inventory inventory, MenuConfig menuConfig) {
        for (int id = 1; id <= 10; id++) {
            String buttonName = menuConfig.getButtonName(id);
            if (buttonName == null || buttonName.isEmpty()) {
                continue;
            }

            List<String> buttonLore = menuConfig.getButtonLore(id);
            String buttonMaterial = menuConfig.getButtonMaterial(id);
            int buttonSlot = menuConfig.getButtonSlot(id);

            ItemStack buttonItem = createItem(buttonMaterial, HexUtils.hexToRGB(buttonName), buttonLore);
            inventory.setItem(buttonSlot, buttonItem);
        }
    }

    private static void fillEggs(Inventory inventory, MenuConfig menuConfig) {
        for (int id = 1; id <= 10; id++) {
            String eggName = menuConfig.getEggName(id);
            if (eggName == null || eggName.isEmpty()) {
                continue;
            }

            List<String> eggLore = menuConfig.getEggLore(id);
            String eggMaterial = menuConfig.getEggMaterial(id);
            int eggSlot = menuConfig.getEggSlot(id);
            int eggAmount = menuConfig.getEggAmount(id);

            ItemStack eggItem = createItem(eggMaterial, HexUtils.hexToRGB(eggName), eggLore);
            if (eggItem != null && eggAmount > 0) {
                eggItem.setAmount(1);
                ItemMeta meta = eggItem.getItemMeta();
                if (meta != null && eggLore != null && !eggLore.isEmpty()) {
                    List<String> coloredLore = eggLore.stream()
                            .map(s -> HexUtils.hexToRGB(s.replace("%amount%", String.valueOf(eggAmount))))
                            .toList();
                    meta.setLore(coloredLore);
                    eggItem.setItemMeta(meta);
                }
                inventory.setItem(eggSlot, eggItem);
            }
        }
    }

    private static ItemStack createItem(String materialName, String displayName, List<String> lore) {
        try {
            Material material = Material.valueOf(materialName);
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(displayName);
                List<String> coloredLore = lore.stream()
                        .map(HexUtils::hexToRGB)
                        .toList();
                meta.setLore(coloredLore);
                item.setItemMeta(meta);
            }

            return item;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
