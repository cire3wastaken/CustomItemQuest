package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.List;

public class ThorHammer {
    public List<String> originalLore;
    public List<String> lore;
    public String name;

    public ThorHammer(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("ThorHammer.Lore");
        this.name = plugin.getConfig().getString("ThorHammer.name");

        for(String s : plugin.getConfig().getStringList("ThorHammer.Lore")){
            lore.add(s.toLowerCase());
        }
    }
}