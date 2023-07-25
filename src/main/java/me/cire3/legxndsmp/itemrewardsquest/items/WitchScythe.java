package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
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
        this.lore = ColorUtils.color(fileConfiguration.getStringList("WitchScythe.Lore"));
        this.name = ColorUtils.color(fileConfiguration.getString("WitchScythe.Name"));
        this.secondsOfEffect = fileConfiguration.getDouble("WitchScythe.Seconds");
    }
}
