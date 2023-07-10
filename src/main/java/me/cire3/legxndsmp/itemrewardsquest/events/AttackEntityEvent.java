package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.event.Listener;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackEntityEvent implements Listener {
    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity victim = event.getEntity();
        if(attacker instanceof Player){
            Player playerAttacker = (Player) attacker;
            if(playerAttacker.getItemInHand().getItemMeta().hasLore()){
                if(playerAttacker.getItemInHand().getItemMeta().getLore().contains("Ability: Gain half as much HP as you do damage.")){
                    playerAttacker.setHealth(playerAttacker.getHealth() + DamageUtils.damageCalculator(victim, playerAttacker) / 4);
                }
            }
        }
    }
}
