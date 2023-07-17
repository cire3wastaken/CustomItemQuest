package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuestInitializer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CHAT_PREFIX;
import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.DISABLED_MESSAGE;

public class AddProtectedRegionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.DARK_RED + DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("addprotectedregion")) {
            commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX + "Unknown command");
            return false;
        }
        if(strings.length != 2){
            commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX +
                "Specify two arguments (region id) (world name)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            ItemRewardsQuest.INSTANCE.protectedRegionsByWorld.computeIfAbsent(strings[1].toLowerCase(),
                    k -> new HashSet<>());

            if(ItemRewardsQuest.INSTANCE.protectedRegionsByWorld.get(strings[1]).add(strings[0].toLowerCase())){
                commandSender.sendMessage(ChatColor.GREEN + CHAT_PREFIX + "Successfully added region " +
                        strings[0] + " to the list of protected regions!");

                ItemRewardsQuest.INSTANCE.configuration.set("Protected." + strings[1].toLowerCase(), strings[0].toLowerCase());

                try {
                    ItemRewardsQuest.INSTANCE.configuration.save(ItemRewardsQuest.INSTANCE.configFile);
                } catch (IOException e) {
                    commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX +
                            "Failed to save deleted region to disk, check logs!");
                    e.printStackTrace();
                }

                try {
                    ItemRewardsQuest.INSTANCE.configuration.save(ItemRewardsQuest.INSTANCE.configFile);
                } catch (IOException e) {
                    commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX +
                            "Failed to save new region to disk, check logs!");
                    e.printStackTrace();
                }
            } else {
                commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX + "Region " +
                    strings[0] + " in world " + strings[1] + " already exists, do you mean /removeprotectedregion?");
            }
        } else {
            commandSender.sendMessage(ChatColor.DARK_RED + CHAT_PREFIX + "You do not have permissions!");
        }
        return true;
    }
}
