package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuestInitializer;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class WitchScythe {
    public double secondsOfEffect;
    public List<String> originalLore;
    public List<String> lore = new ArrayList<>();
    public String name;

    public WitchScythe(FileConfiguration configuration){
        this.update(configuration);
    }

    public void update(FileConfiguration fileConfiguration){
        this.originalLore = fileConfiguration.getStringList("WitchScythe.Lore");
        this.name = fileConfiguration.getString("WitchScythe.Name");
        this.secondsOfEffect = fileConfiguration.getDouble("WitchScythe.Seconds");
        this.lore = new ArrayList<>();

        for(String s : ColorUtils.color(fileConfiguration.getStringList("WitchScythe.Lore"))){
            this.lore.add(s.toLowerCase());
        }
    }
}
