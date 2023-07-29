package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.player;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;

public class PlayerSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission("itemrewardsquest.manageplayers") && !commandSender.isOp()){
            commandSender.sendMessage(Constants.PERMISSION_DENIED);
            return;
        }

        boolean flag = false;

        if(args.length == 3){
            if(args[1].equalsIgnoreCase("disallow")){
                if(ItemRewardsQuest.getInstance().blacklistedPlayers.add(args[2].toLowerCase())) {
                    commandSender.sendMessage(Constants.CHAT_PREFIX + "Successfully disallowed " +
                            args[2] + " from using custom items!");
                } else {
                    commandSender.sendMessage(Constants.FAIL_PREFIX + "Player " + args[2] + " is already disallowed!");
                }
            } else if(args[1].equalsIgnoreCase("allow")){
                if(ItemRewardsQuest.getInstance().blacklistedPlayers.remove(args[2].toLowerCase())) {
                    commandSender.sendMessage(Constants.CHAT_PREFIX + "Successfully allowed " +
                            args[2] + " to use custom items!");
                } else {
                    commandSender.sendMessage(Constants.FAIL_PREFIX + "Player " + args[2] + " is already allowed!");
                }
            } else {
                flag = true;
            }
        } else if (args.length == 2){
            if(args[1].equalsIgnoreCase("list")){
                commandSender.sendMessage(Constants.CHAT_PREFIX + "Disallowed players: ");
                int count = 1;
                for(String name : ItemRewardsQuest.getInstance().blacklistedPlayers){
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
            commandSender.sendMessage(Constants.UNKNOWN_SUBCOMMAND);
        }
    }
}
