package ota.sync.user.exception;

/**
 * UserServiceException handles any exceptions thrown
 * in UserService class.
 */
public class UserServiceException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param message message.
     */
    public UserServiceException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     * @param message message.
     * @param cause cause.
     */
    public UserServiceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
