package com.erydevs.commands;

import com.erydevs.EryDragon;
import com.erydevs.blocker.WorldBlocker;
import com.erydevs.gui.EggGUI;
import com.erydevs.utils.HexUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DragonCommand implements CommandExecutor {

    private final EryDragon plugin;

    public DragonCommand(EryDragon plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(HexUtils.hexToRGB(plugin.getConfiguration().getOnlyPlayerMessage()));
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("erydragon.reload")) {
                player.sendMessage(HexUtils.hexToRGB(plugin.getConfiguration().getNoPermissionMessage()));
                return true;
            }

            plugin.getConfiguration().reload();
            plugin.getMenuConfig().reload();
            player.sendMessage(HexUtils.hexToRGB(plugin.getConfiguration().getReloadConfigMessage()));
            return true;
        }

        if (!WorldBlocker.canOpenMenu(player)) {
            player.sendMessage(HexUtils.hexToRGB(plugin.getConfiguration().getWorldBlockerMessage()));
            return true;
        }

        EggGUI.openGUI(player, plugin);
        return true;
    }
}
