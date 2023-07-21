package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.ColorUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CAN_NOT_USE;
import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CHAT_PREFIX;

public class AttackEntityEvent implements org.bukkit.event.Listener {

    public static final String OLD_VAMPBLADE_LORE = ColorUtils.color("&6Ability: gain half as much HP as you do damage");
    public static final String OLD_GHASTBOW_LORE = ColorUtils.color("&eAbility: create explosions upon whom your arrow lands on");
    public static final String OLD_THORHAMMER_LORE = ColorUtils.color("&cAbility: incur lightning upon your victims");
    public static final String OLD_WITCHSCYTHE_LORE = ColorUtils.color("&aAbility: inflict poison on your victims");
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
                if(!PlayerUtils.shouldUse(playerAttacker) || !PlayerUtils.shouldUse(event.getEntity().getLocation()))
                {
                    if(ItemRewardsQuest.INSTANCE.hasCooldown(playerAttacker)) return;

                    playerAttacker.sendMessage(ChatColor.RED + CAN_NOT_USE);
                    ItemRewardsQuest.INSTANCE.activateCooldown(playerAttacker);
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
                if(!PlayerUtils.shouldUse(playerAttacker) || !PlayerUtils.shouldUse(event.getEntity().getLocation()))
                {
                    if(ItemRewardsQuest.INSTANCE.hasCooldown(playerAttacker)) return;

                    playerAttacker.sendMessage(ChatColor.RED + CAN_NOT_USE);
                    ItemRewardsQuest.INSTANCE.activateCooldown(playerAttacker);
                    return;
                }

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
            if (lowerCaseLore.equals(ItemRewardsQuest.INSTANCE.witchScythe.lore) &&
                playerAttacker.getItemInHand().getType().equals(Material.GOLD_HOE))
            {
                if(!PlayerUtils.shouldUse(playerAttacker) || !PlayerUtils.shouldUse(event.getEntity().getLocation()))
                {
                    if(ItemRewardsQuest.INSTANCE.hasCooldown(playerAttacker)) return;

                    playerAttacker.sendMessage(ChatColor.RED + CAN_NOT_USE);
                    ItemRewardsQuest.INSTANCE.activateCooldown(playerAttacker);
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

            if(playerAttacker.getItemInHand().getItemMeta().getLore().contains(OLD_GHASTBOW_LORE) || playerAttacker.getItemInHand().getItemMeta().getLore().contains(OLD_THORHAMMER_LORE) || playerAttacker.getItemInHand().getItemMeta().getLore().contains(OLD_WITCHSCYTHE_LORE) || playerAttacker.getItemInHand().getItemMeta().getLore().contains(OLD_VAMPBLADE_LORE) ){
                playerAttacker.sendMessage(ChatColor.RED + CHAT_PREFIX + "This items abilities are nullified due to being outdated. Use /updateitem while holding it to update it.");
            }
        }
    }
}

