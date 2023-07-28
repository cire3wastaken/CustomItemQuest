package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.DamageUtils;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Set;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.CHAT_PREFIX;

public class RightClickInteractEvent implements Listener {
    public final HashMap<String, Long> cooldownsForPlayer = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if(!event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            return;
        }

        Player player = event.getPlayer();
        if(player.getItemInHand() == null) {
            return;
        }
        if(player.getItemInHand().getType() != Material.IRON_SPADE){
            return;
        }
        if(!player.getItemInHand().hasItemMeta()) {
            return;
        }
        if(!player.getItemInHand().getItemMeta().hasLore()){
            return;
        }
        if(!player.getItemInHand().getItemMeta().getLore().equals(ItemRewardsQuest.INSTANCE.hyperion.lore)){
            return;
        }

        if(!PlayerUtils.shouldUse(player))
        {
            player.sendMessage(ChatColor.RED + CHAT_PREFIX + "You can not use that item here!");
            return;
        }

        int level = 0;
        boolean alreadyHadAbsorption = false;

        if(this.hasCooldown(player)){
            int timeLeft = (int) Math.ceil(this.cooldownsForPlayer.get(player.getName()) / 1000.0F);
            player.sendMessage(
                ChatColor.RED + CHAT_PREFIX + "You can use this item's ability again in " + timeLeft + "s.");
            return;
        }

        for (PotionEffect effect : player.getActivePotionEffects()){
            if(effect.getType().equals(PotionEffectType.ABSORPTION)){
                alreadyHadAbsorption = true;
                level = effect.getAmplifier() + 1;
                break;
            }
        }

        player.teleport(PlayerUtils.getTargetBlock(event.getPlayer(), 10)[0].getLocation());
        if(alreadyHadAbsorption) player.removePotionEffect(PotionEffectType.ABSORPTION);

        int amplifier = (int)
            Math.floor(((DamageUtils.getAttackDamage(player.getItemInHand()) + 6) * DamageUtils.strengthIncrease(player)
            * ItemRewardsQuest.INSTANCE.hyperion.percentage) / 4F);

        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,
            (int) ItemRewardsQuest.INSTANCE.hyperion.shieldDurationTicks, amplifier + level));
        player.sendMessage(ChatColor.GOLD + CHAT_PREFIX +
                "Your Hyperion has given you a shield for " + amplifier * 4 + " HP!");
        this.activateCooldown(player);

        for(LivingEntity entity : PlayerUtils.getNearbyLivingEntities(player,
                ItemRewardsQuest.INSTANCE.hyperion.explosionRadius))
        {
            entity.damage(ItemRewardsQuest.INSTANCE.hyperion.damage);
        }

        // and cancel the event so that the item cannot really be used / placed
        event.setCancelled(true);
        event.setUseItemInHand(Event.Result.DENY);
    }

    public boolean hasCooldown(Player player){
        return !(this.cooldownsForPlayer.get(player.getName()) < (System.currentTimeMillis() -
            ItemRewardsQuest.INSTANCE.hyperion.cooldownSeconds * 1000));
    }

    public void activateCooldown(Player player){
        this.cooldownsForPlayer.remove(player.getName());
        this.cooldownsForPlayer.put(player.getName(), System.currentTimeMillis());
    }
}
