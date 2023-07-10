package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.List;

public class GhastBow {
    public List<String> lore;
    public List<String> originalLore;
    public String name;
    public double explosionPower;

    public GhastBow(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("GhastBow.Lore");
        this.name = plugin.getConfig().getString("GhastBow.Name");
        this.explosionPower = plugin.getConfig().getDouble("GhastBow.Power");

        for(String s : plugin.getConfig().getStringList("GhastBow.Lore")){
            lore.add(s.toLowerCase());
        }
    }
}
