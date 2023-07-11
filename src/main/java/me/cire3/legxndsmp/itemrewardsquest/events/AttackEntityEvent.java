package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.items.VampireBlade;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class AttackEntityEvent implements org.bukkit.event.Listener {
    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled) return;

        Entity attacker = event.getDamager();
        Entity victim = event.getEntity();

        if(attacker instanceof Player) {
            Player playerAttacker = (Player) attacker;
            //null checks
            if(playerAttacker.getItemInHand() == null){
                return;
            }
            if (!playerAttacker.getItemInHand().hasItemMeta()){
                return;
            }
            if (!playerAttacker.getItemInHand().getItemMeta().hasLore()) {
                return;
            }

            List<String> lowerCaseLore = new ArrayList<>();
            for(String str : playerAttacker.getItemInHand().getItemMeta().getLore()){
                lowerCaseLore.add(str.toLowerCase());
            }

            // Handles vamp blade
            if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.vampireBlade.lore) &&
                playerAttacker.getItemInHand().getType().equals(Material.DIAMOND_SWORD))
            {
                boolean hasStrength = playerAttacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE);
                int strengthLevel = 0;
                if(hasStrength){
                    for(PotionEffect effect : playerAttacker.getActivePotionEffects()){
                        if(effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)){
                            strengthLevel = Math.max(strengthLevel, effect.getAmplifier());
                        }
                    }
                }
                float strengthIncrease = 1.0F + 1.3F * strengthLevel;

                double[] calcs = DamageUtils.damageCalculator(victim, event.getFinalDamage());
                calcs[1] = calcs[1] * strengthIncrease;

                playerAttacker.setHealth(Math.min(playerAttacker.getHealth() +
                    DamageUtils.calcDamage((int) calcs[0], calcs[1], (int) calcs[2], (int) calcs[3], (int) calcs[4])
                        * ItemRewardsQuest.INSTANCE.vampireBlade.toBeHealed,
                        20.0F));
//                playerAttacker.getHealth() +
//                    2 * DamageUtils.damageCalculator(victim, playerAttacker) * ItemRewardsQuest.INSTANCE.vampireBlade.toBeHealed
            }

            // Handles Thor Hammer
            if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.thorHammer.lore) &&
                playerAttacker.getItemInHand().getType().equals(Material.GOLD_AXE))
            {
                playerAttacker.getWorld().strikeLightning(victim.getLocation());
                if(victim instanceof Player){
                    Player target = (Player) victim;
                    target.setHealth(Math.max(target.getHealth() - ItemRewardsQuest.INSTANCE.thorHammer.damage, 0.0F));
                    target.setFireTicks((int) (Math.random() * 100));
                }
            }

            // Handles witch scythe
            if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.witchScythe.lore) &&
                playerAttacker.getItemInHand().getType().equals(Material.GOLD_HOE))
            {
                if(victim instanceof Player){
                    Player playerVictim = (Player) victim;

                    if(playerVictim.hasPotionEffect(PotionEffectType.POISON)){
                        playerVictim.removePotionEffect(PotionEffectType.POISON);
                    }
                    playerVictim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (int) ItemRewardsQuest.INSTANCE.witchScythe.secondsOfEffect * 20, 2));
                }
            }
        }
    }
}
