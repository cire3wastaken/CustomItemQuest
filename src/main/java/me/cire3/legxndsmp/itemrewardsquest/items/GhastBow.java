package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GhastBow {
    public List<String> lore = new ArrayList<>();
    public List<String> originalLore;
    public String name;
    public double damage;
    public double explosionPower;
    public boolean ignoreArmor;

    public GhastBow(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("GhastBow.Lore");
        this.name = plugin.getConfig().getString("GhastBow.Name");
        this.ignoreArmor = plugin.getConfig().getBoolean("GhastBow.IgnoreArmor");
        this.explosionPower = plugin.getConfig().getDouble("GhastBow.Power");
        this.damage = plugin.getConfig().getDouble("GhastBow.Damage");

        for(String s : plugin.getConfig().getStringList("GhastBow.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }

    public void update(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("GhastBow.Lore");
        this.name = plugin.getConfig().getString("GhastBow.Name");
        this.explosionPower = plugin.getConfig().getDouble("GhastBow.Power");
        this.ignoreArmor = plugin.getConfig().getBoolean("GhastBow.IgnoreArmor");
        this.damage = plugin.getConfig().getDouble("GhastBow.Damage");

        for(String s : plugin.getConfig().getStringList("GhastBow.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}
