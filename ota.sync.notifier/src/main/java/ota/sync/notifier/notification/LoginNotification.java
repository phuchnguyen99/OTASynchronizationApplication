package ota.sync.notifier.notification;

/**
 * Login notification is for any notification related
 * to login/logout activities.
 */
public class LoginNotification
{
    private final boolean login;

    /**
     * Constructor
     * @param login login.
     */
    public LoginNotification(final boolean login)
    {
        this.login = login;
    }

    /**
     * get login.
     * @return login.
     */
    public boolean getLogin()
    {
        return login;
    }
}
