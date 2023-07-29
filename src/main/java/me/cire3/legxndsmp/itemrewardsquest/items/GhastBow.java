package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.ConfigurationHelper;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GhastBow {
    public static final List<String> DEFAULT_LORE = Arrays.asList("&6Ability: &eRIGHT CLICK", "&fCreate an &cexplosion &fupon where your &7arrow lands");
    public static List<String> lore;
    public static List<String> oldLore;
    public static String nameConfig;
    public static double damageConfig;
    public static double explosionPowerConfig;
    public static boolean ignoreArmor;
    public static boolean explosion;

    private GhastBow(){}

    public static void update(FileConfiguration configuration){
        lore = ColorUtils.color(ConfigurationHelper.getStringList("GhastBow.Lore", DEFAULT_LORE));
        nameConfig = ColorUtils.color(configuration.getString("GhastBow.Name", "&3Ghast Bow"));
        explosionPowerConfig = configuration.getDouble("GhastBow.Power", 1.0);
        ignoreArmor = configuration.getBoolean("GhastBow.IgnoreArmor", false);
        explosion = configuration.getBoolean("GhastBow.Explosion", false);
        damageConfig = configuration.getDouble("GhastBow.Damage", 5.0);

        oldLore = ColorUtils.color(ConfigurationHelper.getStringList("GhastBow.OldLore",
                Collections.singletonList("&eAbility: create explosions upon whom your arrow lands on")));
    }
}
