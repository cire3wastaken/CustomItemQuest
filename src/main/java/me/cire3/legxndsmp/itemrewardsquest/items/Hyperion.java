package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Hyperion {
    public List<String> lore = new ArrayList<>();
    public List<String> originalLore;
    public String name;
    public double damage;
    public double explosionPower;
    public double explosionRadius;

    public Hyperion(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("Hyperion.Lore");
        this.name = plugin.getConfig().getString("Hyperion.Name");
        this.explosionPower = plugin.getConfig().getDouble("Hyperion.Power");
        this.explosionRadius = plugin.getConfig().getDouble("Hyperion.Radius");
        this.damage = plugin.getConfig().getDouble("Hyperion.Damage");

        for(String s : plugin.getConfig().getStringList("Hyperion.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}
