package me.cire3.legxndsmp.itemrewardsquest.command.item;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
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

import java.util.ArrayList;

public class GhastBowCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.DARK_RED + "ItemRewardsQuest is currently disabled");
            return true;
        }

        if(!command.getName().equalsIgnoreCase("ghastbow")) {
            commandSender.sendMessage(ChatColor.DARK_RED + "Unknown command");
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(ChatColor.DARK_RED + "Specify one argument (player)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.giveitems")  || commandSender.isOp()){
            this.giveItem(commandSender, strings);
        } else {
            commandSender.sendMessage(ChatColor.DARK_RED + "You do not have ItemRewardsQuest.GiveItem permissions");
        }
        return true;
    }

    public void giveItem(CommandSender commandSender, String[] arg){
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorUtils.color(ItemRewardsQuest.INSTANCE.ghastBow.nameConfig));
        meta.setLore(ColorUtils.color(ItemRewardsQuest.INSTANCE.ghastBow.originalLoreConfig));
        item.setItemMeta(meta);

        Player target = Bukkit.getPlayer(arg[0]);
        if (target == null) {
            commandSender.sendMessage(ChatColor.DARK_RED + arg[0] + " is not online!");
            return;
        }

        target.getInventory().addItem(item);
        commandSender.sendMessage(ChatColor.GREEN + "Successfully gave " + arg[0] + " a Ghast Bow!");
    }
}
