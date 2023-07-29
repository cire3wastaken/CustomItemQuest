package me.cire3.legxndsmp.itemrewardsquest;

import org.bukkit.ChatColor;

public class Constants {
    public static final String CHAT_PREFIX = ChatColor.BOLD.toString() + ChatColor.GREEN +
            "[ItemRewardsQuest] >> " + ChatColor.RESET;
    public static final String FAIL_PREFIX = ChatColor.BOLD.toString() + ChatColor.RED + "[ItemRewardsQuest] >> " + ChatColor.RESET;
    public static final String DISABLED_MESSAGE = FAIL_PREFIX + "ItemRewardsQuest is currently disabled!";
    public static final String PERMISSION_DENIED = FAIL_PREFIX + "You do not have permissions!";
    public static final String UNKNOWN_COMMAND = FAIL_PREFIX + "Unknown command!";
    public static final String UNKNOWN_SUBCOMMAND = FAIL_PREFIX + "Unknown sub command!";
    public static final String CAN_NOT_USE = FAIL_PREFIX + "You can not use this item here!";
    public static final String BLACKLISTED = FAIL_PREFIX + "You are blacklisted from using custom items!";
    public static final String DISABLED_ITEM = FAIL_PREFIX + "This item is disabled!";

    public static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/cire3wastaken/CustomItemQuest/master/version.txt";
    public static final String GITHUB_REPO = "https://github.com/cire3wastaken/CustomItemQuest/tree/master";
    public static final String PLUGIN_VERSION = "1.0.0";
    public static final String OUTDATED_MESSAGE = FAIL_PREFIX +
            "ItemRewardsQuest is not up to date! Build it yourself from " + GITHUB_REPO + " or download it from Releases!";
}
