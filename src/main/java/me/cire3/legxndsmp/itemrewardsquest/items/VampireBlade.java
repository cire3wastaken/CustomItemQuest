package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.ConfigurationHelper;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VampireBlade {
    public static final List<String> DEFAULT_LORE = Arrays.asList("&6Ability: &eLEFT CLICK",
            "&aHeal &fhalf the &cfinal damage &fyou do to others.");
    public static List<String> lore;
    public static List<String> oldLore;
    public static double toBeHealed;
    public static String name;

    private VampireBlade(){}

    public static void update(FileConfiguration config){
        lore = ColorUtils.color(ConfigurationHelper.getStringList("VampireBlade.Lore", DEFAULT_LORE));
        name = ColorUtils.color(config.getString("VampireBlade.Name", "&4Vampire Blade"));
        toBeHealed = config.getDouble("VampireBlade.Healing", 0.5);

        oldLore = ColorUtils.color(ConfigurationHelper.getStringList("VampireBlade.OldLore",
                Collections.singletonList("&6Ability: gain half as much HP as you do damage")));
    }
}
