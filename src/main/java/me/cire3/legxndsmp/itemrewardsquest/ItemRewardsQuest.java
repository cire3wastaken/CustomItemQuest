package me.cire3.legxndsmp.itemrewardsquest;

import me.cire3.legxndsmp.itemrewardsquest.events.AttackEntityEvent;

import static org.bukkit.Bukkit.getServer;

public enum ItemRewardsQuest
{
    INSTANCE;

    public SharedVariables sharedVariables;

    /**
     * This field is ONLY for registration, NOTHING ELSE
     * */
    private final ItemRewardsQuestInitializer itemRewardsQuestInitializer = new ItemRewardsQuestInitializer();

    public void init(){
        this.sharedVariables = new SharedVariables();
        this.sharedVariables.isEnabled = true;
        getServer().getPluginManager().registerEvents(new AttackEntityEvent(), itemRewardsQuestInitializer);
    }

    public void enable(){
        this.sharedVariables.isEnabled = true;
    }

    public void disable(){
        this.sharedVariables.isEnabled = false;
    }
}
