package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CHAT_PREFIX;

public class AttackEntityEvent implements org.bukkit.event.Listener {
    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        if(!ItemRewardsQuest.INSTANCE.isEnabled) return;

        if(event.getDamager() instanceof Player) {
            Player playerAttacker = (Player) event.getDamager();

            Entity victim = event.getEntity();

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

            double[] c = DamageUtils.damageCalculator(victim, event.getFinalDamage() - 2);
            c[1] = c[1] * DamageUtils.strengthIncrease(playerAttacker);

            List<String> lowerCaseLore = new ArrayList<>();
            for(String str : playerAttacker.getItemInHand().getItemMeta().getLore()){
                lowerCaseLore.add(str.toLowerCase());
            }

            // Handles vamp blade
            if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.vampireBlade.lore) &&
                playerAttacker.getItemInHand().getType().equals(Material.DIAMOND_SWORD))
            {
                if(!PlayerUtils.shouldUse(playerAttacker))
                {
                    playerAttacker.sendMessage(ChatColor.RED + CHAT_PREFIX + "You can not use that item here!");
                    return;
                }

                playerAttacker.setHealth(Math.min(playerAttacker.getHealth() +
                    Math.min(Math.max(DamageUtils.calcDamage((int) c[0], c[1], 0, (int) c[3],
                        (int) c[4]) * ItemRewardsQuest.INSTANCE.vampireBlade.toBeHealed,
                            playerAttacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) ?
                                (Math.random() >= 0.5 ? 2.0F : 1.0F) :
                                    (Math.random() >= 0.5 ? 1.5F : 1.0F)), 6.0F), 20.0F));
//                  playerAttacker.getHealth() +
//                      2 * DamageUtils.damageCalculator(victim, playerAttacker) * ItemRewardsQuest.INSTANCE.vampireBlade.toBeHealed
            }

            // Handles Thor Hammer
            if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.thorHammer.lore) &&
                playerAttacker.getItemInHand().getType().equals(Material.GOLD_AXE))
            {
                if(!PlayerUtils.shouldUse(playerAttacker))
                {
                    playerAttacker.sendMessage(ChatColor.RED + CHAT_PREFIX + "You can not use that item here!");
                    return;
                }

                playerAttacker.getWorld().strikeLightning(victim.getLocation());
                if(victim instanceof Player){
                    Player target = (Player) victim;
                    target.setFireTicks(((int) Math.floor(ItemRewardsQuest.INSTANCE.thorHammer.fireTicks)));
                }
            }

            // Handles witch scythe
            if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.witchScythe.lore) &&
                playerAttacker.getItemInHand().getType().equals(Material.GOLD_HOE))
            {
                if(!PlayerUtils.shouldUse(playerAttacker))
                {
                    playerAttacker.sendMessage(ChatColor.RED + CHAT_PREFIX + "You can not use that item here!");
                    return;
                }

                if(victim instanceof Player){
                    Player playerVictim = (Player) victim;

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
