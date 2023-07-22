package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CAN_NOT_USE;
import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CHAT_PREFIX;

public class AttackEntityByProjectileEvent implements Listener {
    //Handles ghast bow

    @EventHandler
    public void onAttackEntityByProjectile(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled) return;

        if (event.getDamager() instanceof Projectile) {
            Projectile damager = (Projectile) event.getDamager();

            if (damager.getShooter() instanceof Player) {
                Player playerShooter = (Player) damager.getShooter();

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
                    if(!PlayerUtils.shouldUse(playerShooter))
                    {
                        if(ItemRewardsQuest.INSTANCE.hasCooldown(playerShooter)) return;

                        playerShooter.sendMessage(ChatColor.RED + CAN_NOT_USE);
                        ItemRewardsQuest.INSTANCE.activateCooldown(playerShooter);
                        return;
                    }

                    World world = playerShooter.getWorld();
                    Location location = event.getEntity().getLocation();

                    float power;
                    Effect effect;

                    switch((int) ItemRewardsQuest.INSTANCE.ghastBow.explosionPowerConfig){
                        case 2: power = 5; effect = Effect.EXPLOSION_LARGE; break;
                        case 3: power = 6; effect = Effect.EXPLOSION_HUGE; break;
                        default: effect = Effect.EXPLOSION; power = 4; break;
                    }

                    if(!ItemRewardsQuest.INSTANCE.ghastBow.explosion){
                        world.playEffect(location, effect, 3);
                        world.playSound(location, Sound.EXPLODE, 1F, 1F);

                        Collection<Entity> collection =  world.getNearbyEntities(location, power, power, power);
                        collection.remove(playerShooter);

                        for (Entity nearby: collection) {
                            if (nearby instanceof LivingEntity) {
                                LivingEntity entity = (LivingEntity) nearby;
                                entity.damage(ItemRewardsQuest.INSTANCE.ghastBow.damageConfig *
                                        ((100F - entity.getLocation().distanceSquared(event.getEntity().getLocation())) / 100F));
                            }
                        }
                    } else {
                        playerShooter.getWorld().createExplosion(event.getEntity().getLocation(), power);
                    }
                }
            }
        }
    }
}
