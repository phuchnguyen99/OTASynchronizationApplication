package ota.sync.notifier;

/**
 * Notifier interface handle register listeners to any notifications
 * and push notification.
 */
public interface Notifier
{
   /**
    * push notification
    * @param value value.
    * @param <T> generic value to hold any class passed as parameter.
    */
   <T> void pushNotification(T value);

   /**
    * register notification
    * @param listener listener.
    */
   void registerNotification(Listener listener);

   /**
    * deregiester notification when a listener does
    * not wish to be notified anymore.
    * @param listener listener.
    */
   void deregisterNotification(Listener listener);

}
