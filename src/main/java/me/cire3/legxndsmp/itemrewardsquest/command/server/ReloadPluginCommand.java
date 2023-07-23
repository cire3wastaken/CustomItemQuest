package me.cire3.legxndsmp.itemrewardsquest.command.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuestInitializer;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class ReloadPluginCommand implements CommandExecutor {
    private final ItemRewardsQuestInitializer plugin;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("reloadItemRewardsQuest")){
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }

        if(strings.length != 0){
            commandSender.sendMessage(FAIL_PREFIX +
                    "This command requires no arguments!");
            return false;
        }

        if(commandSender.isOp() || commandSender.hasPermission("itemrewardsquest.reload")){
            try {
                plugin.reloadConfig();
                plugin.onDisable();
                plugin.onEnable();
                ItemRewardsQuest.INSTANCE.init(plugin);
                commandSender.sendMessage(CHAT_PREFIX + "Successfully reloaded ItemRewardsQuest!");
            } catch (Exception e){
                commandSender.sendMessage(FAIL_PREFIX + "An error occurred, check logs!");
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }
        return true;
    }

    public ReloadPluginCommand(ItemRewardsQuestInitializer plugin){
        this.plugin = plugin;
    }
}
