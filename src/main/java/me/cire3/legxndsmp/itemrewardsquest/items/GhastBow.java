package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.List;

public class GhastBow {
    public List<String> lore;
    public List<String> originalLore;
    public String name;
    public double damage;
    public double explosionPower;

    public GhastBow(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("GhastBow.Lore");
        this.name = plugin.getConfig().getString("GhastBow.Name");
        this.explosionPower = plugin.getConfig().getDouble("GhastBow.Power");
        this.damage = plugin.getConfig().getDouble("GhastBow.Damage");

        for(String s : plugin.getConfig().getStringList("GhastBow.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}
