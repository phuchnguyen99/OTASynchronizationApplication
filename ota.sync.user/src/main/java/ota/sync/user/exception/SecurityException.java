package ota.sync.user.exception;

/**
 * Security Exception handle any exception related
 * to security e.x: SHA256.
 */
public class SecurityException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param message message.
     */
    public SecurityException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     * @param message message.
     * @param cause cause.
     */
    public SecurityException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
