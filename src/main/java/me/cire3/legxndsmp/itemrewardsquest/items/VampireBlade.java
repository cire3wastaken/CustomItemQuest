package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class VampireBlade {
    public List<String> lore;
    public List<String> originalLore;
    public double toBeHealed;
    public String name;

    public VampireBlade(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("VampireBlade.Lore");
        this.toBeHealed = plugin.getConfig().getDouble("VampireBlade.Healing");
        this.name = plugin.getConfig().getString("VampireBlade.Name");
        this.lore = new ArrayList<>();

        for(String s : plugin.getConfig().getStringList("VampireBlade.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }

    public void update(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("VampireBlade.Lore");
        this.name = plugin.getConfig().getString("VampireBlade.Name");
        this.toBeHealed = plugin.getConfig().getDouble("VampireBlade.Healing");
        this.lore = new ArrayList<>();

        for(String s : plugin.getConfig().getStringList("VampireBlade.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}
