package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item;

import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.UNKNOWN_COMMAND;
import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.UNKNOWN_SUBCOMMAND;

public class RenameItemCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) return;
        Player player = (Player) commandSender;
        if(player.getItemInHand() == null) return;

        if(args.length > 1) {
            if (args[0].equalsIgnoreCase("rename")) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String string : args) {
                    if (!string.equalsIgnoreCase("rename"))
                        stringBuilder.append(ColorUtils.color(string)).append(" ");
                }

                ItemStack currentItem = player.getItemInHand();
                ItemMeta meta = currentItem.getItemMeta();
                meta.setDisplayName(stringBuilder.toString());
                currentItem.setItemMeta(meta);
            } else {
                commandSender.sendMessage(UNKNOWN_SUBCOMMAND);
            }
        } else {
            commandSender.sendMessage(UNKNOWN_COMMAND);
        }
    }
}
