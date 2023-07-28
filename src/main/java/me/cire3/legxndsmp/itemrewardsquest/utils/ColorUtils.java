package me.cire3.legxndsmp.itemrewardsquest.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

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

    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static List<String> color(List<String> lore){
        return lore.stream().map(ColorUtils::color).collect(Collectors.toList());
    }
}
