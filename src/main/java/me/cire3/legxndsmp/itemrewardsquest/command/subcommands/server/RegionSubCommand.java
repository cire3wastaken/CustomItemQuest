package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RegionSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission("itemrewardsquest.addregions") && !commandSender.isOp()){
            commandSender.sendMessage(Constants.PERMISSION_DENIED);
            return;
        }

        boolean flag = false;
        if(args.length == 5){
            if(args[1].equalsIgnoreCase("whitelist")){
                if(args[2].equalsIgnoreCase("add")){
                    this.addWhitelistedRegion(commandSender, args);
                } else if (args[2].equalsIgnoreCase("remove")){
                    this.removeWhitelistedRegion(commandSender, args);
                } else {
                    flag = true;
                }
            } else if (args[1].equalsIgnoreCase("blacklist")){
                if(args[2].equalsIgnoreCase("add")){
                    this.addBlacklistedRegion(commandSender, args);
                } else if (args[2].equalsIgnoreCase("remove")){
                    this.removeBlacklistedRegion(commandSender, args);
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
        } else if (args.length == 3){
            if(args[1].equalsIgnoreCase("whitelist")){
                if(args[2].equalsIgnoreCase("list")){
                    commandSender.sendMessage(Constants.CHAT_PREFIX + ChatColor.BOLD + ChatColor.GREEN +
                            "Whitelisted Regions in Memory: ");
                    ItemRewardsQuest.getInstance().whitelistedRegions.forEach((name, set) -> {
                        for(String reg : set){
                            commandSender.sendMessage(ChatColor.YELLOW + name + ": " + reg.toLowerCase());
                        }
                    });
                } else {
                    flag = true;
                }
            } else if (args[1].equalsIgnoreCase("blacklist")){
                if(args[2].equalsIgnoreCase("list")){
                    commandSender.sendMessage(Constants.CHAT_PREFIX + ChatColor.BOLD + ChatColor.GREEN +
                            "Blacklisted Regions in Memory: ");
                    ItemRewardsQuest.getInstance().protectedRegions.forEach((name, set) -> {
                        for(String reg : set){
                            commandSender.sendMessage(ChatColor.YELLOW + name + ": " + reg.toLowerCase());
                        }
                    });
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
        } else if (args.length == 2){
            if (args[1].equalsIgnoreCase("world")){
                if(commandSender instanceof Player) {
                    commandSender.sendMessage(Constants.CHAT_PREFIX + "Applicable world name is: " + ((Player) commandSender).getWorld().getName());
                } else {
                    commandSender.sendMessage(Constants.FAIL_PREFIX + "Only players can run this command!");
                }
            }
        } else {
            flag = true;
        }
        if(flag){
            commandSender.sendMessage(Constants.UNKNOWN_SUBCOMMAND);
        }
    }

    private void removeWhitelistedRegion(CommandSender commandSender, String[] strings){
        if(!ItemRewardsQuest.getInstance().whitelistedRegions.containsKey(strings[1].toLowerCase())){
            commandSender.sendMessage(Constants.FAIL_PREFIX + "World '" +
                    strings[4] + "' doesn't exist!");
            return;
        }

        if(ItemRewardsQuest.getInstance().whitelistedRegions.get(strings[4].toLowerCase()).remove(strings[3].toLowerCase())){
            List<String> temp = ItemRewardsQuest.getInstance().getConfig().getStringList("Protected.Whitelist." +
                    strings[1].toLowerCase());

            if(temp == null){
                commandSender.sendMessage(Constants.FAIL_PREFIX + "Whitelisted region '" +
                        strings[3] + "' in world '" + strings[4] + "' doesn't exist!");
                return;
            }

            temp.remove(strings[3].toLowerCase());

            ItemRewardsQuest.getInstance().getConfig().set("Protected.Whitelist." + strings[4].toLowerCase(), temp);

            try {
                ItemRewardsQuest.getInstance().getConfig().save(ItemRewardsQuest.getInstance().getFile());

                commandSender.sendMessage(Constants.CHAT_PREFIX + "Successfully removed region '" +
                        strings[0] + "' from the list of whitelisted regions!");
            } catch (IOException e) {
                commandSender.sendMessage(Constants.FAIL_PREFIX + "Failed to save deleted region to disk, check logs!");
                commandSender.sendMessage(Constants.FAIL_PREFIX + "This change will only be in memory!");
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(Constants.FAIL_PREFIX + "Whitelisted region '" +
                    strings[3] + "' in world '" + strings[4] + "' doesn't exist!");
        }
    }

    private void removeBlacklistedRegion(CommandSender commandSender, String[] strings){
        if(!ItemRewardsQuest.getInstance().protectedRegions.containsKey(strings[1].toLowerCase())){
            commandSender.sendMessage(Constants.FAIL_PREFIX + "World '" +
                    strings[4] + "' doesn't exist!");
            return;
        }

        if(ItemRewardsQuest.getInstance().protectedRegions.get(strings[4].toLowerCase()).remove(strings[3].toLowerCase())){
            List<String> temp = ItemRewardsQuest.getInstance().getConfig().getStringList("Protected.Blacklist." +
                    strings[1].toLowerCase());

            if(temp == null){
                commandSender.sendMessage(Constants.FAIL_PREFIX + "Blacklisted region '" +
                        strings[3] + "' in world '" + strings[4] + "' doesn't exist!");
                return;
            }

            temp.remove(strings[3].toLowerCase());

            ItemRewardsQuest.getInstance().getConfig().set("Protected.Blacklist." + strings[4].toLowerCase(), temp);

            try {
                ItemRewardsQuest.getInstance().getConfig().save(ItemRewardsQuest.getInstance().getFile());

                commandSender.sendMessage(Constants.CHAT_PREFIX + "Successfully removed region '" +
                        strings[0] + "' from the list of blacklisted regions!");
            } catch (IOException e) {
                commandSender.sendMessage(Constants.FAIL_PREFIX + "Failed to save deleted region to disk, check logs!");
                commandSender.sendMessage(Constants.FAIL_PREFIX + "This change will only be in memory!");
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(Constants.FAIL_PREFIX + "Blacklisted region '" +
                    strings[3] + "' in world '" + strings[4] + "' doesn't exist!");
        }
    }

    private void addBlacklistedRegion(CommandSender commandSender, String[] strings){
        ItemRewardsQuest.getInstance().protectedRegions.computeIfAbsent(strings[4].toLowerCase(),
                k -> new HashSet<>());

        if(ItemRewardsQuest.getInstance().protectedRegions.get(strings[4]).add(strings[3].toLowerCase())){
            List<String> temp = ItemRewardsQuest.getInstance().getConfig().getStringList("Protected.Blacklist." +
                    strings[1].toLowerCase());

            if(temp == null){
                temp = new ArrayList<>();
            }

            temp.add(strings[3].toLowerCase());

            ItemRewardsQuest.getInstance().getConfig().set("Protected.Blacklist." + strings[4].toLowerCase(), temp);

            List<String> worlds = ItemRewardsQuest.getInstance().getConfig().getStringList("Protected.Worldlist");
            if(worlds == null){
                worlds = new ArrayList<>();
            }

            if(!worlds.contains(strings[1].toLowerCase())) {
                worlds.add(strings[1].toLowerCase());
                ItemRewardsQuest.getInstance().getConfig().set("Protected.Worldlist", worlds);
            }

            try {
                ItemRewardsQuest.getInstance().getConfig().save(ItemRewardsQuest.getInstance().getFile());
                commandSender.sendMessage(Constants.CHAT_PREFIX + "Successfully added region '" +
                        strings[0] + "' to the list of blacklistec regions!");
            } catch (IOException e) {
                commandSender.sendMessage(Constants.FAIL_PREFIX + "Failed to save deleted region to disk, check logs!");
                commandSender.sendMessage(Constants.FAIL_PREFIX + "This change will only be in memory!");
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(Constants.FAIL_PREFIX + "Blacklisted region '" +
                    strings[0] + "' in world '" + strings[1] + "' already exists!");
        }
    }

    private void addWhitelistedRegion(CommandSender commandSender, String[] strings){
        ItemRewardsQuest.getInstance().whitelistedRegions.computeIfAbsent(strings[4].toLowerCase(),
                k -> new HashSet<>());

        if(ItemRewardsQuest.getInstance().whitelistedRegions.get(strings[4]).add(strings[3].toLowerCase())){
            List<String> temp = ItemRewardsQuest.getInstance().getConfig().getStringList("Protected.Whitelist." +
                    strings[1].toLowerCase());

            if(temp == null){
                temp = new ArrayList<>();
            }

            temp.add(strings[3].toLowerCase());

            ItemRewardsQuest.getInstance().getConfig().set("Protected.Whitelist." + strings[4].toLowerCase(), temp);

            List<String> worlds = ItemRewardsQuest.getInstance().getConfig().getStringList("Protected.Worldlist");
            if(worlds == null){
                worlds = new ArrayList<>();
            }

            if(!worlds.contains(strings[1].toLowerCase())) {
                worlds.add(strings[1].toLowerCase());
                ItemRewardsQuest.getInstance().getConfig().set("Protected.Worldlist", worlds);
            }

            try {
                ItemRewardsQuest.getInstance().getConfig().save(ItemRewardsQuest.getInstance().getFile());
                commandSender.sendMessage(Constants.CHAT_PREFIX + "Successfully added region '" +
                        strings[0] + "' to the list of whitelisted regions!");
            } catch (IOException e) {
                commandSender.sendMessage(Constants.FAIL_PREFIX + "Failed to save deleted region to disk, check logs!");
                commandSender.sendMessage(Constants.FAIL_PREFIX + "This change will only be in memory!");
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(Constants.FAIL_PREFIX + "Whitelisted region '" +
                    strings[0] + "' in world '" + strings[1] + "' already exists!");
        }
    }
}
