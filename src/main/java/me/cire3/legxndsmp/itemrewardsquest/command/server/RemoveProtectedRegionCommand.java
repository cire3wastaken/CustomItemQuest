package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class RemoveProtectedRegionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("removeprotectedregion")) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 2){
            commandSender.sendMessage(FAIL_PREFIX + "Specify two arguments (region id) (world name)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.addregions") || commandSender.isOp()){
            if(!ItemRewardsQuest.INSTANCE.protectedRegions.containsKey(strings[1].toLowerCase())){
                commandSender.sendMessage(FAIL_PREFIX + "World '" +
                    strings[1] + "' doesn't exist!");
                return true;
            }

            if(ItemRewardsQuest.INSTANCE.protectedRegions.get(strings[1].toLowerCase()).remove(strings[0].toLowerCase())){
                List<String> temp = ItemRewardsQuest.INSTANCE.configuration.getStringList("Protected.Blacklist." +
                    strings[1].toLowerCase());

                if(temp == null){
                    commandSender.sendMessage(FAIL_PREFIX + "Blacklisted region '" +
                            strings[0] + "' in world '" + strings[1] + "' doesn't exist, do you mean /addprotectedregion?");
                    return true;
                }

                temp.remove(strings[0].toLowerCase());

                ItemRewardsQuest.INSTANCE.configuration.set("Protected.Blacklist." + strings[1].toLowerCase(), temp);

                try {
                    ItemRewardsQuest.INSTANCE.configuration.save(ItemRewardsQuest.INSTANCE.configFile);

                    commandSender.sendMessage(CHAT_PREFIX + "Successfully removed region '" +
                            strings[0] + "' from the list of protected regions!");
                } catch (IOException e) {
                    commandSender.sendMessage(FAIL_PREFIX + "Failed to save deleted region to disk, check logs!");
                    commandSender.sendMessage(FAIL_PREFIX + "This change will only be in memory!");
                    e.printStackTrace();
                }
            } else {
                commandSender.sendMessage(FAIL_PREFIX + "Blacklisted region '" +
                        strings[0] + "' in world '" + strings[1] + "' doesn't exist, do you mean /addprotectedregion?");
            }
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }
        return true;
    }
}
