package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class VampireBlade {
    public List<String> lore;
    public double toBeHealed;
    public String name;

    public VampireBlade(FileConfiguration config){
        this.update(config);
    }

    public void update(FileConfiguration config){
        this.lore = config.getStringList("VampireBlade.Lore");
        this.name = config.getString("VampireBlade.Name");
        this.toBeHealed = config.getDouble("VampireBlade.Healing");
    }
}
