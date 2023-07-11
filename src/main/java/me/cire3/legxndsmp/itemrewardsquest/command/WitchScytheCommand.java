package me.cire3.legxndsmp.itemrewardsquest.command;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WitchScytheCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage("ItemRewardsQuest is currently disabled");
            return true;
        }

        if (!command.getName().equalsIgnoreCase("witchscythe")) {
            commandSender.sendMessage("Unknown command");
            return false;
        }
        if (strings.length != 1) {
            commandSender.sendMessage("Specify one argument (player)");
            return false;
        }

        if (commandSender.hasPermission("itemrewardsquest.giveitems") || commandSender.isOp()) {
            ItemStack item = new ItemStack(Material.GOLD_HOE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ColorUtils.toColor('&', ItemRewardsQuest.INSTANCE.witchScythe.name));
            meta.setLore(ItemRewardsQuest.INSTANCE.witchScythe.originalLore);
            item.setItemMeta(meta);

            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null) {
                commandSender.sendMessage(strings[0] + " is not online!");
                return true;
            }
            target.getInventory().addItem(item);
            return true;
        } else {
            commandSender.sendMessage("You do not have ItemRewardsQuest.GiveItem permissions");
            return true;
        }
    }
}