package ota.sync.database.cache;

import ota.sync.database.DatabaseService;
import ota.sync.database.DefaultDatabaseService;
import ota.sync.database.ResultRowMap;
import ota.sync.database.exception.DatabaseException;
import ota.sync.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of UserCacheHelper.
 */
public class DefaultUserCacheHelper implements UserCacheHelper
{
    private final String CREATE_USER_QUERY = "INSERT INTO USER(USER_NAME, PASSWORD) VALUES (?, ?)";
    private final String SELECT_USER_QUERY = "SELECT * FROM USER WHERE USER_NAME = ?";
    private final String UPDATE_USER_QUERY = "UPDATE USER SET PASSWORD = ? WHERE USER_NAME = ?";
    private final DatabaseService databaseService;

    /**
     * Constructor.
     * @param databaseService database service.
     */
    public DefaultUserCacheHelper(final DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @Override
    public boolean addUser(final User user) throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(user.getUsername());
        arguments.add(user.getHashString());
        databaseService.executeSql(CREATE_USER_QUERY, arguments);
        return true;
    }

    @Override
    public User getUser(final User user) throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(user.getUsername());
        final List<ResultRowMap> resultRowMaps = databaseService.executeQuery(SELECT_USER_QUERY, arguments);
        return (resultRowMaps == null || resultRowMaps.isEmpty()) ?  null :
                new User(resultRowMaps.get(0).getString("USER_NAME"),
                         resultRowMaps.get(0).getString("PASSWORD"));

    }

    @Override
    public boolean modifyUser(final User user) throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(user.getHashString());
        arguments.add(user.getUsername());
        return databaseService.executeSql(UPDATE_USER_QUERY, arguments);
    }
}
