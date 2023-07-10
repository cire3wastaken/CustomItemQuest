package me.cire3.legxndsmp.itemrewardsquest;

import me.cire3.legxndsmp.itemrewardsquest.events.AttackEntityByProjectileEvent;
import me.cire3.legxndsmp.itemrewardsquest.events.AttackEntityEvent;
import me.cire3.legxndsmp.itemrewardsquest.items.GhastBow;
import me.cire3.legxndsmp.itemrewardsquest.items.ThorHammer;
import me.cire3.legxndsmp.itemrewardsquest.items.VampireBlade;
import me.cire3.legxndsmp.itemrewardsquest.items.WitchScythe;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getServer;

public enum ItemRewardsQuest
{
    INSTANCE;

    public boolean isEnabled;

    public VampireBlade vampireBlade;
    public ThorHammer thorHammer;
    public GhastBow ghastBow;
    public WitchScythe witchScythe;

    public void init(Plugin plugin){
        this.witchScythe = new WitchScythe(plugin);
        this.vampireBlade = new VampireBlade(plugin);
        this.thorHammer = new ThorHammer(plugin);
        this.ghastBow = new GhastBow(plugin);
        this.isEnabled = true;

        getServer().getPluginManager().registerEvents(new AttackEntityEvent(), plugin);
        getServer().getPluginManager().registerEvents(new AttackEntityByProjectileEvent(), plugin);

        for(Player p : getOnlinePlayers()){
            p.addAttachment(plugin);
        }
    }

    public void enable(Plugin plugin){
        this.isEnabled = true;
    }

    public void disable(Plugin plugin){
        this.isEnabled = false;
    }
}
