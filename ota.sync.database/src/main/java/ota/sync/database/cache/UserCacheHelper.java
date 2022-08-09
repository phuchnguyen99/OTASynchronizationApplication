package ota.sync.database.cache;

import ota.sync.database.exception.DatabaseException;
import ota.sync.user.User;

/**
 * UserCacheHelper class handle services with the User table.
 */
public interface UserCacheHelper
{
    /**
     * add user.
     * @param user user.
     * @return true if succeeds.
     * @throws DatabaseException if the operations fails.
     */
    boolean addUser(final User user) throws DatabaseException;

    /**
     * get user.
     * @param user user.
     * @return user.
     * @throws DatabaseException if the operation fails.
     */
    User getUser(final User user) throws DatabaseException;

    /**
     * modify user.
     * @param user user.
     * @return true if succeeds.
     * @throws DatabaseException if the operation fails.
     */
    boolean modifyUser(final User user) throws DatabaseException;

}
