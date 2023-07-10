package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class VampireBlade {
    public List<String> lore = new ArrayList<>();
    public List<String> originalLore;
    public double toBeHealed;
    public String name;

    public VampireBlade(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("VampireBlade.Lore");
        this.toBeHealed = plugin.getConfig().getDouble("VampireBlade.Healing");
        this.name = plugin.getConfig().getString("VampireBlade.Name");

        for(String s : plugin.getConfig().getStringList("VampireBlade.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}
