package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;

public class DisableSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission("itemrewardsquest.reload") && !commandSender.isOp()){
            return;
        }

        ItemRewardsQuest.getInstance().isEnabled = false;
        commandSender.sendMessage(Constants.CHAT_PREFIX + "ItemRewardsQuest has been disabled!");
    }
}
