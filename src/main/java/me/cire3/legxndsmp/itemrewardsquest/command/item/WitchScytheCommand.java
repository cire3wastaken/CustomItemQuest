package me.cire3.legxndsmp.itemrewardsquest.command.item;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class WitchScytheCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if (!command.getName().equalsIgnoreCase("witchscythe")) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }
        if (strings.length != 1) {
            commandSender.sendMessage(FAIL_PREFIX + "Specify one argument (player)");
            return false;
        }

        if (commandSender.hasPermission("itemrewardsquest.giveitems") || commandSender.isOp()) {
            this.giveItem(commandSender, strings);
            commandSender.sendMessage(CHAT_PREFIX + "Successfully gave " + strings[0] + " a Witch Scythe!");
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }
        return true;
    }

    public void giveItem(CommandSender commandSender, String[] args){
        this.giveItem(commandSender, args, null);
    }

    public void giveItem(CommandSender commandSender, String[] args, ItemMeta metaToSave){
        ItemStack item = new ItemStack(Material.GOLD_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorUtils.color(ItemRewardsQuest.INSTANCE.witchScythe.name));
        meta.setLore(ColorUtils.color(ItemRewardsQuest.INSTANCE.witchScythe.lore));

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            commandSender.sendMessage(FAIL_PREFIX + args[0] + " is not online!");
            return;
        }

        if(metaToSave != null){
            Map<Enchantment, Integer> enchants = metaToSave.getEnchants();
            if(enchants != null){
                enchants.forEach((enchant, level) -> {
                    meta.addEnchant(enchant, level, false);
                });
            }

            String name = metaToSave.getDisplayName();
            if(name != null){
                meta.setDisplayName(name);
            }
        }

        item.setItemMeta(meta);
        target.getInventory().addItem(item);
    }
}
