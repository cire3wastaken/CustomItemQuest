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
    private final Plugin plugin;

    public VampireBladeCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!commandSender.getName().equalsIgnoreCase("vampireblade")){
            return false;
        }
        if(strings.length != 1){
            return false;
        }

        if(commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if(sender.hasPermission("customitems.spawn.vampblade")){
                ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ColorUtils.toColor('&', ItemRewardsQuest.INSTANCE.vampireBlade.name));
                meta.setLore(ItemRewardsQuest.INSTANCE.vampireBlade.originalLore);
                item.setItemMeta(meta);

                Player target = Bukkit.getPlayer(strings[0]);
                target.getInventory().addItem(item);
            }
        }

        return false;
    }
}
