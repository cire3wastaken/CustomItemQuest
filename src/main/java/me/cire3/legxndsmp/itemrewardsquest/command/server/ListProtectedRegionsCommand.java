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
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("listprotectedregions")){
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }

        if(strings.length != 0){
            commandSender.sendMessage(FAIL_PREFIX + "This command requires no arguments!");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            commandSender.sendMessage(CHAT_PREFIX + ChatColor.BOLD.toString() + ChatColor.GREEN.toString() +
                    "Blacklisted Regions in Memory: ");
            ItemRewardsQuest.INSTANCE.protectedRegions.forEach((name, set) -> {
                for(String reg : set){
                    commandSender.sendMessage(ChatColor.YELLOW + name + ": " + reg.toLowerCase());
                }
            });
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }
        return true;
    }
}
