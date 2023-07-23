package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class GetWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("getworld")) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 0){
            commandSender.sendMessage(FAIL_PREFIX + "This command requires no arguments!");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            if(commandSender instanceof Player){
                commandSender.sendMessage(CHAT_PREFIX + "World name is '" +
                    ((Player) commandSender).getWorld().getName().toLowerCase() + "'");
            } else {
                commandSender.sendMessage(FAIL_PREFIX + "Only players can run this!");
            }
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }
        return true;
    }
}
