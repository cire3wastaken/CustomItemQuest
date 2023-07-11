package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TntExplodeEvents implements Listener {
    Set<UUID> noDamageEntity = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @EventHandler
    public final void explode(EntityExplodeEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) entity;
            String name = tnt.getCustomName();
            if (name != null) {
                if (name.equals("GhastTNT")) {
                    for (Block block : e.blockList()) {
                        for(Entity ent : block.getWorld().getNearbyEntities(block.getLocation(), 1d, 1d, 1d)){
                            noDamageEntity.add(ent.getUniqueId());
                        }
                    }
                    e.blockList().clear();
                }
            }
        }
    }

    @EventHandler
    public final void damage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (noDamageEntity.contains(entity.getUniqueId())) {
            e.setCancelled(true);
            noDamageEntity.remove(entity.getUniqueId());
            if(entity instanceof LivingEntity){
                ((LivingEntity) entity).damage(ItemRewardsQuest.INSTANCE.ghastBow.damageConfig);
            }
        }
    }
}
