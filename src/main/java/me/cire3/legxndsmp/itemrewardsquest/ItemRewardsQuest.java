package me.cire3.legxndsmp.itemrewardsquest;

import me.cire3.legxndsmp.itemrewardsquest.command.ItemCommands;
import me.cire3.legxndsmp.itemrewardsquest.command.ConvertCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.LFixCommand;
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

public class ItemRewardsQuest
{
    private static ItemRewardsQuest INSTANCE;

    public final Map<String, Set<String>> protectedRegions = new HashMap<>();
    public final Map<String, Set<String>> whitelistedRegions = new HashMap<>();
    public final Map<String, Long> tillNextMessage = new HashMap<>();
    public final Map<Items, Boolean> toggledItems = new HashMap<>();
    public final Set<String> blacklistedPlayers = new HashSet<>();

    public boolean isEnabled;

    public ConvertCommand convertCommand;
    public LFixCommand lFixCommand;
    public ItemCommands itemCommands;

    private File configFile;


    private FileConfiguration configuration;

    private boolean outdated;
    private ItemRewardsQuestInitializer plugin;

    private ItemRewardsQuest(){

    }
    public static ItemRewardsQuest getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ItemRewardsQuest();
        }
        return INSTANCE;
    }

    public void init(ItemRewardsQuestInitializer plugin) {
        this.plugin = plugin;

        this.defineConfig();

        if(!this.isUpToDate()){
            this.outdated = true;
            Bukkit.getLogger().info(Constants.OUTDATED_MESSAGE);
        }

        this.convertCommand = new ConvertCommand();
        this.lFixCommand = new LFixCommand();
        this.itemCommands = new ItemCommands();

        this.isEnabled = true;

        plugin.saveConfig();
        this.register(plugin);
        this.updateConfig();
        this.loadRegions();
        this.items();
    }

    public void enable() {
        this.defineConfig();

        this.register(this.plugin);

        this.isEnabled = true;

        if(this.outdated){
            Bukkit.getLogger().info(Constants.OUTDATED_MESSAGE);
        }
    }

    public void disable(){
        HandlerList.unregisterAll(plugin);

        this.isEnabled = false;

        if(this.outdated){
            Bukkit.getLogger().info(Constants.OUTDATED_MESSAGE);
        }
    }

    private void register(ItemRewardsQuestInitializer plugin){
        Bukkit.getLogger().info(ChatColor.BLUE + Constants.CHAT_PREFIX + "Registering startup for ItemRewardsQuest");
        Bukkit.getServer().getPluginManager().registerEvents(new AttackEntityEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new AttackEntityByProjectileEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ProjectileHitBlockEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new RightClickInteractEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinServerEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ShootBowEvent(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerChatEvents(), plugin);

        plugin.getCommand("updateitem").setExecutor(this.convertCommand);
        plugin.getCommand("lfix").setExecutor(this.lFixCommand);
        plugin.getCommand("itemrewardsquest").setExecutor(this.itemCommands);

        ThorHammer.update(configuration);
        VampireBlade.update(configuration);
        WitchScythe.update(configuration);
        GhastBow.update(configuration);
        Hyperion.update(configuration);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.addAttachment(plugin);
        }
        Bukkit.getLogger().info(ChatColor.GREEN + Constants.CHAT_PREFIX + "Registering for ItemRewardsQuest finished");
    }

    private boolean isUpToDate() {
        File downloaded = new File(this.plugin.getDataFolder(), "versionDownloaded.txt");
        File current = new File(this.plugin.getDataFolder(), "versionCurrent.txt");

        if(!FileUtils.createNewFile(current) || !FileUtils.createNewFile(downloaded)){
            return false;
        }

        try {
            FileUtils.downloadUsingStream(Constants.VERSION_FILE_URL, downloaded);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE,
                "Failed to download version.txt, please manually verify this is up to date!" + Constants.GITHUB_REPO);
            Bukkit.getLogger().info(ChatColor.DARK_RED + Constants.CHAT_PREFIX +
                "Failed to download version.txt, please manually verify this is up to date!" + Constants.GITHUB_REPO);
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

    private void updateConfig(){
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
                    Bukkit.getLogger().info(ChatColor.DARK_RED + Constants.CHAT_PREFIX +
                            "Failed to update config.yml, force disabling ItemRewardsQuest to prevent glitches!\n" +
                            "Grab the latest config.yml from " + Constants.GITHUB_REPO + " and replace the current one!");
                    this.plugin.onDisable();
                }
                return;
            } else if (versionConfig[i] > versionCurrent[i]){
                break;
            }
        }
    }

    private double[] versionParse(String ver){
        if(ver == null){
            Bukkit.getLogger().info(ChatColor.DARK_RED + Constants.CHAT_PREFIX +
                    "Unknown version format, please manually check if this is up to date! " + Constants.GITHUB_REPO);
            return null;
        }

        if(ver.split("\\.").length != 3){
            Bukkit.getLogger().info(ChatColor.DARK_RED + Constants.CHAT_PREFIX +
                    "Unknown version format, please manually check if this is up to date! " + Constants.GITHUB_REPO);
            return null;
        }
        return new double[]{
                Double.parseDouble(ver.split("\\.")[0]),
                Double.parseDouble(ver.split("\\.")[1]),
                Double.parseDouble(ver.split("\\.")[2])
        };
    }

    private void defineConfig(){
        this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()){
            this.configuration = this.plugin.getConfig();
            this.plugin.saveDefaultConfig();
        } else {
            this.configuration = YamlConfiguration.loadConfiguration(this.configFile);
        }
    }

    private void loadRegions(){
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

    private void items(){
        this.toggledItems.putIfAbsent(Items.GHASTBOW, true);
        this.toggledItems.putIfAbsent(Items.VAMPIREBLADE, true);
        this.toggledItems.putIfAbsent(Items.HYPERION, true);
        this.toggledItems.putIfAbsent(Items.WITCHSCYHTE, true);
        this.toggledItems.putIfAbsent(Items.THORHAMMER, true);
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

    public File getFile() {
        return configFile;
    }

    public FileConfiguration getConfig() {
        return configuration;
    }


    public boolean isBlacklisted(Player p){
        return this.blacklistedPlayers.contains(p.getName().toLowerCase());
    }

    public boolean isDisabled(Items item){
        return !this.toggledItems.get(item);
    }

    public boolean status(){
        return this.outdated;
    }


}
