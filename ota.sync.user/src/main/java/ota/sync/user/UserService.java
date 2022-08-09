package ota.sync.user;

import ota.sync.user.exception.UserServiceException;

/**
 * UserService handles functionality related to user
 * such as createUser, login, etc.
 */
public interface UserService
{
    /**
     * create user.
     * @param username username.
     * @param password password.
     * @return true if succeeds.
     * @throws UserServiceException if the operation fails.
     */
    boolean createUser(String username, String password) throws UserServiceException;

    /**
     * login.
     * @param username username.
     * @param password password.
     * @return true if succeeds.
     * @throws UserServiceException if the operation fails.
     */
    boolean login(String username, String password) throws UserServiceException;
}
