package me.cire3.legxndsmp.itemrewardsquest.utils;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String toColor(char replacedChar, String stringToChange){
        StringBuilder string = new StringBuilder();
        for(char c : stringToChange.toCharArray()){
            if(c == replacedChar){
                string.append('§');
            } else {
                string.append(c);
            }
        }
        return string.toString();
    }
}
