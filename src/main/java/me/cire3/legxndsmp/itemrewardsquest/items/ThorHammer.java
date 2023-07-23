package me.cire3.legxndsmp.itemrewardsquest.items;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ThorHammer {
    public List<String> lore;
    public boolean ignoreArmor;
    public String name;
    public double damage;
    public double fireTicks;

    public ThorHammer(FileConfiguration config){
        this.update(config);
    }

    public void update(FileConfiguration config){
        this.lore = config.getStringList("ThorHammer.Lore");
        this.name = config.getString("ThorHammer.Name");
        this.ignoreArmor = config.getBoolean("ThorHammer.IgnoreArmor");
        this.fireTicks = config.getDouble("ThorHammer.FireSeconds") * 20;
        this.damage = config.getDouble("ThorHammer.Damage");
    }
}