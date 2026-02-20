package com.erydevs.listeners;

import com.erydevs.EryDragon;
import com.erydevs.config.MenuConfig;
import com.erydevs.gui.action.ActionMenu;
import com.erydevs.utils.HexUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class InventoryListener implements Listener {

    private final EryDragon plugin;

    public InventoryListener(EryDragon plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        MenuConfig menuConfig = plugin.getMenuConfig();

        if (!event.getView().getTitle().equals(HexUtils.hexToRGB(menuConfig.getMenuName()))) {
            return;
        }

        event.setCancelled(true);

        int slot = event.getRawSlot();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return;
        }

        if (checkButtonClick(player, slot, menuConfig)) {
            return;
        }

        checkEggClick(player, slot, menuConfig, currentItem);
    }

    private boolean checkButtonClick(Player player, int slot, MenuConfig menuConfig) {
        for (int id = 1; id <= 10; id++) {
            if (menuConfig.getButtonSlot(id) == slot) {
                List<String> actions = menuConfig.getButtonActions(id);
                for (String action : actions) {
                    ActionMenu.executeAction(player, action, player.getOpenInventory().getTopInventory());
                }
                return true;
            }
        }
        return false;
    }

    private void checkEggClick(Player player, int slot, MenuConfig menuConfig, ItemStack currentItem) {
        for (int id = 1; id <= 10; id++) {
            String eggName = menuConfig.getEggName(id);
            if (eggName == null || eggName.isEmpty()) {
                continue;
            }

            if (menuConfig.getEggSlot(id) == slot) {
                String eggMaterial = menuConfig.getEggMaterial(id);
                int amount = menuConfig.getEggAmount(id);
                String command = plugin.getConfiguration().getObmenCommand();

                if (hasEggInInventory(player, eggMaterial)) {
                    executeExchange(player, command, amount, eggMaterial);
                } else {
                    player.sendMessage(HexUtils.hexToRGB(plugin.getConfiguration().getEggErrorMessage()));
                }

                break;
            }
        }
    }

    private boolean hasEggInInventory(Player player, String eggMaterial) {
        try {
            Material material = Material.valueOf(eggMaterial);
            PlayerInventory inventory = player.getInventory();

            for (ItemStack item : inventory.getContents()) {
                if (item != null && item.getType() == material) {
                    return true;
                }
            }
        } catch (IllegalArgumentException ignored) {
        }

        return false;
    }

    private void executeExchange(Player player, String command, int amount, String eggMaterial) {
        removeEggFromInventory(player, eggMaterial);

        if (plugin.getConfiguration().isEggSoundEnabled()) {
            try {
                Sound sound = Sound.valueOf(plugin.getConfiguration().getEggSound());
                player.playSound(player.getLocation(), sound,
                        plugin.getConfiguration().getEggSoundVolume(),
                        plugin.getConfiguration().getEggSoundPitch());
            } catch (IllegalArgumentException ignored) {
            }
        }

        String message = plugin.getConfiguration().getEggDragonMessage()
                .replace("%amount%", String.valueOf(amount));
        player.sendMessage(HexUtils.hexToRGB(message));

        String finalCommand = command.replace("%payer_name%", player.getName());
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), finalCommand);
    }

    private void removeEggFromInventory(Player player, String eggMaterial) {
        try {
            Material material = Material.valueOf(eggMaterial);
            ItemStack removeItem = new ItemStack(material, 1);
            player.getInventory().removeItem(removeItem);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
