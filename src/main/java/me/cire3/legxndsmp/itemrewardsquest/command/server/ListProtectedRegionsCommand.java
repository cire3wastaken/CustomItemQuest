package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class ListProtectedRegionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.RED + DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("listprotectedregions")){
            commandSender.sendMessage(ChatColor.RED + UNKNOWN_COMMAND);
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            commandSender.sendMessage(ChatColor.BOLD.toString() + ChatColor.GREEN.toString() + CHAT_PREFIX +
                    "Blacklisted Regions in Memory: ");
            ItemRewardsQuest.INSTANCE.protectedRegions.forEach((name, set) -> {
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
