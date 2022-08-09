package ota.sync.vehicle.exception;

/**
 * VehicleServiceException handles any exception thrown from
 * DefaultVehicleService class.
 */
public class VehicleServiceException extends Exception
{
    /**
     * Constructor.
     * @param message message.
     * @param cause cause.
     */
    public VehicleServiceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param message message.
     */
    public VehicleServiceException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     * @param cause cause.
     */
    public VehicleServiceException(final Throwable cause)
    {
        super(cause);
    }
}
