package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Hyperion {
    public List<String> lore;
    public String name;
    public double damage;
    public double explosionPower;
    public double explosionRadius;
    public double cooldownSeconds;
    public double percentage;
    public double shieldDurationTicks;
    public boolean ignoreArmor;

    public Hyperion(FileConfiguration config){
        this.update(config);
    }

    public void update(FileConfiguration config){
        this.lore = ColorUtils.color(config.getStringList("Hyperion.Lore"));
        this.name = ColorUtils.color(config.getString("Hyperion.Name"));
        this.explosionPower = config.getDouble("Hyperion.Power");
        this.explosionRadius = config.getDouble("Hyperion.Radius");
        this.damage = config.getDouble("Hyperion.Damage");
        this.cooldownSeconds = config.getDouble("Hyperion.Cooldown");
        this.percentage = config.getDouble("Hyperion.Amount");
        this.ignoreArmor = config.getBoolean("Hyperion.IgnoreArmor");
    }
}
