package ota.sync.notifier.notification;

/**
 * VehicleNotification is for any notification
 * related to vehicle data or vehicle screen activities.
 */
public class VehicleNotification
{
    private boolean returnMainScreen;
    private boolean vehicleDataUpdated;

    /**
     * Constructor.
     * @param returnMainScreen return main screen.
     * @param vehicleDataUpdated vehicle data updated.
     */
    public VehicleNotification(final boolean returnMainScreen, final boolean vehicleDataUpdated)
    {
        this.returnMainScreen = returnMainScreen;
        this.vehicleDataUpdated = vehicleDataUpdated;
    }

    /**
     * is return to main screen.
     * @return is return main screen.
     */
    public boolean isReturnMainScreen()
    {
        return returnMainScreen;
    }

    /**
     * is vehicle data updated.
     * @return vehicle data updated.
     */
    public boolean isVehicleDataUpdated()
    {
        return vehicleDataUpdated;
    }
}
