package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RemoveProtectedRegionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.DARK_RED + "ItemRewardsQuest is currently disabled");
            return true;
        }

        if(!command.getName().equalsIgnoreCase("removeprotectedregion")) {
            commandSender.sendMessage(ChatColor.DARK_RED + "Unknown command");
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(ChatColor.DARK_RED + "Specify one argument (region id)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            if(ItemRewardsQuest.INSTANCE.protectedRegions.remove(strings[0])){
                commandSender.sendMessage(ChatColor.GREEN + "Successfully re,pved region " +
                        strings[0] + " from the list of protected regions!");
            } else {
                commandSender.sendMessage(ChatColor.DARK_RED + "Region " +
                        strings[0] + " does not exist, do you mean /addprotectedregion?");
            }
        } else {
            commandSender.sendMessage(ChatColor.DARK_RED + "You do not have ItemRewardsQuest.AddRegions permissions");
        }
        return true;
    }
}
