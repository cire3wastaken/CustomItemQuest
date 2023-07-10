package me.cire3.legxndsmp.itemrewardsquest.utils;

public class ColorUtils {
    public String toColor(char replacedChar, String stringToChange){
        char[] stringArray = stringToChange.toCharArray();
        StringBuilder changedString = new StringBuilder();

        for(char c : stringArray){
            if(c == replacedChar){
                changedString.append("§");
            } else {
                changedString.append(c);
            }
        }

        return changedString.toString();
    }
}
