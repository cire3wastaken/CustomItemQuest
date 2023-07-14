package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class VampireBlade {
    public List<String> lore;
    public List<String> originalLore;
    public double toBeHealed;
    public String name;

    public VampireBlade(FileConfiguration config){
        this.update(config);
    }

    public void update(FileConfiguration config){
        this.originalLore = config.getStringList("VampireBlade.Lore");
        this.name = config.getString("VampireBlade.Name");
        this.toBeHealed = config.getDouble("VampireBlade.Healing");
        this.lore = new ArrayList<>();

        for(String s : config.getStringList("VampireBlade.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}
