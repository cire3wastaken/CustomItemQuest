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

public class ReloadPluginCommand implements CommandExecutor {
    private final ItemRewardsQuestInitializer plugin;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!command.getName().equalsIgnoreCase("reloadItemRewardsQuest")){
            return false;
        }

        if(commandSender.isOp() || commandSender.hasPermission("itemrewardsquest.reload")){
            try {
                plugin.reloadConfig();
                plugin.onDisable();
                plugin.onEnable();
                commandSender.sendMessage(ChatColor.GREEN + "Successfully reloaded ItemRewardsQuest!");
            } catch (Exception e){
                commandSender.sendMessage(ChatColor.DARK_RED + "An error occurred");
                plugin.getLogger().info(Arrays.toString(e.getStackTrace()));
            }
        } else {
            commandSender.sendMessage(ChatColor.DARK_RED + "You do not have itemrewardsquest.reload permission!");
        }
        return true;
    }

    public ReloadPluginCommand(ItemRewardsQuestInitializer plugin){
        this.plugin = plugin;
    }
}
