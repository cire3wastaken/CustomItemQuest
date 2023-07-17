package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.HashSet;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CHAT_PREFIX;
import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.DISABLED_MESSAGE;

public class RemoveWhitelistedRegionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.DARK_RED + DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("removewhitelistedregion")) {
            commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX + "Unknown command");
            return false;
        }
        if(strings.length != 2){
            commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX +
                    "Specify two arguments (region id) (world name)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            ItemRewardsQuest.INSTANCE.whitelistedRegions.computeIfAbsent(strings[1].toLowerCase(),
                    k -> new HashSet<>());

            if(ItemRewardsQuest.INSTANCE.whitelistedRegions.get(strings[1]).add(strings[0].toLowerCase())){

                ItemRewardsQuest.INSTANCE.configuration.set("Protected.Whitelist" + strings[1].toLowerCase(),
                        strings[0].toLowerCase());

                try {
                    ItemRewardsQuest.INSTANCE.configuration.save(ItemRewardsQuest.INSTANCE.configFile);
                    commandSender.sendMessage(ChatColor.GREEN + CHAT_PREFIX + "Successfully added region " +
                            strings[0] + " to the list of whitelisted regions!");
                } catch (IOException e) {
                    commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX +
                            "Failed to save deleted region to disk, check logs!");
                    commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX +
                            "This change will only be in memory!");
                    e.printStackTrace();
                }
            } else {
                commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX + "Whitelisted region " +
                        strings[0] + " in world " + strings[1] + " doesn't exist, do you mean /addwhitelistedregion?");
            }
        } else {
            commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX + "You do not have permissions!");
        }
        return true;
    }
}
