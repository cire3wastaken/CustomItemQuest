package me.cire3.legxndsmp.itemrewardsquest.command.player;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class BlacklistPlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("blacklistplayer")) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(FAIL_PREFIX + "Specify one argument (player name)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.manageplayers") || commandSender.isOp()) {
            if(INSTANCE.blacklistedPlayers.add(strings[0])){
                commandSender.sendMessage(CHAT_PREFIX + "Successfully blacklisted " +
                        strings[0] + " from using custom items!");
            } else {
                commandSender.sendMessage(CHAT_PREFIX + strings[0] + " is already blacklisted, " +
                        "did you mean to /freeplayer?");
            }
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }

        return true;
    }
}
