package me.cire3.legxndsmp.itemrewardsquest.event;

public abstract class CancellableEvent<T extends org.bukkit.event.Listener> extends Event<T>
{
    private boolean isCancelled = false;

    public boolean isCancelled()
    {
        return isCancelled;
    }

    public void cancel()
    {
        isCancelled = true;
    }
}
