package me.cire3.legxndsmp.itemrewardsquest;

import me.cire3.legxndsmp.itemrewardsquest.command.GhastBowCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.ThorHammerCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.VampireBladeCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.WitchScytheCommand;
import me.cire3.legxndsmp.itemrewardsquest.events.AttackEntityByProjectileEvent;
import me.cire3.legxndsmp.itemrewardsquest.events.AttackEntityEvent;
import me.cire3.legxndsmp.itemrewardsquest.items.GhastBow;
import me.cire3.legxndsmp.itemrewardsquest.items.ThorHammer;
import me.cire3.legxndsmp.itemrewardsquest.items.VampireBlade;
import me.cire3.legxndsmp.itemrewardsquest.items.WitchScythe;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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

    public VampireBladeCommand vampireBladeCommand;
    public GhastBowCommand ghastBowCommand;
    public ThorHammerCommand thorHammerCommand;
    public WitchScytheCommand witchScytheCommand;

    private ItemRewardsQuestInitializer plugin;

    public void init(ItemRewardsQuestInitializer plugin) {
        this.vampireBladeCommand = new VampireBladeCommand();
        this.ghastBowCommand = new GhastBowCommand();
        this.thorHammerCommand = new ThorHammerCommand();
        this.witchScytheCommand = new WitchScytheCommand();

        this.witchScythe = new WitchScythe(plugin);
        this.vampireBlade = new VampireBlade(plugin);
        this.thorHammer = new ThorHammer(plugin);
        this.ghastBow = new GhastBow(plugin);

        this.isEnabled = true;
        this.plugin = plugin;

        this.register(this.plugin);
    }

    public void enable() {
        this.register(this.plugin);

        this.isEnabled = true;
    }

    public void disable(){
        HandlerList.unregisterAll(plugin);

        this.isEnabled = false;
    }

    private void register(ItemRewardsQuestInitializer plugin){
        Bukkit.getServer().getPluginManager().registerEvents(new AttackEntityEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new AttackEntityByProjectileEvent(), plugin);

        plugin.getCommand("vampireblade").setExecutor(this.vampireBladeCommand);
        plugin.getCommand("ghastbow").setExecutor(this.ghastBowCommand);
        plugin.getCommand("thorhammer").setExecutor(this.thorHammerCommand);
        plugin.getCommand("witchscythe").setExecutor(this.witchScytheCommand);

        this.thorHammer.update(this.plugin);
        this.vampireBlade.update(this.plugin);
        this.witchScythe.update(this.plugin);
        this.ghastBow.update(this.plugin);

        for(Player p : getOnlinePlayers()){
            p.addAttachment(plugin);
        }
    }
}
