package com.erydevs.gui.action;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ActionMenu {

    public static void executeAction(Player player, String action, Inventory inventory) {
        if (action.equalsIgnoreCase("[close]")) {
            player.closeInventory();
        }
    }
}
