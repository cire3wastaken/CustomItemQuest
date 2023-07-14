package me.cire3.legxndsmp.itemrewardsquest;

import me.cire3.legxndsmp.itemrewardsquest.command.item.*;
import me.cire3.legxndsmp.itemrewardsquest.command.player.ConvertCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.server.ReloadPluginCommand;
import me.cire3.legxndsmp.itemrewardsquest.events.*;
import me.cire3.legxndsmp.itemrewardsquest.items.*;
import me.cire3.legxndsmp.itemrewardsquest.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public enum ItemRewardsQuest
{
    INSTANCE;

    public static final String PLUGIN_VERSION = "0.0.0";
    public static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/cire3wastaken/CustomItemQuest/master/version.txt";
    public static final String GITHUB_REPO = "https://github.com/cire3wastaken/CustomItemQuest/tree/master";

    public final Set<String> protectedRegions = new HashSet<>();

    public boolean isEnabled;

    public VampireBlade vampireBlade;
    public ThorHammer thorHammer;
    public GhastBow ghastBow;
    public WitchScythe witchScythe;
    public Hyperion hyperion;

    public VampireBladeCommand vampireBladeCommand;
    public GhastBowCommand ghastBowCommand;
    public ThorHammerCommand thorHammerCommand;
    public WitchScytheCommand witchScytheCommand;
    public HypeCommand hypeCommand;
    public ReloadPluginCommand reloadPluginCommand;
    public ConvertCommand convertCommand;

    public File configFile;
    public FileConfiguration configuration;

    private boolean outdated;
    private ItemRewardsQuestInitializer plugin;

    public void init(ItemRewardsQuestInitializer plugin) {
        this.plugin = plugin;

        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        if(configFile.exists()){
            this.configuration = plugin.getConfig();
            plugin.saveDefaultConfig();
        } else {
            this.configuration = YamlConfiguration.loadConfiguration(this.configFile);
        }

        if(!this.isUpToDate()){
            this.outdated = true;
            Bukkit.getLogger().log(Level.WARNING,
                    "ItemRewardsQuest is not up to date! Build it yourself from " + GITHUB_REPO + " or download it from Releases!");
        }

        this.vampireBladeCommand = new VampireBladeCommand();
        this.ghastBowCommand = new GhastBowCommand();
        this.thorHammerCommand = new ThorHammerCommand();
        this.witchScytheCommand = new WitchScytheCommand();
        this.hypeCommand = new HypeCommand();
        this.reloadPluginCommand = new ReloadPluginCommand(plugin);
        this.convertCommand = new ConvertCommand();

        this.hyperion = new Hyperion(this.configuration);
        this.witchScythe = new WitchScythe(this.configuration);
        this.vampireBlade = new VampireBlade(this.configuration);
        this.thorHammer = new ThorHammer(this.configuration);
        this.ghastBow = new GhastBow(this.configuration);

        this.isEnabled = true;

        plugin.saveConfig();
        this.register(plugin);
    }

    public void enable() {
        this.register(this.plugin);

        this.isEnabled = true;

        if(this.outdated){
            Bukkit.getLogger().log(Level.WARNING,
                    "ItemRewardsQuest is not up to date! Build it yourself from " + GITHUB_REPO + " or download it from Releases!");
        }
    }

    public void disable(){
        HandlerList.unregisterAll(plugin);

        this.isEnabled = false;

        if(this.outdated){
            Bukkit.getLogger().log(Level.WARNING,
                    "ItemRewardsQuest is not up to date! Build it yourself from " + GITHUB_REPO + " or download it from Releases!");
        }
    }

    private void register(ItemRewardsQuestInitializer plugin){
        Bukkit.getLogger().info("Registering startup for ItemRewardsQuest");
        Bukkit.getServer().getPluginManager().registerEvents(new AttackEntityEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new AttackEntityByProjectileEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ProjectileHitBlockEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new RightClickInteractEvent(), plugin);

        plugin.getCommand("vampireblade").setExecutor(this.vampireBladeCommand);
        plugin.getCommand("ghastbow").setExecutor(this.ghastBowCommand);
        plugin.getCommand("thorhammer").setExecutor(this.thorHammerCommand);
        plugin.getCommand("hyperionblade").setExecutor(this.hypeCommand);
        plugin.getCommand("witchscythe").setExecutor(this.witchScytheCommand);
        plugin.getCommand("reloaditemrewardsquest").setExecutor(this.reloadPluginCommand);
        plugin.getCommand("updateitem").setExecutor(this.convertCommand);

        this.thorHammer.update(configuration);
        this.vampireBlade.update(configuration);
        this.witchScythe.update(configuration);
        this.ghastBow.update(configuration);
        this.hyperion.update(configuration);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.addAttachment(plugin);
        }
        Bukkit.getLogger().info("Registering for ItemRewardsQuest finished");
    }

    private boolean isUpToDate() {
        File downloaded = new File(this.plugin.getDataFolder(), "versionDownloaded.txt");
        File current = new File(this.plugin.getDataFolder(), "versionCurrent.txt");

        if(!FileUtils.createNewFile(current) || !FileUtils.createNewFile(downloaded)){
            return false;
        }

        try {
            FileUtils.downloadUsingStream(VERSION_FILE_URL, downloaded);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to download version.txt, please manually verify this is up to date!");
            e.printStackTrace();
            return false;
        }
        if(!FileUtils.createNewFile(current)){
            return false;
        }

        return FileUtils.getVersion(downloaded).equals(FileUtils.getVersion(current)) &&
                !FileUtils.getVersion(downloaded).equalsIgnoreCase(String.valueOf(Integer.MAX_VALUE)) &&
                !FileUtils.getVersion(current).equalsIgnoreCase(String.valueOf(Integer.MAX_VALUE));
    }
}
