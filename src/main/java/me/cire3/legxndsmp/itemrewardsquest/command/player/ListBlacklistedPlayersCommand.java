package me.cire3.legxndsmp.itemrewardsquest.command.player;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class ListBlacklistedPlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("listblacklistedplayers")) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(FAIL_PREFIX + "Specify one argument (player name)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.manageplayers") || commandSender.isOp()) {
            commandSender.sendMessage(CHAT_PREFIX + ChatColor.YELLOW + "Blacklisted players: ");
            int count = 1;

            for(String name : ItemRewardsQuest.INSTANCE.blacklistedPlayers){
                commandSender.sendMessage(ChatColor.YELLOW + "#" + count + ": " + name);
                count++;
            }
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }

        return true;
    }
}
