package me.cire3.legxndsmp.itemrewardsquest.items;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuestInitializer;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class WitchScythe {
    public double secondsOfEffect;
    public List<String> originalLore;
    public List<String> lore = new ArrayList<>();
    public String name;

    public WitchScythe(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("WitchScythe.Lore");
        this.secondsOfEffect = plugin.getConfig().getDouble("WitchScythe.Seconds");
        this.name = plugin.getConfig().getString("WitchScythe.name");

        for(String s : plugin.getConfig().getStringList("WitchScythe.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }

    public void update(Plugin plugin){
        this.originalLore = plugin.getConfig().getStringList("WitchScythe.Lore");
        this.name = plugin.getConfig().getString("WitchScythe.Name");
        this.secondsOfEffect = plugin.getConfig().getDouble("WitchScythe.Seconds");

        for(String s : plugin.getConfig().getStringList("WitchScythe.Lore")){
            this.lore.add(s.toLowerCase());
        }
    }
}
