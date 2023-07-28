package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
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
        this.lore = ColorUtils.color(config.getStringList("VampireBlade.Lore"));
        this.name = ColorUtils.color(config.getString("VampireBlade.Name"));
        this.toBeHealed = config.getDouble("VampireBlade.Healing");
    }
}
