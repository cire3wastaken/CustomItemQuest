package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;
import static me.cire3.legxndsmp.itemrewardsquest.command.player.ConvertCommand.OLD_GHASTBOW_LORE;

public class ShootBowEvent implements Listener {
    @EventHandler
    public void entityShootBow(EntityShootBowEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player playerShooter = (Player) event.getEntity();

        if(!PlayerUtils.shouldUse(playerShooter))
        {
            if(ItemRewardsQuest.INSTANCE.hasCooldown(playerShooter)) return;

            playerShooter.sendMessage(ChatColor.RED + CAN_NOT_USE);
            ItemRewardsQuest.INSTANCE.activateCooldown(playerShooter);
            return;
        }

        if(PlayerUtils.containsString(playerShooter.getItemInHand(), OLD_GHASTBOW_LORE)){
            playerShooter.sendMessage(FAIL_PREFIX +
                    "This items abilities are nullified due to being outdated. " +
                    "Use /updateitem while holding it to update it.");
            return;
        }

        if(ItemRewardsQuest.INSTANCE.isBlacklisted(playerShooter)){
            if(ItemRewardsQuest.INSTANCE.hasCooldown(playerShooter)) return;

            playerShooter.sendMessage(ChatColor.RED + BLACKLISTED);
            ItemRewardsQuest.INSTANCE.activateCooldown(playerShooter);
            return;
        }

        if(PlayerUtils.containsLore(playerShooter.getItemInHand(), ItemRewardsQuest.INSTANCE.ghastBow.lore)
                && playerShooter.getItemInHand().getType().equals(Material.BOW))
        {
            event.getProjectile().setCustomName("aksjfuaqialfkiaGhastBowShot");
            event.getEntity().setCustomNameVisible(false);
        }
    }
}
