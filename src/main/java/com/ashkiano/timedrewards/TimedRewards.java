package com.ashkiano.timedrewards;

import org.bukkit.plugin.java.JavaPlugin;

public final class TimedRewards extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("reward").setExecutor(new RewardCommandExecutor(this)); // Příkaz "reward" můžete změnit podle vašich preferencí
        Metrics metrics = new Metrics(this, 21241);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
