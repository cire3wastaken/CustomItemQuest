package me.cire3.legxndsmp.itemrewardsquest.utils;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginAwareness;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;

public class PlayerUtils {
    public static Block[] getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastAirBlock = player.getLocation().getBlock();
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastAirBlock = lastBlock;
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return new Block[]{lastAirBlock, lastBlock};
    }

    public static ArrayList<LivingEntity> getNearbyLivingEntities(Player pl, double range){
        ArrayList<LivingEntity> nearby = new ArrayList<>();
        for (Entity e : pl.getNearbyEntities(range, range, range)){
            if (e instanceof LivingEntity){
                nearby.add((LivingEntity) e);
            }
        }
        return nearby;
    }

    public static boolean isInNonPvpRegion(Player p){
        RegionContainer container = WGBukkit.getPlugin().getRegionContainer();
        RegionQuery query = container.createQuery();

        return query.testState(p.getLocation(), p, DefaultFlag.PVP);
    }

    public static boolean isInProtectedRegion(Player p){
        RegionContainer container = WGBukkit.getPlugin().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
        for (ProtectedRegion region : set){
            if(ItemRewardsQuest.INSTANCE.protectedRegions.contains(region.getId())){
                return true;
            }
        }

        return query.testState(p.getLocation(), p, DefaultFlag.BLOCK_BREAK) &&
                query.testState(p.getLocation(), p, DefaultFlag.BLOCK_PLACE) &&
                query.testState(p.getLocation(), p, DefaultFlag.BUILD);
    }
}
