package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.items.VampireBlade;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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
        if(!ItemRewardsQuest.INSTANCE.isEnabled){
            return;
        }

        Entity attacker = event.getDamager();
        Entity victim = event.getEntity();

        // Handles Vamp Blade
        if(attacker instanceof Player) {
            Player playerAttacker = (Player) attacker;
            if (playerAttacker.getItemInHand().getItemMeta().hasLore()) {
                if(!playerAttacker.getItemInHand().hasItemMeta()){
                    return;
                }

                if(!playerAttacker.getItemInHand().getItemMeta().hasLore()){
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
                    playerAttacker.setHealth(playerAttacker.getHealth() + DamageUtils.damageCalculator(victim, playerAttacker) * ItemRewardsQuest.INSTANCE.vampireBlade.toBeHealed);
                }

                // Handles Thor Hammer
                if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.thorHammer.lore) &&
                    playerAttacker.getItemInHand().getType().equals(Material.GOLD_AXE))
                {
                    playerAttacker.getWorld().strikeLightning(victim.getLocation());
                    if(victim instanceof Player){
                        Player target = (Player) victim;
                        target.setHealth(target.getHealth() - ItemRewardsQuest.INSTANCE.thorHammer.damage);
                    }
                }

                // Handles witch scythe
                if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.witchScythe.lore) &&
                    playerAttacker.getItemInHand().getType().equals(Material.GOLD_HOE))
                {
                    if(victim instanceof Player){
                        ((Player) victim).removePotionEffect(PotionEffectType.POISON);
                        ((Player) victim).addPotionEffect(new PotionEffect(PotionEffectType.POISON, (int) ItemRewardsQuest.INSTANCE.witchScythe.secondsOfEffect, 2));
                    }
                }
            }
        }
    }
}
