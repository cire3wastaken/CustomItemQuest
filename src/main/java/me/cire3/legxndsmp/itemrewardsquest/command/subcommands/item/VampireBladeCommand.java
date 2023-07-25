package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import me.cire3.legxndsmp.itemrewardsquest.items.Items;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class VampireBladeCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!commandSender.hasPermission("itemrewardsquest.giveitems") && !commandSender.isOp()){
            commandSender.sendMessage(PERMISSION_DENIED);
            return;
        }

        boolean flag = false;
        if(strings.length == 3){
            if(strings[1].equalsIgnoreCase("give")) {
                if(this.giveItem(commandSender, strings))
                    commandSender.sendMessage(CHAT_PREFIX + "Successfully gave " + strings[2] + " a Vampire Blade!");
            } else {
                flag = true;
            }
        } else if (strings.length == 2) {
            if(strings[1].equalsIgnoreCase("toggle")) {
                boolean temp = ItemRewardsQuest.INSTANCE.toggledItems.get(Items.VAMPIREBLADE);
                ItemRewardsQuest.INSTANCE.toggledItems.remove(Items.VAMPIREBLADE);
                ItemRewardsQuest.INSTANCE.toggledItems.put(Items.VAMPIREBLADE, !temp);

                commandSender.sendMessage("Vampire Blades are now " + (!temp ? "disabled" : "enabled") + "!");
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }

        if(flag){
            commandSender.sendMessage(UNKNOWN_COMMAND);
            commandSender.sendMessage("/itemrewardsquest help");
        }
    }

    public boolean giveItem(CommandSender commandSender, String[] args) {
        return this.giveItem(commandSender, args, null);
    }

    public boolean giveItem(CommandSender commandSender, String[] args, ItemMeta metaToSave) {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorUtils.color(INSTANCE.vampireBlade.name));
        meta.setLore(ColorUtils.color(INSTANCE.vampireBlade.lore));

        Player target = Bukkit.getPlayerExact(args[2]);
        if (target == null) {
            commandSender.sendMessage(FAIL_PREFIX + args[2] + " is not online!");
            return false;
        }

        if (metaToSave != null) {
            Map<Enchantment, Integer> enchants = metaToSave.getEnchants();
            if (enchants != null) {
                enchants.forEach((enchant, level) -> {
                    meta.addEnchant(enchant, level, false);
                });
            }

            String name = metaToSave.getDisplayName();
            if (name != null) {
                meta.setDisplayName(name);
            }
        }

        item.setItemMeta(meta);
        target.getInventory().addItem(item);
        return true;
    }
}