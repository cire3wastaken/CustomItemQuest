package me.cire3.legxndsmp.itemrewardsquest.utils;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;

import java.util.List;

public class ConfigurationHelper {
    public static List<String> getStringList(String path, List<String> def){
        List<String> val = ItemRewardsQuest.getInstance().getConfig().getStringList(path);
        return val != null ? val : def;
    }
}
