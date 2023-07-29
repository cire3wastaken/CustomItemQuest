package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import me.cire3.legxndsmp.itemrewardsquest.items.GhastBow;
import me.cire3.legxndsmp.itemrewardsquest.items.Items;
import me.cire3.legxndsmp.itemrewardsquest.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import static me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest.*;
import static me.cire3.legxndsmp.itemrewardsquest.command.ConvertCommand.OLD_GHASTBOW_LORE;

public class ShootBowEvent implements Listener {
    @EventHandler
    public void entityShootBow(EntityShootBowEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player playerShooter = (Player) event.getEntity();

        if(!PlayerUtils.shouldUse(playerShooter))
        {
            if(ItemRewardsQuest.getInstance().hasCooldown(playerShooter)) return;

            playerShooter.sendMessage(ChatColor.RED + Constants.CAN_NOT_USE);
            ItemRewardsQuest.getInstance().activateCooldown(playerShooter);
            return;
        }

        if(PlayerUtils.containsLore(playerShooter.getItemInHand(), GhastBow.oldLore)){
            playerShooter.sendMessage(Constants.FAIL_PREFIX +
                    "This items abilities are nullified due to being outdated. " +
                    "Use /updateitem while holding it to update it.");
            return;
        }

        if(ItemRewardsQuest.getInstance().isBlacklisted(playerShooter)){
            if(ItemRewardsQuest.getInstance().hasCooldown(playerShooter)) return;

            playerShooter.sendMessage(ChatColor.RED + Constants.BLACKLISTED);
            ItemRewardsQuest.getInstance().activateCooldown(playerShooter);
            return;
        }

        if(PlayerUtils.containsLore(playerShooter.getItemInHand(), GhastBow.lore)
                && playerShooter.getItemInHand().getType().equals(Material.BOW))
        {
            if(ItemRewardsQuest.getInstance().isDisabled(Items.WITCHSCYHTE)){
                playerShooter.sendMessage(Constants.DISABLED_ITEM);
                return;
            }

            event.getProjectile().setCustomName("aksjfuaqialfkiaGhastBowShot");
            event.getEntity().setCustomNameVisible(false);
        }
    }
}
