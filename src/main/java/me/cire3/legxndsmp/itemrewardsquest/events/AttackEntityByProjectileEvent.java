package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class AttackEntityByProjectileEvent implements Listener {
    //Handles ghast bow

    @EventHandler
    public void onAttackEntityByProjectile(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled) return;

        if (event.getDamager() instanceof Projectile) {
            Projectile damager = (Projectile) event.getDamager();
            LivingEntity shooter = (LivingEntity) damager.getShooter();

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

                if(lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.ghastBow.loreConfig) &&
                    playerShooter.getItemInHand().getType().equals(Material.BOW))
                {
                    if(!ItemRewardsQuest.INSTANCE.ghastBow.explosion){
                        World world = playerShooter.getWorld();
                        Location location = event.getEntity().getLocation(); ;

                        TNTPrimed tnt = world.spawn(location, TNTPrimed.class);
                        tnt.setFuseTicks(1);
                        tnt.setCustomName("GhastTNT");
                        tnt.setCustomNameVisible(false);
                    }
                } else {
                    playerShooter.getWorld().createExplosion(event.getEntity().getLocation(), (float) ItemRewardsQuest.INSTANCE.ghastBow.explosionPowerConfig);
                }
            }
        }
    }
}
