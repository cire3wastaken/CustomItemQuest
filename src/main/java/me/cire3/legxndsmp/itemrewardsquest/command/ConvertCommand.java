package me.cire3.legxndsmp.itemrewardsquest.command;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.GhastBowCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.ThorHammerCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.VampireBladeCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.WitchScytheCommand;
import me.cire3.legxndsmp.itemrewardsquest.items.GhastBow;
import me.cire3.legxndsmp.itemrewardsquest.items.ThorHammer;
import me.cire3.legxndsmp.itemrewardsquest.items.VampireBlade;
import me.cire3.legxndsmp.itemrewardsquest.items.WitchScythe;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ConvertCommand implements CommandExecutor {

    public static final List<List<String>> OLD_LORE = Arrays.asList(VampireBlade.oldLore, ThorHammer.oldLore,
            GhastBow.oldLore, WitchScythe.oldLore);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!ItemRewardsQuest.getInstance().isEnabled){
            commandSender.sendMessage(Constants.DISABLED_MESSAGE);
            return true;
        }

        if(!command.getName().equalsIgnoreCase("updateitem")) {
            commandSender.sendMessage(Constants.UNKNOWN_COMMAND);
            return false;
        }
        if(strings.length != 0){
            commandSender.sendMessage(Constants.FAIL_PREFIX + "This command requires no arguments");
            return false;
        }

        if(commandSender.hasPermission("itemrewardsquest.updateitems") || commandSender.isOp()){
            if(commandSender instanceof Player){
                Player target = (Player) commandSender;
                if(target.getItemInHand() == null || !target.getItemInHand().hasItemMeta() ||
                        !target.getItemInHand().getItemMeta().hasLore())
                {
                    commandSender.sendMessage(Constants.FAIL_PREFIX + "Please hold an old item to update!");
                    return true;
                }

                ItemMeta meta = target.getItemInHand().getItemMeta();
                for(List<String> lore : OLD_LORE){
                    if(meta.getLore().equals(lore)){
                        if(lore.equals(VampireBlade.oldLore)){
                            ((VampireBladeCommand) ItemRewardsQuest.getInstance().itemCommands.subCommands.get("vampireblade"))
                                .giveItem(commandSender, new String[]{ "", "", target.getName()}
                            );
                        } else if (lore.equals(ThorHammer.oldLore)){
                            ((ThorHammerCommand) ItemRewardsQuest.getInstance().itemCommands.subCommands.get("thorhammer"))
                                .giveItem(commandSender, new String[]{ "", "", target.getName()}
                            );
                        } else if (lore.equals(GhastBow.oldLore)){
                            ((GhastBowCommand) ItemRewardsQuest.getInstance().itemCommands.subCommands.get("ghastbow"))
                                .giveItem(commandSender, new String[]{ "", "", target.getName()}
                            );
                        } else if (lore.equals(WitchScythe.oldLore)){
                            ((WitchScytheCommand) ItemRewardsQuest.getInstance().itemCommands.subCommands.get("witchscythe"))
                                .giveItem(commandSender, new String[]{ "", "", target.getName()}
                            );
                        }
                    }
                }

                commandSender.sendMessage(Constants.FAIL_PREFIX +
                        "Failed to update item, ensure you are holding an old item!");
            }
        } else {
            commandSender.sendMessage(Constants.PERMISSION_DENIED);
        }
        return true;
    }
}
