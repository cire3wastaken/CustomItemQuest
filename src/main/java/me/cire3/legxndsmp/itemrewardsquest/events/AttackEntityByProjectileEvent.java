package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.items.GhastBow;
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

public class AttackEntityByProjectileEvent implements Listener {
    //Handles ghast bow

    @EventHandler(priority = EventPriority.HIGH)
    public void onAttackEntityByProjectile(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.getInstance().isEnabled) return;
        if(event.isCancelled()) return;

        if(event.getDamager() instanceof Projectile){
            if(((Projectile) event.getDamager()).getShooter() instanceof Player){
                Player playerShooter = (Player) ((Projectile) event.getDamager()).getShooter();
                if("aksjfuaqialfkiaGhastBowShot".equalsIgnoreCase(event.getEntity().getCustomName())){

                    if(!PlayerUtils.shouldUse(playerShooter))
                    {
                        if(ItemRewardsQuest.getInstance().hasCooldown(playerShooter)) return;

                        playerShooter.sendMessage(ChatColor.RED + Constants.CAN_NOT_USE);
                        ItemRewardsQuest.getInstance().activateCooldown(playerShooter);
                        return;
                    }

                    if(PlayerUtils.containsLore(playerShooter.getItemInHand(), GhastBow.oldLore)){
                        playerShooter.sendMessage(Constants.FAIL_PREFIX +
                                "This items abilities are nullified due to being outdated. " +
                                "Use /updateitem while holding it to update it.");
                        return;
                    }

                    if(ItemRewardsQuest.getInstance().isBlacklisted(playerShooter)){
                        if(ItemRewardsQuest.getInstance().hasCooldown(playerShooter)) return;

                        playerShooter.sendMessage(ChatColor.RED + Constants.BLACKLISTED);
                        ItemRewardsQuest.getInstance().activateCooldown(playerShooter);
                        return;
                    }

                    if(ItemRewardsQuest.getInstance().isDisabled(Items.WITCHSCYHTE)){
                        playerShooter.sendMessage(Constants.DISABLED_ITEM);
                        return;
                    }

                    World world = playerShooter.getWorld();
                    Location location = event.getEntity().getLocation();

                    float power;
                    Effect effect;

                    switch((int) GhastBow.explosionPowerConfig){
                        case 2: power = 5; effect = Effect.EXPLOSION_LARGE; break;
                        case 3: power = 6; effect = Effect.EXPLOSION_HUGE; break;
                        default: effect = Effect.EXPLOSION; power = 4; break;
                    }

                    if(!GhastBow.explosion){
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

                                entity.damage(GhastBow.damageConfig * ((100F -
                                    entity.getLocation().distanceSquared(event.getEntity().getLocation()) * 3) / 100F),
                                        playerShooter
                                );

                                BigDecimal healthAfter = BigDecimal.valueOf(entity.getHealth());

                                // Checks that it wasn't cancelled
                                if(healthAfter.compareTo(healthBefore) < 0){
                                    Vector affectedLoc = entity.getLocation().toVector();
                                    Vector abilityLoc = event.getDamager().getLocation().toVector();
                                    Vector result = affectedLoc.subtract(abilityLoc).divide(new Vector(2, 2, 2));
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
