package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.player;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class PlayerSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission("itemrewardsquest.manageplayers") && !commandSender.isOp()){
            commandSender.sendMessage(PERMISSION_DENIED);
            return;
        }

        boolean flag = false;

        if(args.length == 3){
            if(args[1].equalsIgnoreCase("disallow")){
                if(ItemRewardsQuest.INSTANCE.blacklistedPlayers.add(args[2].toLowerCase())) {
                    commandSender.sendMessage(CHAT_PREFIX + "Successfully disallowed " +
                            args[2] + " from using custom items!");
                } else {
                    commandSender.sendMessage(FAIL_PREFIX + "Player " + args[2] + " is already disallowed!");
                }
            } else if(args[1].equalsIgnoreCase("allow")){
                if(ItemRewardsQuest.INSTANCE.blacklistedPlayers.remove(args[2].toLowerCase())) {
                    commandSender.sendMessage(CHAT_PREFIX + "Successfully allowed " +
                            args[2] + " to use custom items!");
                } else {
                    commandSender.sendMessage(FAIL_PREFIX + "Player " + args[2] + " is already allowed!");
                }
            } else if (args[1].equalsIgnoreCase("reach")){

            } else {
                flag = true;
            }
        } else if (args.length == 2){
            if(args[1].equalsIgnoreCase("list")){
                commandSender.sendMessage(CHAT_PREFIX + "Disallowed players: ");
                int count = 1;
                for(String name : INSTANCE.blacklistedPlayers){
                    commandSender.sendMessage("#" + count + ": " + name);
                    count++;
                }
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }

        if(flag){
            commandSender.sendMessage(UNKNOWN_SUBCOMMAND);
        }
    }
}
