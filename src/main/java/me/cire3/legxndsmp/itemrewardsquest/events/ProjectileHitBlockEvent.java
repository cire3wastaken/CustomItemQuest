package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;

public class ProjectileHitBlockEvent implements Listener {
    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event){
        BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(),
                event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(),
                0.0D, 4);
        Block hitBlock = null;

        while (iterator.hasNext()) {
            hitBlock = iterator.next();

            if (hitBlock.getType() != Material.AIR) {
                break;
            }
        }
        if (event.getEntity() instanceof Arrow) {
            LivingEntity shooter = (LivingEntity) event.getEntity().getShooter();

            if (shooter instanceof Player) {
                Player playerShooter = (Player) shooter;
                if(playerShooter.getItemInHand() == null){
                    return;
                }
                if(!playerShooter.getItemInHand().hasItemMeta()){
                    return;
                }
                if(!playerShooter.getItemInHand().getItemMeta().hasLore()){
                    return;
                }

                List<String> lowerCaseLore = new ArrayList<>();
                for(String str : playerShooter.getItemInHand().getItemMeta().getLore()){
                    lowerCaseLore.add(str.toLowerCase());
                }

                if(lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.ghastBow.lore) &&
                        playerShooter.getItemInHand().getType().equals(Material.BOW)){
                    if(hitBlock == null) return;

                    if(!ItemRewardsQuest.INSTANCE.ghastBow.explosion){
                        World world = playerShooter.getWorld();
                        Location location = event.getEntity().getLocation();

                        TNTPrimed tnt = world.spawn(location, TNTPrimed.class);
                        tnt.setFuseTicks(1);
                        tnt.setCustomName("GhastTNT");
                        tnt.setCustomNameVisible(false);
                    } else {
                        playerShooter.getWorld().createExplosion(hitBlock.getLocation(),
                                (float) ItemRewardsQuest.INSTANCE.ghastBow.explosionPower);
                    }
                }
            }
        }
    }
}
