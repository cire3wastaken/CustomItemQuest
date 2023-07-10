package me.cire3.legxndsmp.itemrewardsquest.event;

import java.util.ArrayList;

public abstract class Event<T extends org.bukkit.event.Listener>
{
    public abstract void fire(ArrayList<T> listeners);

    public abstract Class<T> getListenerType();
}
