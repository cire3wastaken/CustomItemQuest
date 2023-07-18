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

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class HypeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.RED + DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("hyperionblade")) {
            commandSender.sendMessage(ChatColor.RED + UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(ChatColor.RED + CHAT_PREFIX + "Specify one argument (player)");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.giveitems")  || commandSender.isOp()){
            ItemStack item = new ItemStack(Material.IRON_SPADE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ColorUtils.color(ItemRewardsQuest.INSTANCE.hyperion.name));
            meta.setLore(ColorUtils.color(ItemRewardsQuest.INSTANCE.hyperion.originalLore));
            item.setItemMeta(meta);

            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null) {
                commandSender.sendMessage(ChatColor.RED + CHAT_PREFIX + strings[0] + " is not online!");
                return true;
            }

            target.getInventory().addItem(item);
            commandSender.sendMessage(ChatColor.GREEN + CHAT_PREFIX + "Successfully gave " +
                strings[0] + " a Hyperion!");
        } else {
            commandSender.sendMessage(ChatColor.RED + PERMISSION_DENIED);
        }
        return true;
    }
}
