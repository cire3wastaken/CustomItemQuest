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
import org.bukkit.plugin.Plugin;

public class VampireBladeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!commandSender.getName().equalsIgnoreCase("vampireblade")){
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage("Specify one argument (player)");
            return false;
        }

        if(commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if(sender.hasPermission("itemrewardsquest.giveitems")){
                ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ColorUtils.toColor('&', ItemRewardsQuest.INSTANCE.vampireBlade.name));
                meta.setLore(ItemRewardsQuest.INSTANCE.vampireBlade.originalLore);
                item.setItemMeta(meta);

                Player target = Bukkit.getPlayer(strings[0]);
                if (target == null) {
                    sender.sendMessage(strings[0] + " is not online!");
                    return false;
                }

                target.getInventory().addItem(item);
            }
        }

        return false;
    }
}
