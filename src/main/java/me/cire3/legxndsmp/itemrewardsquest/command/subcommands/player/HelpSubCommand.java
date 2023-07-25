package me.cire3.legxndsmp.itemrewardsquest.command.subcommands.player;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.command.subcommands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;

public class HelpSubCommand implements SubCommand {
    public final List<String> itemSub = Arrays.asList("ghastbow", "hyperion", "vampireblade", "thorhammer", "witchscythe");

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("itemrewardsquest.updateitems") || commandSender.isOp()) {
            boolean flag = false;
            if (args.length == 1 || args.length == 0) {
                commandSender.sendMessage(CHAT_PREFIX + "ItemRewardsQuest Help Menu: Made by cire3 (sire3#0000)");
                commandSender.sendMessage(makeHelpString("help", "Opens this help menu"));
                commandSender.sendMessage("");

                commandSender.sendMessage(makeHelpString("ghastbow [give/toggle/state]",
                    "Commands for the Ghast Bow"));
                commandSender.sendMessage(makeHelpString("hyperion [give/toggle/state]",
                    "Commands for the Hyperion"));
                commandSender.sendMessage(makeHelpString("witchscythe [give/toggle/state]",
                    "Commands for the Witch Scythe"));
                commandSender.sendMessage(makeHelpString("vampireblade [give/toggle/state]",
                    "Commands for the Vampire Blade"));
                commandSender.sendMessage(makeHelpString("thorhammer [give/toggle/state]",
                    "Commands for the Thor Hammer"));
                commandSender.sendMessage("");

                commandSender.sendMessage(makeHelpString("reload", "Reloads the plugin"));
                commandSender.sendMessage(makeHelpString("disable", "Disables item functionality"));
                commandSender.sendMessage(makeHelpString("enable", "Enables item functionality"));
                commandSender.sendMessage("");

                commandSender.sendMessage(makeHelpString("players [disallow/allow/list]",
                    "Commands to manipulate players"));
                commandSender.sendMessage(makeHelpString("regions [whitelist/blacklist/world]",
                    "Commands to manipulate regions"));
            } else if(args.length == 2) {
                for (String str : ItemRewardsQuest.INSTANCE.itemCommands.subCommands.keySet()) {
                    if (str.equalsIgnoreCase(args[1])) {
                        if (this.itemSub.contains(str.toLowerCase())) {
                            commandSender.sendMessage(makeTitleString(str));
                            commandSender.sendMessage(makeSubString("give (player)", "Gives a player the item"));
                            commandSender.sendMessage(makeSubString("toggle", "Toggles usage of this item"));
                            commandSender.sendMessage(makeSubString("state", "Returns the usage state of this item"));

                        } else if (str.equalsIgnoreCase("regions")) {
                            commandSender.sendMessage(makeTitleString("regions"));
                            commandSender.sendMessage(makeSubString("whitelist [add/remove/list]",
                                    "Commands to manipulate whitelisted regions"));
                            commandSender.sendMessage(makeSubString("blacklist [add/remove/list]",
                                    "Commands to manipulate blacklisted regions"));
                            commandSender.sendMessage(makeSubString("world", "Returns the world ID"));

                        } else if (str.equalsIgnoreCase("players")) {
                            commandSender.sendMessage(makeTitleString("players"));
                            commandSender.sendMessage(makeSubString("disallow (player)", "Prevents a player from using custom items"));
                            commandSender.sendMessage(makeSubString("allow (player)", "Allows a player to use custom items"));
                            commandSender.sendMessage(makeSubString("list", "Lists disallowed players"));

                        } else if (str.equalsIgnoreCase("reload")) {
                            commandSender.sendMessage(makeTitleString("reload"));
                            commandSender.sendMessage(makeHelpString("reload", "Reloads the plugin"));

                        } else if (str.equalsIgnoreCase("disable")) {
                            commandSender.sendMessage(makeTitleString("disable"));
                            commandSender.sendMessage(makeHelpString("disable", "Disables item functionality"));

                        } else if (str.equalsIgnoreCase("enable")) {
                            commandSender.sendMessage(makeTitleString("enable"));
                            commandSender.sendMessage(makeHelpString("enable", "Enables item functionality"));
                        }

                        break;
                    }
                }
                if(!ItemRewardsQuest.INSTANCE.itemCommands.subCommands.containsKey(args[1])) flag = true;
            } else if (args.length == 3){
                if(args[1].equalsIgnoreCase("regions")){
                    if(args[2].equalsIgnoreCase("whitelist")){
                        commandSender.sendMessage(makeSubString("add (region) (world)",
                                "Adds a whitelisted region"));
                        commandSender.sendMessage(makeSubString("remove (region) (world)",
                                "Removes a whitelisted region"));
                        commandSender.sendMessage(makeSubString("list",
                                "Lists whitelisted regions"));

                    } else if(args[2].equalsIgnoreCase("blacklist")){
                        commandSender.sendMessage(makeSubString("add (region) (world)", "Adds a blacklisted region"));
                        commandSender.sendMessage(makeSubString("remove (region) (world)", "Removes a blacklisted region"));
                        commandSender.sendMessage(makeSubString("list", "Lists blacklisted regions"));

                    } else if(args[2].equalsIgnoreCase("world")){
                        commandSender.sendMessage(makeSubString("world", "Returns the world ID"));
                    } else {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }

            if(flag)
                commandSender.sendMessage(UNKNOWN_SUBCOMMAND);
        }
    }

    public static String makeHelpString(String cmd, String desc){
        return makeSubString("/itemrewardsquest " + cmd, desc);
    }

    public static String makeTitleString(String cmd){
        return ChatColor.BOLD + ChatColor.RED.toString() + "Help menu for " + cmd + ": ";
    }

    public static String makeSubString(String cmd, String desc){
        return ChatColor.GOLD + cmd + ChatColor.DARK_GRAY + " - " +
                ChatColor.WHITE + desc;
    }
}
