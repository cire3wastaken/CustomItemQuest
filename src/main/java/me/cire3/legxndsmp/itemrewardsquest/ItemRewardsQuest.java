package me.cire3.legxndsmp.itemrewardsquest;

import me.cire3.legxndsmp.itemrewardsquest.command.item.*;
import me.cire3.legxndsmp.itemrewardsquest.command.player.ConvertCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.server.*;
import me.cire3.legxndsmp.itemrewardsquest.events.*;
import me.cire3.legxndsmp.itemrewardsquest.items.*;
import me.cire3.legxndsmp.itemrewardsquest.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public enum ItemRewardsQuest
{
    INSTANCE;

    public static final String CHAT_PREFIX = "[ItemRewardsQuest] >> ";
    public static final String DISABLED_MESSAGE = CHAT_PREFIX + "ItemRewardsQuest is currently disabled!";
    public static final String PERMISSION_DENIED = CHAT_PREFIX + "You do not have permissions!";
    public static final String UNKNOWN_COMMAND = CHAT_PREFIX + "Unknown command!";

    public static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/cire3wastaken/CustomItemQuest/master/version.txt";
    public static final String GITHUB_REPO = "https://github.com/cire3wastaken/CustomItemQuest/tree/master";
    public static final String PLUGIN_VERSION = "1.0.0";
    public static final String OUTDATED_MESSAGE = ChatColor.DARK_RED + CHAT_PREFIX +
            "ItemRewardsQuest is not up to date! Build it yourself from " + GITHUB_REPO + " or download it from Releases!";

    public final Map<String, Set<String>> protectedRegions = new HashMap<>();
    public final Map<String, Set<String>> whitelistedRegions = new HashMap<>();

    public final HashMap<String, Long> tillNextMessage = new HashMap<>();

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
    public AddProtectedRegionCommand addProtectedRegionCommand;
    public RemoveProtectedRegionCommand removeProtectedRegionCommand;
    public AddWhitelistedRegionCommand addWhitelistedRegionCommand;
    public RemoveWhitelistedRegionCommand removeWhitelistedRegionCommand;
    public ListProtectedRegionsCommand listProtectedRegionsCommand;
    public ListWhitelistedRegionsCommand listWhitelistedRegionsCommand;
    public GetWorldCommand getWorldCommand;

    public File configFile;
    public FileConfiguration configuration;

    private boolean outdated;
    private ItemRewardsQuestInitializer plugin;

    public void init(ItemRewardsQuestInitializer plugin) {
        this.plugin = plugin;

        this.defineConfig();

        if(!this.isUpToDate()){
            this.outdated = true;
            Bukkit.getLogger().info(OUTDATED_MESSAGE);
        }

        this.vampireBladeCommand = new VampireBladeCommand();
        this.ghastBowCommand = new GhastBowCommand();
        this.thorHammerCommand = new ThorHammerCommand();
        this.witchScytheCommand = new WitchScytheCommand();
        this.hypeCommand = new HypeCommand();
        this.reloadPluginCommand = new ReloadPluginCommand(plugin);
        this.convertCommand = new ConvertCommand();
        this.addProtectedRegionCommand = new AddProtectedRegionCommand();
        this.removeProtectedRegionCommand = new RemoveProtectedRegionCommand();
        this.addWhitelistedRegionCommand = new AddWhitelistedRegionCommand();
        this.removeWhitelistedRegionCommand = new RemoveWhitelistedRegionCommand();
        this.listProtectedRegionsCommand = new ListProtectedRegionsCommand();
        this.listWhitelistedRegionsCommand = new ListWhitelistedRegionsCommand();
        this.getWorldCommand = new GetWorldCommand();

        this.hyperion = new Hyperion(this.configuration);
        this.witchScythe = new WitchScythe(this.configuration);
        this.vampireBlade = new VampireBlade(this.configuration);
        this.thorHammer = new ThorHammer(this.configuration);
        this.ghastBow = new GhastBow(this.configuration);

        this.isEnabled = true;

        plugin.saveConfig();
        this.register(plugin);
        this.updateConfig();
        this.loadRegions();
    }

    public void enable() {
        this.defineConfig();

        this.register(this.plugin);

        this.isEnabled = true;

        if(this.outdated){
            Bukkit.getLogger().info(OUTDATED_MESSAGE);
        }
    }

    public void disable(){
        HandlerList.unregisterAll(plugin);

        this.isEnabled = false;

        if(this.outdated){
            Bukkit.getLogger().info(OUTDATED_MESSAGE);
        }
    }

    private void register(ItemRewardsQuestInitializer plugin){
        Bukkit.getLogger().info(ChatColor.BLUE + CHAT_PREFIX + "Registering startup for ItemRewardsQuest");
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
        plugin.getCommand("addprotectedregion").setExecutor(this.addProtectedRegionCommand);
        plugin.getCommand("removeprotectedregion").setExecutor(this.removeProtectedRegionCommand);
        plugin.getCommand("addwhitelistedregion").setExecutor(this.addWhitelistedRegionCommand);
        plugin.getCommand("removewhitelistedregion").setExecutor(this.removeWhitelistedRegionCommand);
        plugin.getCommand("listprotectedregions").setExecutor(this.listProtectedRegionsCommand);
        plugin.getCommand("listwhitelistedregions").setExecutor(this.listWhitelistedRegionsCommand);
        plugin.getCommand("getworld").setExecutor(this.getWorldCommand);

        this.thorHammer.update(configuration);
        this.vampireBlade.update(configuration);
        this.witchScythe.update(configuration);
        this.ghastBow.update(configuration);
        this.hyperion.update(configuration);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.addAttachment(plugin);
        }
        Bukkit.getLogger().info(ChatColor.GREEN + CHAT_PREFIX + "Registering for ItemRewardsQuest finished");
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
            Bukkit.getLogger().log(Level.SEVERE,
                "Failed to download version.txt, please manually verify this is up to date!" + GITHUB_REPO);
            Bukkit.getLogger().info(ChatColor.DARK_RED + CHAT_PREFIX +
                "Failed to download version.txt, please manually verify this is up to date!" + GITHUB_REPO);
            e.printStackTrace();
            return false;
        }
        if(!FileUtils.createNewFile(current)){
            return false;
        }

        double[] versionDownloaded = this.versionParse(FileUtils.getVersion(downloaded));
        double[] versionCurrent = this.versionParse(FileUtils.getVersion(current));

        if(versionCurrent == null || versionDownloaded == null){
            return false;
        }

        for(int i = 0; i < versionDownloaded.length && i < versionCurrent.length; i++){
            double downloadedVer = versionDownloaded[i];
            double currentVer = versionCurrent[i];
            if(downloadedVer > currentVer){
                return false;
            }
        }
        return true;
    }

    public void updateConfig(){
        File current = new File(this.plugin.getDataFolder(), "versionCurrent.txt");

        double[] versionCurrent = this.versionParse(FileUtils.getVersion(current));
        double[] versionConfig = this.versionParse(this.configuration.getString("Plugin.Version"));

        if(versionCurrent == null || versionConfig == null){
            return;
        }

        for(int i = 0; i < versionConfig.length && i < versionCurrent.length; i++){
            if(versionConfig[i] < versionCurrent[i]){
                if(this.configFile.delete()){
                    this.plugin.saveDefaultConfig();
                } else {
                    Bukkit.getLogger().info(ChatColor.DARK_RED + CHAT_PREFIX +
                            "Failed to update config.yml, force disabling ItemRewardsQuest to prevent glitches!\n" +
                            "Grab the latest config.yml from " + GITHUB_REPO + " and replace the current one!");
                    this.plugin.onDisable();
                }
                return;
            } else if (versionConfig[i] > versionCurrent[i]){
                break;
            }
        }
    }

    public double[] versionParse(String ver){
        if(ver.split("\\.").length != 3){
            Bukkit.getLogger().info(ChatColor.DARK_RED + CHAT_PREFIX +
                    "Unknown version format, please manually check if this is up to date!" + GITHUB_REPO);
            return null;
        }
        return new double[]{
                Double.parseDouble(ver.split("\\.")[0]),
                Double.parseDouble(ver.split("\\.")[1]),
                Double.parseDouble(ver.split("\\.")[2])
        };
    }

    public void defineConfig(){
        this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()){
            this.configuration = this.plugin.getConfig();
            this.plugin.saveDefaultConfig();
        } else {
            this.configuration = YamlConfiguration.loadConfiguration(this.configFile);
        }
    }

    @SuppressWarnings({"unchecked"})
    public void loadRegions(){
        /*Map<String, List<String>> whitelisted = ((Map<String, List<String>>) this.configuration.get("Protected.Whitelist"));
        Map<String, List<String>> blacklisted = ((Map<String, List<String>>) this.configuration.get("Protected.Blacklist"));

        this.whitelistedRegions.clear();
        this.protectedRegions.clear();

        for(String key : whitelisted.keySet()){
            this.whitelistedRegions.put(key, new HashSet<>(whitelisted.get(key)));
        }

        for(String key : blacklisted.keySet()){
            this.protectedRegions.put(key, new HashSet<>(whitelisted.get(key)));
        }*/
    }
}
