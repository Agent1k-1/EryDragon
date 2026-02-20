package com.erydevs.commands;

import com.erydevs.EryDragon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand implements TabCompleter {

    private final EryDragon plugin;

    public ReloadCommand(EryDragon plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("erydragon.reload")) {
                    completions.add("reload");
                }
            }
        }

        return completions;
    }
}
