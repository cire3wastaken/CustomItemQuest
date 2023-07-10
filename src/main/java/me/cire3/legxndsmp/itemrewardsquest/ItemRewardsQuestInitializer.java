package me.cire3.legxndsmp.itemrewardsquest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ItemRewardsQuestInitializer extends JavaPlugin {
    private boolean hasBeenInitialized = false;
    @Override
    public void onEnable(){
        if(hasBeenInitialized){
            Bukkit.getLogger().info(ChatColor.GOLD + "ItemsRewardsQuest has been enabled");
            ItemRewardsQuest.INSTANCE.enable(this);
        } else {
            Bukkit.getLogger().info(ChatColor.GOLD + "ItemsRewardsQuest has been initialized");
            ItemRewardsQuest.INSTANCE.init(this);
            hasBeenInitialized = true;
        }
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info(ChatColor.GOLD + "ItemsRewardsQuest has been disabled");
        ItemRewardsQuest.INSTANCE.disable(this);
    }
}
