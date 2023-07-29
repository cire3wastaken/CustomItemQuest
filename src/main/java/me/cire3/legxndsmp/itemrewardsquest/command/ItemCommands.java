package me.cire3.legxndsmp.itemrewardsquest.command;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.item.*;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.player.HelpSubCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.player.PlayerSubCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server.DisableSubCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server.EnableSubCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server.RegionSubCommand;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.server.ReloadPluginSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class ItemCommands implements CommandExecutor {
    public final Map<String, SubCommand> subCommands = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!command.getName().equalsIgnoreCase("itemrewardsquest")){
            return false;
        }

        this.execute(commandSender, strings);
        return true;
    }

    public void execute(CommandSender commandSender, String[] args){
        if(args.length == 0){
            this.subCommands.get("help").execute(commandSender, args);
            return;
        }

        SubCommand subCommand = this.subCommands.get(args[0]);

        if(subCommand == null){
            commandSender.sendMessage(Constants.UNKNOWN_COMMAND);
            commandSender.sendMessage(Constants.FAIL_PREFIX + "/itemrewardsquest help");
            return;
        }

        boolean flag = false;
        if(subCommand instanceof EnableSubCommand || args[0].equalsIgnoreCase("enable")){
            subCommand.execute(commandSender, args);
        }

        if(getInstance().isEnabled) {
            subCommand.execute(commandSender, args);
        } else {
            flag = true;
        }
        if(flag){
            commandSender.sendMessage(Constants.DISABLED_MESSAGE);
        }
    }

    public ItemCommands(){
        this.subCommands.put("ghastbow", new GhastBowCommand()); // FINISHED
        this.subCommands.put("hyperion", new HypeCommand()); // FINISHED
        this.subCommands.put("thorhammer", new ThorHammerCommand()); // FINISHED
        this.subCommands.put("vampireblade", new VampireBladeCommand()); // FINISHED
        this.subCommands.put("witchscythe", new WitchScytheCommand()); // FINISHED

        this.subCommands.put("help", new HelpSubCommand()); // FINISHED

        this.subCommands.put("reload", new ReloadPluginSubCommand()); // FINISHED
        this.subCommands.put("enable", new EnableSubCommand()); // FINISHED
        this.subCommands.put("disable", new DisableSubCommand()); // FINISHED

        this.subCommands.put("players", new PlayerSubCommand()); // FINISHED

        this.subCommands.put("regions", new RegionSubCommand()); // FINISHED

        this.subCommands.put("rename", new RenameItemCommand());
    }
}
