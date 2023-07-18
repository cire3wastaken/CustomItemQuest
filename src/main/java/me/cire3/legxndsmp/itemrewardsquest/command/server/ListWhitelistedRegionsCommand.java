package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;
import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.PERMISSION_DENIED;

public class ListWhitelistedRegionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.RED + DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("listwhitelistedregions")){
            commandSender.sendMessage(ChatColor.RED + UNKNOWN_COMMAND);
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            commandSender.sendMessage(ChatColor.BOLD.toString() + ChatColor.GREEN.toString() + CHAT_PREFIX +
                    "Whitelisted Regions in Memory: ");
            ItemRewardsQuest.INSTANCE.whitelistedRegions.forEach((name, set) -> {
                for(String reg : set){
                    commandSender.sendMessage(ChatColor.YELLOW + name + ": " + reg.toLowerCase());
                }
            });
        } else {
            commandSender.sendMessage(ChatColor.RED + PERMISSION_DENIED);
        }
        return true;
    }
}
