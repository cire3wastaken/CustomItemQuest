package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.ConfigurationHelper;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hyperion {
    public static final List<String> DEFAULT_LORE = Arrays.asList("&6Item Ability: &eRIGHT CLICK",
            "&fTeleports &a10 blocks &fahead of you. Then implode dealing damage to nearby enemies.",
            "&fAlso applies X% of this item's final damage as an &6Absorption &fshield.",
            "&6Cooldown: 30s");
    public static List<String> lore;
    public static List<String> oldLore;
    public static String name;
    public static double damage;
    public static double explosionPower;
    public static double explosionRadius;
    public static double cooldownSeconds;
    public static double percentage;
    public static double shieldDurationTicks;
    public static boolean ignoreArmor;

    private Hyperion(){}

    public static void update(FileConfiguration config){
        lore = ColorUtils.color(ConfigurationHelper.getStringList("Hyperion.Lore", DEFAULT_LORE));
        name = ColorUtils.color(config.getString("Hyperion.Name", "&dHyperion"));
        explosionPower = config.getDouble("Hyperion.Power", 3.0);
        explosionRadius = config.getDouble("Hyperion.Radius", 6.0);
        damage = config.getDouble("Hyperion.Damage",  10.0);
        cooldownSeconds = config.getDouble("Hyperion.Cooldown", 30.0);
        percentage = config.getDouble("Hyperion.Amount", 0.25);
        ignoreArmor = config.getBoolean("Hyperion.IgnoreArmor", false);
        shieldDurationTicks = config.getDouble("Hyperion.Duration", 5) * 20;

        oldLore = ColorUtils.color(ConfigurationHelper.getStringList("Hyperion.OldLore",
                Collections.singletonList("temp")));
    }
}
