package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.ConfigurationHelper;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThorHammer {
    public static final List<String> DEFAULT_LORE = Arrays.asList("&6Ability: &eLEFT CLICK",
            "&fStrike &elightning &fon your target");
    public static List<String> lore;
    public static List<String> oldLore;
    public static boolean ignoreArmor;
    public static String name;
    public static double damage;
    public static double fireTicks;

    private ThorHammer(){}

    public static void update(FileConfiguration config){
        lore = ColorUtils.color(ConfigurationHelper.getStringList("ThorHammer.Lore", DEFAULT_LORE));
        name = ColorUtils.color(config.getString("ThorHammer.Name", "&bThor's Hammer"));
        ignoreArmor = config.getBoolean("ThorHammer.IgnoreArmor", false);
        fireTicks = config.getDouble("ThorHammer.FireSeconds", 10) * 20;
        damage = config.getDouble("ThorHammer.Damage", 3.0);

        oldLore = ColorUtils.color(ConfigurationHelper.getStringList("ThorHammer.OldLore",
                Collections.singletonList("&cAbility: incur lightning upon your victims")));
    }
}