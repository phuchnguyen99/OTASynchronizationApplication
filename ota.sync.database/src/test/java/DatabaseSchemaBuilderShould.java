import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ota.sync.database.DatabaseSchemaBuilder;
import ota.sync.database.DatabaseService;
import ota.sync.database.DefaultDatabaseService;

public class DatabaseSchemaBuilderShould
{
    private DatabaseSchemaBuilder builder;
    private DatabaseService databaseService;

    @Before
    public void setUp()
    {
        databaseService = new DefaultDatabaseService();
    }

    @After
    public void tearDown() throws Exception
    {
        databaseService.removeDatabase();
    }

    @Test
    public void create_table() throws Exception
    {
        builder = new DatabaseSchemaBuilder();
        builder.buildDealershipSchema(new DefaultDatabaseService());
    }
}
