package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GhastBow {
    public List<String> loreConfig = new ArrayList<>();
    public List<String> originalLoreConfig;
    public String nameConfig;
    public double damageConfig;
    public double explosionPowerConfig;
    public boolean ignoreArmor;
    public boolean explosion;

    public GhastBow(Plugin plugin){
        this.originalLoreConfig = plugin.getConfig().getStringList("GhastBow.Lore");
        this.nameConfig = plugin.getConfig().getString("GhastBow.Name");
        this.ignoreArmor = plugin.getConfig().getBoolean("GhastBow.IgnoreArmor");
        this.explosionPowerConfig = plugin.getConfig().getDouble("GhastBow.Power");
        this.damageConfig = plugin.getConfig().getDouble("GhastBow.Damage");
        this.explosion = plugin.getConfig().getBoolean("GhastBow.Explosion");
        this.loreConfig = new ArrayList<>();

        for(String s : plugin.getConfig().getStringList("GhastBow.Lore")){
            this.loreConfig.add(s.toLowerCase());
        }
    }

    public void update(Plugin plugin){
        this.originalLoreConfig = plugin.getConfig().getStringList("GhastBow.Lore");
        this.nameConfig = plugin.getConfig().getString("GhastBow.Name");
        this.explosionPowerConfig = plugin.getConfig().getDouble("GhastBow.Power");
        this.ignoreArmor = plugin.getConfig().getBoolean("GhastBow.IgnoreArmor");
        this.explosion = plugin.getConfig().getBoolean("GhastBow.Explosion");
        this.damageConfig = plugin.getConfig().getDouble("GhastBow.Damage");
        this.loreConfig = new ArrayList<>();

        for(String s : plugin.getConfig().getStringList("GhastBow.Lore")){
            this.loreConfig.add(s.toLowerCase());
        }
    }
}
