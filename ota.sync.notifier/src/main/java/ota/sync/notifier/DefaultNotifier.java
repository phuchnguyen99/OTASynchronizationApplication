package ota.sync.notifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Notifier interface.
 */
public class DefaultNotifier implements Notifier
{
    /**
     * listener list.
     */
    private final static List<Listener> listeners = new ArrayList<>();
    @Override
    public <T> void pushNotification( T value)
    {
        for(final Listener listener : listeners)
        {
            listener.update(value);
        }
    }

    @Override
    public void registerNotification(final Listener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void deregisterNotification(final Listener listener)
    {
        listeners.remove(listener);
    }
}
