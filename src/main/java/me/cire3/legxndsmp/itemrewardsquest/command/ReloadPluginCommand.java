package me.cire3.legxndsmp.itemrewardsquest.command;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuestInitializer;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.Bukkit;
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
        if(strings.length != 0){
            commandSender.sendMessage("This command requires no arguments");
            return false;
        }

        if(commandSender.isOp() || commandSender instanceof Player){
            if(commandSender.hasPermission("itemrewardsquest.reload") || commandSender.isOp()){
                try {
                    plugin.reloadConfig();
                    plugin.onDisable();
                    plugin.onEnable();
                    return true;
                } catch (Exception e){
                    commandSender.sendMessage("An error occurred, check logs");
                    plugin.getLogger().info(Arrays.toString(e.getStackTrace()));

                    return false;
                }
            }
        }

        return false;
    }

    public ReloadPluginCommand(ItemRewardsQuestInitializer plugin){
        this.plugin = plugin;
    }
}