package com.ashkiano.timedrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardCommandExecutor implements CommandExecutor {
    private final TimedRewards plugin;

    public RewardCommandExecutor(TimedRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();
        long now = System.currentTimeMillis();
        long lastClaimed = plugin.getConfig().getLong("rewards." + playerName + ".lastClaimed", 0);
        long rewardInterval = plugin.getConfig().getLong("reward-interval-hours", 24) * 3600000; // 1 hour = 3600000 ms

        if (now - lastClaimed < rewardInterval) {
            player.sendMessage("You cannot claim your reward yet.");
            return true;
        }

        String rewardCommand = plugin.getConfig().getString("reward-command").replace("%player%", playerName);
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), rewardCommand);
        player.sendMessage("You have claimed your reward.");

        plugin.getConfig().set("rewards." + playerName + ".lastClaimed", now);
        plugin.saveConfig();

        return true;
    }
}