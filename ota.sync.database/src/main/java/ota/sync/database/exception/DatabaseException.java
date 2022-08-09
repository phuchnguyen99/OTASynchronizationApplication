package ota.sync.database.exception;

/**
 * DatabaseException handles exception thrown from
 * DatabaseService.
 */
public class DatabaseException extends Exception
{
    /**
     * Constructor.
     * @param message message.
     * @param cause cause.
     */
    public DatabaseException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param message message.
     */
    public DatabaseException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     * @param cause cause.
     */
    public DatabaseException(final Throwable cause)
    {
        super(cause);
    }
}
