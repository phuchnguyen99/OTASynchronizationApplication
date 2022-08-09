package ota.sync.notifier;

/**
 * Listener class to handle activities after being notified.
 * @param <T> notification class generic value holder.
 */
public interface Listener<T>
{
    /**
     * update.
     * @param value value.
     */
    void update(T value);
}
