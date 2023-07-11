package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ThorHammer {
    public List<String> originalLore;
    public List<String> lore = new ArrayList<>();
    public boolean ignoreArmor;
    public String name;
    public double damage;

    public ThorHammer(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("ThorHammer.Lore");
        this.name = plugin.getConfig().getString("ThorHammer.name");
        this.damage = plugin.getConfig().getDouble("GhastBow.Damage");

        for(String s : plugin.getConfig().getStringList("ThorHammer.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }

    public void update(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("ThorHammer.Lore");
        this.name = plugin.getConfig().getString("ThorHammer.Name");
        this.ignoreArmor = plugin.getConfig().getBoolean("ThorHammer.IgnoreArmor");
        this.damage = plugin.getConfig().getDouble("ThorHammer.Damage");

        for(String s : plugin.getConfig().getStringList("ThorHammer.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}