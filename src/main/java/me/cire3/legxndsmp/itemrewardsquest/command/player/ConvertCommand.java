package me.cire3.legxndsmp.itemrewardsquest.command.player;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConvertCommand implements CommandExecutor {
    public static final String OLD_VAMPBLADE_LORE = "Ability: gain half as much HP as you do damage";
    public static final String OLD_GHASTBOW_LORE = "&eAbility: create explosions upon whom your arrow lands on";
    public static final String OLD_THORHAMMER_LORE = "&cAbility: incur lightning upon your victims";
    public static final String OLD_WITCHSCYTHE_LORE = "&aAbility: inflict poison on your victims";

    public static final List<String> OLD_LORE = Arrays.asList(OLD_GHASTBOW_LORE, OLD_THORHAMMER_LORE,
            OLD_VAMPBLADE_LORE, OLD_WITCHSCYTHE_LORE);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(ChatColor.DARK_RED + "ItemRewardsQuest is currently disabled");
            return true;
        }

        if(!command.getName().equalsIgnoreCase("updateitem")) {
            commandSender.sendMessage(ChatColor.DARK_RED + "Unknown command");
            return false;
        }
        if(strings.length != 0){
            commandSender.sendMessage(ChatColor.DARK_RED + "This command requires no arguments");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.updateitems") || commandSender.isOp()){
            if(commandSender instanceof Player){
                Player target = (Player) commandSender;
                if(target.getItemInHand() == null){
                    commandSender.sendMessage(ChatColor.DARK_RED + "Please hold an custom item to update!");
                    return true;
                }
                if(!target.getItemInHand().hasItemMeta()){
                    commandSender.sendMessage(ChatColor.DARK_RED + "Please hold an custom item to update!");
                    return true;
                }
                if(!target.getItemInHand().getItemMeta().hasLore()){
                    commandSender.sendMessage(ChatColor.DARK_RED + "Please hold an custom item to update!");
                    return true;
                }

                ItemMeta meta = target.getItemInHand().getItemMeta();
                for(String lore : OLD_LORE){
                    if(meta.getLore().contains(lore)){
                        target.getItemInHand().setType(Material.AIR);
                        switch(lore){
                            case OLD_GHASTBOW_LORE:
                            {
                                ItemRewardsQuest.INSTANCE.ghastBowCommand.giveItem(target, new String[]{
                                        target.getName()
                                });
                            }
                            case OLD_VAMPBLADE_LORE:
                            {
                                ItemRewardsQuest.INSTANCE.vampireBladeCommand.giveItem(target, new String[]{
                                        target.getName()
                                });
                            }
                            case OLD_THORHAMMER_LORE:
                            {
                                ItemRewardsQuest.INSTANCE.thorHammerCommand.giveItem(target, new String[]{
                                        target.getName()
                                });
                            }
                            case OLD_WITCHSCYTHE_LORE:
                            {
                                ItemRewardsQuest.INSTANCE.witchScytheCommand.giveItem(target, new String[]{
                                        target.getName()
                                });
                            }
                        }
                        target.sendMessage(ChatColor.GREEN + "Successfully updated the item!");
                        break;
                    }
                }

                commandSender.sendMessage(ChatColor.DARK_RED + "Failed to update item, ensure you are holding a custom item");
            }
        } else {
            commandSender.sendMessage(ChatColor.DARK_RED + "You do not have ItemRewardsQuest.updateitems permissions");
        }
        return true;
    }
}
