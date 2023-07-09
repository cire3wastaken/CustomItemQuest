package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.event.Listener;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class AttackEntityEvent implements Listener {
    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity victim = event.getEntity();
        if(attacker instanceof Player){
            Player player = (Player) attacker;
            if(player.getItemInHand().)
        }
    }

    public float onAttack(Entity target, Player attacker) {
        if(target instanceof Player) {
            Player playerTarget = (Player) target;
            int armorPoints = 0;
            armorPoints = armorPoints + DamageUtils.getArmorPoints(playerTarget.getInventory().getHelmet());
            armorPoints = armorPoints + DamageUtils.getArmorPoints(playerTarget.getInventory().getChestplate());
            armorPoints = armorPoints + DamageUtils.getArmorPoints(playerTarget.getInventory().getLeggings());
            armorPoints = armorPoints + DamageUtils.getArmorPoints(playerTarget.getInventory().getBoots());

            int amplifier = 0;
            playerTarget.getActivePotionEffects().forEach(effect -> {
                if(effect.getType() == PotionEffectType.DAMAGE_RESISTANCE){
                    amplifier = Math.max(amplifier, effect.getAmplifier());
                }
            });

            int epf = 0;

            return DamageUtils.calcDamage(armorPoints,
                    attacker.getItemInHand().(), epf,
                    playerTarget.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) ?
                            ) : 0);
        }
    }
}
