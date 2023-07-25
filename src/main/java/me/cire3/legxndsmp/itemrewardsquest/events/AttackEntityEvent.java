package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;
import static me.cire3.legxndsmp.itemrewardsquest.command.player.ConvertCommand.*;

public class AttackEntityEvent implements org.bukkit.event.Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled) return;
        if(event.isCancelled()) return;

        if(event.getDamager() instanceof Player) {
            Player playerAttacker = (Player) event.getDamager();

            if(!PlayerUtils.shouldUse(playerAttacker))
            {
                if(ItemRewardsQuest.INSTANCE.hasCooldown(playerAttacker)) return;

                playerAttacker.sendMessage(ChatColor.RED + CAN_NOT_USE);
                ItemRewardsQuest.INSTANCE.activateCooldown(playerAttacker);
                return;
            }

            if(ItemRewardsQuest.INSTANCE.isBlacklisted(playerAttacker)){
                if(ItemRewardsQuest.INSTANCE.hasCooldown(playerAttacker)) return;

                playerAttacker.sendMessage(ChatColor.RED + BLACKLISTED);
                ItemRewardsQuest.INSTANCE.activateCooldown(playerAttacker);
                return;
            }
            Entity victim = event.getEntity();

            if(PlayerUtils.containsString(playerAttacker.getItemInHand(), OLD_THORHAMMER_LORE) ||
                PlayerUtils.containsString(playerAttacker.getItemInHand(), OLD_WITCHSCYTHE_LORE) ||
                PlayerUtils.containsString(playerAttacker.getItemInHand(), OLD_VAMPBLADE_LORE) )
            {
                playerAttacker.sendMessage(FAIL_PREFIX +
                        "This items abilities are nullified due to being outdated. " +
                        "Use /updateitem while holding it to update it.");
                return;
            }

            if(victim instanceof Player){
                if(!PlayerUtils.shouldUse((Player) victim)){
                    return;
                }
            }

            double[] c = DamageUtils.damageCalculator(victim, event.getFinalDamage() - 2);
            c[1] = c[1] * DamageUtils.strengthIncrease(playerAttacker);

            // Handles vamp blade
            if (PlayerUtils.containsLore(playerAttacker.getItemInHand(), ItemRewardsQuest.INSTANCE.vampireBlade.lore) &&
                    playerAttacker.getItemInHand().getType().equals(Material.DIAMOND_SWORD))
            {
                playerAttacker.setHealth(Math.min(playerAttacker.getHealth() +
                    Math.min(Math.max(DamageUtils.calcDamage((int) c[0], c[1], 0, (int) c[3],
                            (int) c[4]) * ItemRewardsQuest.INSTANCE.vampireBlade.toBeHealed,
                        playerAttacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) ?
                            (Math.random() >= 0.5 ? 2.0F : 1.0F) :
                                (Math.random() >= 0.5 ? 1.5F : 1.0F)), 6.0F), 20.0F));
            }

            // Handles Thor Hammer
            if (PlayerUtils.containsLore(playerAttacker.getItemInHand(), ItemRewardsQuest.INSTANCE.thorHammer.lore) &&
                    playerAttacker.getItemInHand().getType().equals(Material.GOLD_AXE))
            {
                playerAttacker.getWorld().strikeLightningEffect(victim.getLocation());
                if(victim instanceof LivingEntity){
                    LivingEntity target = (LivingEntity) victim;

                    if(ItemRewardsQuest.INSTANCE.thorHammer.ignoreArmor){
                        target.setHealth(Math.max(target.getHealth() - ItemRewardsQuest.INSTANCE.thorHammer.damage, 0));
                    } else {
                        target.damage(ItemRewardsQuest.INSTANCE.thorHammer.damage);
                    }
                    target.setFireTicks(((int) Math.floor(ItemRewardsQuest.INSTANCE.thorHammer.fireTicks)));
                }
            }

            // Handles witch scythe
            if (PlayerUtils.containsLore(playerAttacker.getItemInHand(), ItemRewardsQuest.INSTANCE.witchScythe.lore) &&
                    playerAttacker.getItemInHand().getType().equals(Material.GOLD_HOE))
            {
                if(victim instanceof LivingEntity){
                    LivingEntity playerVictim = (LivingEntity) victim;

                    if(playerVictim.hasPotionEffect(PotionEffectType.POISON)){
                        playerVictim.removePotionEffect(PotionEffectType.POISON);
                    }
                    playerVictim.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
                            (int) Math.ceil(ItemRewardsQuest.INSTANCE.witchScythe.secondsOfEffect * 20), 4));
                }
            }
        }
    }
}

