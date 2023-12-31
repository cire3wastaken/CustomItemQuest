package me.cire3.legxndsmp.itemrewardsquest.utils;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

public class FileUtils {
    public static void downloadUsingStream(String urlStr, File fileToWrite) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(fileToWrite);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bufferedInputStream.read(buffer, 0, 1024)) != -1) {
            fileOutputStream.write(buffer, 0, count);
        }
        fileOutputStream.close();
        bufferedInputStream.close();
    }

    public static boolean createNewFile(File fileL) {
        try {
            if (fileL.createNewFile()) {
                Bukkit.getLogger().info("Created " + fileL.getAbsolutePath());
            }
            return FileUtils.writeVersion(fileL);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Error occurred in creating " +
                fileL.getAbsolutePath());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeVersion(File file){
        try {
            FileWriter myWriter = new FileWriter(file, false);
            myWriter.write(Constants.PLUGIN_VERSION);
            myWriter.close();
            Bukkit.getLogger().info("Successfully wrote " + Constants.PLUGIN_VERSION + " to " +
                    file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Error occurred in writing to " +
                    file.getAbsolutePath());
            e.printStackTrace();
            return false;
        }
    }

    public static String getVersion(File fileV){
        try {
            Scanner var2 = new Scanner(fileV);
            String var3 = var2.nextLine();
            var2.close();
            return var3;
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.WARNING, "Could not find " + fileV.getAbsolutePath());
            e.printStackTrace();
            return String.valueOf(Integer.MAX_VALUE);
        }
    }
}
