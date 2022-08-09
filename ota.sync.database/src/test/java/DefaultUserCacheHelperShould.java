import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ota.sync.database.DatabaseService;
import ota.sync.database.ResultRowMap;
import ota.sync.database.cache.DefaultUserCacheHelper;
import ota.sync.database.cache.UserCacheHelper;
import ota.sync.database.exception.DatabaseException;
import ota.sync.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DefaultUserCacheHelperShould
{
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private final DatabaseService databaseService = context.mock(DatabaseService.class);
    private final UserCacheHelper userCacheHelper = new DefaultUserCacheHelper(databaseService);

    @Test
    public void add_user() throws Exception
    {
        final User user = new User("username", "password");
        final List<Object> arguments = new ArrayList<>();
        arguments.add(user.getUsername());
        arguments.add(user.getHashString());
        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeSql("INSERT INTO USER(USER_NAME, PASSWORD) VALUES (?, ?)", arguments);
                will(returnValue(true));
            }
        });
        assertTrue(userCacheHelper.addUser(user));
    }

    @Test
    public void get_user() throws Exception
    {
        final User user = new User("username", "password");
        final List<Object> arguments = new ArrayList<>();
        arguments.add(user.getUsername());

        final List<ResultRowMap> resultRowMaps = new ArrayList<ResultRowMap>();
        resultRowMaps.add(new ResultRowMap());
        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeQuery("SELECT * FROM USER WHERE USER_NAME = ?", arguments);
                will(returnValue(resultRowMaps));
            }
        });
        final User result = userCacheHelper.getUser(user);
        assertNotNull(result);
    }

    @Test
    public void modify_user() throws Exception
    {
        final User user = new User("username", "password");
        final List<Object> arguments = new ArrayList<>();
        arguments.add(user.getHashString());
        arguments.add(user.getUsername());

        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeSql("UPDATE USER SET PASSWORD = ? WHERE USER_NAME = ?", arguments);
                will(returnValue(true));
            }
        });
        assertTrue(userCacheHelper.modifyUser(user));
    }
}
