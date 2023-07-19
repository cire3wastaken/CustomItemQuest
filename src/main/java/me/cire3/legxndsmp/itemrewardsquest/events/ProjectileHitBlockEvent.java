package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CAN_NOT_USE;
import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CHAT_PREFIX;

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

                if(lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.ghastBow.loreConfig) &&
                        playerShooter.getItemInHand().getType().equals(Material.BOW)){
                    if(!PlayerUtils.shouldUse(playerShooter))
                    {
                        playerShooter.sendMessage(ChatColor.RED + CAN_NOT_USE);
                        return;
                    }

                    if(hitBlock == null) return;

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
                        for(int i = 0; i < 15; i++){
                            world.playEffect(location, effect, 3);
                        }

                        world.playSound(location, Sound.EXPLODE, 1F, 1F);

                        for (Entity nearby: world.getNearbyEntities(location, power, power, power)) {
                            if (nearby instanceof LivingEntity) {
                                LivingEntity entity = (LivingEntity) nearby;

                                entity.damage(ItemRewardsQuest.INSTANCE.ghastBow.damageConfig *
                                        (entity.getLocation().distanceSquared(hitBlock.getLocation()) / 100F));
                            }
                        }
                    } else {
                        playerShooter.getWorld().createExplosion(hitBlock.getLocation(), power);
                    }
                }
            }
        }
    }
}
