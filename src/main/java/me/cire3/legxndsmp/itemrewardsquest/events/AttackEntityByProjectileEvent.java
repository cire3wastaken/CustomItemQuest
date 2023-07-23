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

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;
import static me.cire3.legxndsmp.itemrewardsquest.command.player.ConvertCommand.OLD_GHASTBOW_LORE;

public class AttackEntityByProjectileEvent implements Listener {
    //Handles ghast bow

    @EventHandler
    public void onAttackEntityByProjectile(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled) return;

        if (event.getDamager() instanceof Projectile) {
            Projectile damager = (Projectile) event.getDamager();

            if (damager.getShooter() instanceof Player) {
                Player playerShooter = (Player) damager.getShooter();

                if(!PlayerUtils.shouldUse(playerShooter) || !PlayerUtils.shouldUse(event.getEntity().getLocation()))
                {
                    if(ItemRewardsQuest.INSTANCE.hasCooldown(playerShooter)) return;

                    playerShooter.sendMessage(ChatColor.RED + CAN_NOT_USE);
                    ItemRewardsQuest.INSTANCE.activateCooldown(playerShooter);
                    return;
                }

                if(PlayerUtils.containsString(playerShooter.getItemInHand(), OLD_GHASTBOW_LORE)){
                    playerShooter.sendMessage(FAIL_PREFIX +
                            "This items abilities are nullified due to being outdated. " +
                            "Use /updateitem while holding it to update it.");
                    return;
                }

                if(ItemRewardsQuest.INSTANCE.isBlacklisted(playerShooter)){
                    if(ItemRewardsQuest.INSTANCE.hasCooldown(playerShooter)) return;

                    playerShooter.sendMessage(ChatColor.RED + BLACKLISTED);
                    ItemRewardsQuest.INSTANCE.activateCooldown(playerShooter);
                    return;
                }

                if(PlayerUtils.containsLore(playerShooter.getItemInHand(), ItemRewardsQuest.INSTANCE.ghastBow.lore)
                    && playerShooter.getItemInHand().getType().equals(Material.BOW))
                {
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
                                if(nearby instanceof Player){
                                    if(!PlayerUtils.shouldUse((Player) nearby)){
                                        continue;
                                    }
                                }

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
