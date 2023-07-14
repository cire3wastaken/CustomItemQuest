package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ThorHammer {
    public List<String> originalLore;
    public List<String> lore = new ArrayList<>();
    public boolean ignoreArmor;
    public String name;
    public double damage;
    public double fireTicks;

    public ThorHammer(FileConfiguration config){
        this.update(config);
    }

    public void update(FileConfiguration config){
        this.originalLore = config.getStringList("ThorHammer.Lore");
        this.name = config.getString("ThorHammer.Name");
        this.ignoreArmor = config.getBoolean("ThorHammer.IgnoreArmor");
        this.fireTicks = config.getDouble("ThorHammer.FireSeconds") * 20;
        this.damage = config.getDouble("ThorHammer.Damage");
        this.lore = new ArrayList<>();

        for(String s : ColorUtils.color(config.getStringList("ThorHammer.Lore"))){
            this.lore.add(s.toLowerCase());
        }
    }
}