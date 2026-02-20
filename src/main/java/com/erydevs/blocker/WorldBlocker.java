package com.erydevs.blocker;

import com.erydevs.EryDragon;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldBlocker {

    private static EryDragon plugin;

    public static void init(EryDragon eryDragon) {
        plugin = eryDragon;
    }

    public static boolean canOpenMenu(Player player) {
        if (player.hasPermission("dragon.unblocker")) {
            return true;
        }

        String worldName = player.getWorld().getName();
        return !plugin.getConfiguration().getBlockedWorlds().contains(worldName);
    }
}
