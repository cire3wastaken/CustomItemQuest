package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class AttackEntityByProjectileEvent implements Listener {
    //Handles ghast bow

    @EventHandler
    public void onAttackEntityByProjectile(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
            Projectile damager = (Projectile) event.getDamager();
            LivingEntity shooter = (LivingEntity) damager.getShooter();

            if (shooter instanceof Player) {
                Player playerShooter = (Player) shooter;
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
                    playerShooter.getWorld().createExplosion(event.getEntity().getLocation(), (float) ItemRewardsQuest.INSTANCE.ghastBow.explosionPower);

                    ((Player) event.getEntity()).setHealth(((Player) event.getEntity()).getHealth() - 2.0F);
                }
            }
        }
    }
}