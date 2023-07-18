package me.cire3.legxndsmp.itemrewardsquest.command.item;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.items.WitchScythe;
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

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class WitchScytheCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.RED + DISABLED_MESSAGE);
            return true;
        }

        if (!command.getName().equalsIgnoreCase("witchscythe")) {
            commandSender.sendMessage(ChatColor.RED + UNKNOWN_COMMAND);
            return false;
        }
        if (strings.length != 1) {
            commandSender.sendMessage(ChatColor.RED + CHAT_PREFIX + "Specify one argument (player)");
            return false;
        }

        if (commandSender.hasPermission("itemrewardsquest.giveitems") || commandSender.isOp()) {
            this.giveItem(commandSender, strings);
            commandSender.sendMessage(ChatColor.GREEN + CHAT_PREFIX + "Successfully gave " + strings[0] + " a Witch Scythe!");
        } else {
            commandSender.sendMessage(ChatColor.RED + PERMISSION_DENIED);
        }
        return true;
    }

    public void giveItem(CommandSender commandSender, String[] args){
        ItemStack item = new ItemStack(Material.GOLD_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorUtils.color(ItemRewardsQuest.INSTANCE.witchScythe.name));
        meta.setLore(ColorUtils.color(ItemRewardsQuest.INSTANCE.witchScythe.originalLore));
        item.setItemMeta(meta);

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + CHAT_PREFIX + args[0] + " is not online!");
            return;
        }
        target.getInventory().addItem(item);
    }
}
