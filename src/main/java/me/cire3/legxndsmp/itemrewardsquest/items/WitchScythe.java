package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.List;

public class WitchScythe {
    public double secondsOfEffect;
    public List<String> originalLore;
    public List<String> lore;
    public String name;

    public WitchScythe(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("WitchScythe.Lore");
        this.secondsOfEffect = plugin.getConfig().getDouble("WitchScythe.Seconds");
        this.name = plugin.getConfig().getString("WitchScythe.name");

        for(String s : plugin.getConfig().getStringList("WitchScythe.Lore")){
            lore.add(s.toLowerCase());
        }
    }
}
