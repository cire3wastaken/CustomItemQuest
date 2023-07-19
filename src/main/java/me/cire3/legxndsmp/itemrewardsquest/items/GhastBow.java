package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GhastBow {
    public List<String> loreConfig = new ArrayList<>();
    public List<String> originalLoreConfig;
    public String nameConfig;
    public double damageConfig;
    public double explosionPowerConfig;
    public boolean ignoreArmor;
    public boolean explosion;

    public GhastBow(FileConfiguration configuration){
        this.update(configuration);
    }

    public void update(FileConfiguration configuration){
        this.originalLoreConfig = configuration.getStringList("GhastBow.Lore");
        this.nameConfig = configuration.getString("GhastBow.Name");
        this.explosionPowerConfig = configuration.getDouble("GhastBow.Power");
        this.ignoreArmor = configuration.getBoolean("GhastBow.IgnoreArmor");
        this.explosion = configuration.getBoolean("GhastBow.Explosion");

       this.damageConfig = configuration.getDouble("GhastBow.Damage");
        this.loreConfig = new ArrayList<>();

        for(String s : ColorUtils.color(configuration.getStringList("GhastBow.Lore"))){
            this.loreConfig.add(s.toLowerCase());
        }
    }
}
