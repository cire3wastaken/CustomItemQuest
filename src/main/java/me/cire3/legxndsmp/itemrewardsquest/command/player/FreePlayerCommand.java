package me.cire3.legxndsmp.itemrewardsquest.command.player;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class FreePlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.RED + DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("freeplayer")) {
            commandSender.sendMessage(ChatColor.RED + UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(ChatColor.RED + CHAT_PREFIX +
                    "Specify one argument (player name)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.manageplayers") || commandSender.isOp()) {
            if(INSTANCE.blacklistedPlayers.remove(strings[0])){
                commandSender.sendMessage(ChatColor.GREEN + CHAT_PREFIX + "Successfully enabled " +
                        strings[0] + " to use custom items!");
            } else {
                commandSender.sendMessage(ChatColor.GREEN + CHAT_PREFIX + strings[0] + " isn't blacklisted, " +
                        "did you mean to /blacklistplayer?");
            }
        }

        return true;
    }
}
