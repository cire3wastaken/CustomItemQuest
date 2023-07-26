package me.cire3.legxndsmp.itemrewardsquest.command;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.GhastBowCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.ThorHammerCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.VampireBladeCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.WitchScytheCommand;
import me.cire3.legxndsmp.itemrewardsquest.events.RightClickInteractEvent;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
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

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class ConvertCommand implements CommandExecutor {
    public static final String OLD_VAMPBLADE_LORE = ColorUtils.color("&6Ability: gain half as much HP as you do damage");
    public static final String OLD_GHASTBOW_LORE = ColorUtils.color("&eAbility: create explosions upon whom your arrow lands on");
    public static final String OLD_THORHAMMER_LORE = ColorUtils.color("&cAbility: incur lightning upon your victims");
    public static final String OLD_WITCHSCYTHE_LORE = ColorUtils.color("&aAbility: inflict poison on your victims");

    public static final List<String> OLD_LORE = Arrays.asList(OLD_GHASTBOW_LORE, OLD_THORHAMMER_LORE,
            OLD_VAMPBLADE_LORE, OLD_WITCHSCYTHE_LORE);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            commandSender.sendMessage(DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("updateitem")) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 0){
            commandSender.sendMessage(FAIL_PREFIX + "This command requires no arguments");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.updateitems") || commandSender.isOp()){
            if(commandSender instanceof Player){
                Player target = (Player) commandSender;
                if(target.getItemInHand() == null || !target.getItemInHand().hasItemMeta() ||
                        !target.getItemInHand().getItemMeta().hasLore())
                {
                    commandSender.sendMessage(FAIL_PREFIX + "Please hold an old item to update!");
                    return true;
                }

                ItemMeta meta = target.getItemInHand().getItemMeta();
                for(String str : meta.getLore()){
                    for(String lore : OLD_LORE) {
                        if (str.equalsIgnoreCase(lore)) {
                            if(lore.equalsIgnoreCase(OLD_GHASTBOW_LORE)) {
                                ((GhastBowCommand) INSTANCE.itemCommands.subCommands.get("ghastbow"))
                                    .giveItem(commandSender, new String[]{ "", "", commandSender.getName() },
                                        meta
                                );
                            } else if(lore.equalsIgnoreCase(OLD_VAMPBLADE_LORE)) {
                                ((VampireBladeCommand) INSTANCE.itemCommands.subCommands.get("vampireblade"))
                                    .giveItem(commandSender, new String[]{ "", "", commandSender.getName() },
                                        meta
                                );
                            } else if(lore.equalsIgnoreCase(OLD_THORHAMMER_LORE)) {
                                ((ThorHammerCommand) INSTANCE.itemCommands.subCommands.get("thorhammer"))
                                    .giveItem(commandSender, new String[]{ "", "", commandSender.getName() },
                                        meta
                                );
                            } else if(lore.equalsIgnoreCase(OLD_WITCHSCYTHE_LORE)) {
                                ((WitchScytheCommand) INSTANCE.itemCommands.subCommands.get("witchscythe"))
                                    .giveItem(commandSender, new String[]{ "", "", commandSender.getName() },
                                        meta
                                );
                            }

                            target.sendMessage(CHAT_PREFIX + "Successfully updated the item!");
                            return true;
                        }
                    }
                }

                commandSender.sendMessage(FAIL_PREFIX +
                        "Failed to update item, ensure you are holding an old item!");
            }
        } else {
            commandSender.sendMessage(PERMISSION_DENIED);
        }
        return true;
    }
}
