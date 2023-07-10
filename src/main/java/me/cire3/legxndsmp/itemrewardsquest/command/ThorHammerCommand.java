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

public class ThorHammerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!command.getName().equalsIgnoreCase("thorhammer")){
            commandSender.sendMessage("Unknown Command");
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage("Specify one argument (player)");
            return false;
        }

        Player sender = (Player) commandSender;
        if(sender.hasPermission("itemrewardsquest.giveitems") || sender.isOp()){
            ItemStack item = new ItemStack(Material.GOLD_AXE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ColorUtils.toColor('&', ItemRewardsQuest.INSTANCE.thorHammer.name));
            meta.setLore(ItemRewardsQuest.INSTANCE.thorHammer.originalLore);
            item.setItemMeta(meta);

            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null) {
                sender.sendMessage(strings[0] + " is not online!");
                return false;
            }
            target.getInventory().addItem(item);
            return true;
        }
        sender.sendMessage("You do not have ItemRewardsQuest.GiveItem permissions");
        return false;
    }
}
