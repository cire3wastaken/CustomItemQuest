package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class WitchScythe {
    public double secondsOfEffect;
    public List<String> lore;
    public String name;

    public WitchScythe(FileConfiguration configuration){
        this.update(configuration);
    }

    public void update(FileConfiguration fileConfiguration){
        this.lore = fileConfiguration.getStringList("WitchScythe.Lore");
        this.name = fileConfiguration.getString("WitchScythe.Name");
        this.secondsOfEffect = fileConfiguration.getDouble("WitchScythe.Seconds");
    }
}
