package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.items.Items;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.math.BigDecimal;
import java.util.Collection;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;
import static me.cire3.legxndsmp.itemrewardsquest.command.ConvertCommand.OLD_GHASTBOW_LORE;

public class AttackEntityByProjectileEvent implements Listener {
    //Handles ghast bow

    @EventHandler(priority = EventPriority.HIGH)
    public void onAttackEntityByProjectile(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled) return;
        if(event.isCancelled()) return;

        if(event.getDamager() instanceof Projectile){
            if(((Projectile) event.getDamager()).getShooter() instanceof Player){
                Player playerShooter = (Player) ((Projectile) event.getDamager()).getShooter();
                if("aksjfuaqialfkiaGhastBowShot".equalsIgnoreCase(event.getEntity().getCustomName())){

                    if(!PlayerUtils.shouldUse(playerShooter))
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

                    if(INSTANCE.isDisabled(Items.WITCHSCYHTE)){
                        playerShooter.sendMessage(DISABLED_ITEM);
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
                                if(nearby instanceof Player){
                                    if(!PlayerUtils.shouldUse((Player) nearby)){
                                        continue;
                                    }
                                }

                                LivingEntity entity = (LivingEntity) nearby;
                                BigDecimal healthBefore = BigDecimal.valueOf(entity.getHealth());

                                entity.damage(ItemRewardsQuest.INSTANCE.ghastBow.damageConfig * ((100F -
                                    entity.getLocation().distanceSquared(event.getEntity().getLocation()) * 3) / 100F),
                                        playerShooter
                                );

                                BigDecimal healthAfter = BigDecimal.valueOf(entity.getHealth());

                                // Checks that it wasn't cancelled
                                if(healthAfter.compareTo(healthBefore) < 0){
                                    Vector affectedLoc = entity.getLocation().toVector();
                                    Vector abilityLoc = playerShooter.getLocation().toVector();
                                    Vector result = affectedLoc.subtract(abilityLoc);
                                    entity.setVelocity(result);
                                }
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
