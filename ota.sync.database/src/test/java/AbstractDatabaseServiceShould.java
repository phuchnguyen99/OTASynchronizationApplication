import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ota.sync.database.DatabaseService;
import ota.sync.database.DefaultDatabaseService;
import ota.sync.database.ResultRowMap;
import ota.sync.database.exception.DatabaseException;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class AbstractDatabaseServiceShould
{
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private final DatabaseService databaseService = new DefaultDatabaseService();

    @After
    public void tearDown()throws Exception
    {
        databaseService.removeDatabase();
    }

    @Test
    public void execute_sql_statement() throws Exception
    {
        String sql = "INSERT INTO USER(USER_NAME, PASSWORD) VALUES (?, ?)";
        final List<Object> args = new ArrayList<>();
        args.add("username");
        args.add("password");
        databaseService.executeSql(sql, args);
    }

    @Test
    public void execute_sql_throw_exception_if_record_already_exists() throws Exception
    {
        String sql = "INSERT INTO USER(USER_NAME, PASSWORD) VALUES (?, ?)";
        final List<Object> args = new ArrayList<>();
        args.add("username1");
        args.add("password");
        databaseService.executeSql(sql, args);

        final List<Object> args2 = new ArrayList<>();
        args2.add("username1");
        args2.add("password");
        exception.expect(DatabaseException.class);
        databaseService.executeSql(sql, args);
    }

    @Test
    public void execute_query_statement() throws Exception
    {
        final String insertSQL = "INSERT INTO USER(USER_NAME, PASSWORD) VALUES (?, ?)";
        final List<Object> args = new ArrayList<>();
        args.add("username");
        args.add("password");
        databaseService.executeSql(insertSQL, args);

        String sql = "SELECT * FROM USER WHERE USER_NAME = ?";
        final List<Object> args2 = new ArrayList<>();
        args2.add("username");
        final List<ResultRowMap> resultRowMaps = databaseService.executeQuery(sql, args2);
        assertNotNull(resultRowMaps);
    }
}
