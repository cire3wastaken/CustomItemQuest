package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;

public class EnableSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ItemRewardsQuest.INSTANCE.isEnabled = true;
        ItemRewardsQuest.INSTANCE.enable();
    }
}
