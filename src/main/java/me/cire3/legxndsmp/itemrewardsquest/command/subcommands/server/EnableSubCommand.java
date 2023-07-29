package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;

public class EnableSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission("itemrewardsquest.reload") && !commandSender.isOp()){
            return;
        }

        ItemRewardsQuest.getInstance().isEnabled = true;
        ItemRewardsQuest.getInstance().itemCommands.subCommands.get("reload").execute(commandSender, args);

        commandSender.sendMessage(Constants.CHAT_PREFIX + "ItemRewardsQuest is now enabled!");
    }
}
