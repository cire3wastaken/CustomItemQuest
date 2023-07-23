package me.cire3.legxndsmp.itemrewardsquest;

import me.cire3.legxndsmp.itemrewardsquest.command.item.*;
import me.cire3.legxndsmp.itemrewardsquest.command.player.BlacklistPlayerCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.player.ConvertCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.player.FreePlayerCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.player.ListBlacklistedPlayersCommand;
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

public enum ItemRewardsQuest
{
    INSTANCE;

    public static final String CHAT_PREFIX = ChatColor.BOLD.toString() + ChatColor.GREEN +
            "[ItemRewardsQuest] >> " + ChatColor.RESET;
    public static final String FAIL_PREFIX = ChatColor.BOLD.toString() + ChatColor.RED + CHAT_PREFIX + ChatColor.RESET;
    public static final String DISABLED_MESSAGE = FAIL_PREFIX + "ItemRewardsQuest is currently disabled!";
    public static final String PERMISSION_DENIED = FAIL_PREFIX + "You do not have permissions!";
    public static final String UNKNOWN_COMMAND = FAIL_PREFIX + "Unknown command!";
    public static final String CAN_NOT_USE = FAIL_PREFIX + "You can not use this item here!";
    public static final String BLACKLISTED = FAIL_PREFIX + "You are blacklisted from using custom items!";

    public static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/cire3wastaken/CustomItemQuest/master/version.txt";
    public static final String GITHUB_REPO = "https://github.com/cire3wastaken/CustomItemQuest/tree/master";
    public static final String PLUGIN_VERSION = "1.0.0";
    public static final String OUTDATED_MESSAGE = FAIL_PREFIX +
            "ItemRewardsQuest is not up to date! Build it yourself from " + GITHUB_REPO + " or download it from Releases!";

    public final Map<String, Set<String>> protectedRegions = new HashMap<>();
    public final Map<String, Set<String>> whitelistedRegions = new HashMap<>();
    public final Set<String> blacklistedPlayers = new HashSet<>();

    public final Map<String, Long> tillNextMessage = new HashMap<>();

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
    public BlacklistPlayerCommand blacklistPlayerCommand;
    public FreePlayerCommand freePlayerCommand;
    public ListBlacklistedPlayersCommand listBlacklistedPlayersCommand;

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
        this.listBlacklistedPlayersCommand = new ListBlacklistedPlayersCommand();
        this.blacklistPlayerCommand = new BlacklistPlayerCommand();
        this.freePlayerCommand = new FreePlayerCommand();

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
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinServerEvent(), plugin);

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
                    this.plugin.saveResource("config.yml", false);
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
        if(ver == null){
            Bukkit.getLogger().info(ChatColor.DARK_RED + CHAT_PREFIX +
                    "Unknown version format, please manually check if this is up to date! " + GITHUB_REPO);
            return null;
        }

        if(ver.split("\\.").length != 3){
            Bukkit.getLogger().info(ChatColor.DARK_RED + CHAT_PREFIX +
                    "Unknown version format, please manually check if this is up to date! " + GITHUB_REPO);
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

    public void loadRegions(){
        try {
            List<String> worlds = this.configuration.getStringList("Protected.Worldlist");
            for (String name : worlds) {
                String worldName = name.toLowerCase();
                List<String> whitelisted = this.configuration.getStringList("Protected.Whitelist." +
                        worldName);

                List<String> blacklisted = this.configuration.getStringList("Protected.Blacklist." +
                        worldName);

                if (whitelisted != null) {
                    for (String region : whitelisted) {
                        this.whitelistedRegions.computeIfAbsent(worldName, k -> new HashSet<>());
                        Set<String> temp = this.whitelistedRegions.get(worldName);
                        temp.add(region.toLowerCase());

                        this.whitelistedRegions.put(worldName, temp);
                    }
                }

                if (blacklisted != null) {
                    for (String region : blacklisted) {
                        this.protectedRegions.computeIfAbsent(worldName, k -> new HashSet<>());
                        Set<String> temp = this.protectedRegions.get(worldName);
                        temp.add(region.toLowerCase());

                        this.protectedRegions.put(worldName, temp);
                    }
                }
            }
        } catch (Exception e){
            Bukkit.getLogger().log(Level.SEVERE, "Unknown error, check logs!");
            e.printStackTrace();
        }

        this.protectedRegions.computeIfAbsent("survival", k -> new HashSet<>());
        this.whitelistedRegions.computeIfAbsent("survival", k -> new HashSet<>());
        this.protectedRegions.get("survival").add("spawn");
        this.whitelistedRegions.get("survival").add("pvparena");
    }

    public void activateCooldown(Player player){
        this.tillNextMessage.remove(player.getName());
        this.tillNextMessage.put(player.getName(), System.currentTimeMillis());
    }

    public boolean hasCooldown(Player player){
        if(!this.tillNextMessage.containsKey(player.getName())){
            return false;
        }

        return !(this.tillNextMessage.get(player.getName()) < (System.currentTimeMillis() - 5000));
    }

    public boolean isBlacklisted(Player p){
        return this.blacklistedPlayers.contains(p.getName());
    }

    public boolean status(){
        return this.outdated;
    }
}
