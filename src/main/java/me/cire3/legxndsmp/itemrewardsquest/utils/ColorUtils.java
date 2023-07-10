package me.cire3.legxndsmp.itemrewardsquest.utils;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String toColor(char replacedChar, String stringToChange){
        return ChatColor.translateAlternateColorCodes(replacedChar, stringToChange);
    }
}
