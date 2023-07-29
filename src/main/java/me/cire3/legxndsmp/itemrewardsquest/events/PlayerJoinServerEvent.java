package me.cire3.legxndsmp.itemrewardsquest.events;

import me.cire3.legxndsmp.itemrewardsquest.Constants;
import me.cire3.legxndsmp.itemrewardsquest.ItemRewardsQuest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinServerEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(event.getPlayer().isOp()){
            if(ItemRewardsQuest.getInstance().status())
                event.getPlayer().sendMessage(Constants.OUTDATED_MESSAGE);
        }
    }
}
