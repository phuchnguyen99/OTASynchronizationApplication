package ota.sync.user.dft;

import ota.sync.database.cache.UserCacheHelper;
import ota.sync.database.exception.DatabaseException;
import ota.sync.user.User;
import ota.sync.user.UserService;
import ota.sync.user.dft.helper.SecurityHelper;
import ota.sync.user.exception.UserServiceException;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * Implementation of UserService.
 */
public class DefaultUserService implements UserService
{
    /** number pattern */
    private final Pattern numberPattern = Pattern.compile(".*\\d+.*");

    /** uppercase pattern */
    private final Pattern uppercasePattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])");
    /** cached helper */
    private final UserCacheHelper cacheHelper;

    /**
     * Constructor.
     * @param cacheHelper cache helper.
     */
    public DefaultUserService(final UserCacheHelper cacheHelper)
    {
        this.cacheHelper = cacheHelper;
    }

    @Override
    public boolean createUser(final String username, final String password) throws UserServiceException
    {
        final User user;
        try
        {
            user = cacheHelper.getUser(new User(username, password));
        }
        catch(final DatabaseException e)
        {
            throw new UserServiceException(e.getMessage());
        }
        if(user == null)
        {
            validatePassword(password);
            final byte[] hash = SecurityHelper.getKeyPasswordHash(password);
            final User createdUser = new User(username, hash);
            storeUserToDatabase(createdUser);
            return true;
        }
        throw new UserServiceException("User already existed.");
    }

    @Override
    public boolean login(final String username, final String password)
        throws UserServiceException
    {
        final User user;
        try
        {
           user = cacheHelper.getUser((new User(username, password)));
        }
        catch(final DatabaseException e)
        {
            throw new UserServiceException(e.getMessage());
        }

        if(user == null)
        {
            throw new UserServiceException("User does not exist.");
        }

        final byte[] hash = SecurityHelper.getKeyPasswordHash(password);
        final String hashString = new String(hash, StandardCharsets.UTF_8);
        if(!user.getHashString().equalsIgnoreCase(hashString))
        {
            throw new UserServiceException("Password is incorrect.");
        }
        return true;
    }

    /**
     * validate password with following criteria:
     * 1. password contains at least 8 characters.
     * 2. password contains at least 1 number.
     * 3. password contains at least 1 uppercase letter.
     *
     * @param password password.
     * @throws UserServiceException if password does not meet the criteria.
     */
    private void validatePassword(final String password) throws UserServiceException
    {
        if(password.length() < 8)
        {
            throw new UserServiceException("Password must contain at least 8 characters.");
        }

         if(!numberPattern.matcher(password).find())
        {
            throw new UserServiceException("Password must contain at least 1 number.");
        }

        if(!uppercasePattern.matcher(password).find())
        {
            throw new UserServiceException("Password must contain at least 1 uppercase letter.");
        }
    }

    /**
     * store user to database.
     * @param user user.
     * @throws UserServiceException if the operation fails.
     */
    private void storeUserToDatabase(final User user) throws UserServiceException
    {
        try
        {
            cacheHelper.addUser(user);
        }
        catch(final DatabaseException e)
        {
            throw new UserServiceException(e.getMessage());
        }
    }
}
