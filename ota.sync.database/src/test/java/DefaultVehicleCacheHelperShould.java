import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ota.sync.database.DatabaseService;
import ota.sync.database.ResultRowMap;
import ota.sync.database.cache.DefaultVehicleCacheHelper;
import ota.sync.database.cache.VehicleCacheHelper;
import ota.sync.database.exception.DatabaseException;
import ota.sync.vehicle.VehicleData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DefaultVehicleCacheHelperShould
{
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private final DatabaseService databaseService = context.mock(DatabaseService.class);
    private final VehicleCacheHelper vehicleCacheHelper = new DefaultVehicleCacheHelper(databaseService, databaseService);

    @Test
    public void get_vehicle_data() throws Exception
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add("LAF12345678910");
        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeQuery("SELECT * FROM VEHICLE WHERE VIN = ?", arguments);
                will(returnValue(new ArrayList<ResultRowMap>()));
            }
        });
        final List<VehicleData> vehicleData = vehicleCacheHelper.getVehicle(new VehicleData("LAF12345678910"));
        assertNotNull(vehicleData);
    }

    @Test
    public void get_vehicle_data_throws_exception() throws Exception
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add("LAF12345678910");
        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeQuery("SELECT * FROM VEHICLE WHERE VIN = ?", arguments);
                will(throwException(new DatabaseException("Unable to select data from vehicle")));
            }
        });
        exception.expect(DatabaseException.class);
        vehicleCacheHelper.getVehicle(new VehicleData("LAF12345678910"));
    }

    @Test
    public void modify_vehicle_data() throws Exception
    {
        final List<Object> arguments = new ArrayList<>();
        final VehicleData vehicleData = new VehicleData("LAF12345678910", "ModuleName",
                                                        "SoftwareName", true,
                                                        new Date(), new Date());
        arguments.add(vehicleData.getModuleName());
        arguments.add(vehicleData.getSoftwareNumber());
        arguments.add(vehicleData.getStatus());
        arguments.add(vehicleData.getTimestamp());
        arguments.add(vehicleData.getVin());
        arguments.add(vehicleData.getCreationTime());
        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeSql("UPDATE VEHICLE SET MODULE_NAME = ?,"
                                                      + " SOFTWARE_NUMBER = ? , STATUS= ?, "
                                                      + "UPDATE_TIME=? WHERE VIN=? AND CREATION_TIME = ?", arguments);
                will(returnValue(true));
            }
        });
        final boolean result = vehicleCacheHelper.modifyVehicle(vehicleData);
        assertTrue(result);
    }

    @Test
    public void delete_vehicle_data() throws Exception
    {
        final List<Object> arguments = new ArrayList<>();
        final VehicleData vehicleData = new VehicleData("LAF12345678910", "ModuleName",
                                                        "SoftwareName", true, new Date(), new Date());
        arguments.add(vehicleData.getVin());
        arguments.add(vehicleData.getCreationTime());
        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeSql("DELETE FROM VEHICLE WHERE VIN = ? AND CREATION_TIME = ?", arguments);
                will(returnValue(true));
            }
        });
        final boolean result = vehicleCacheHelper.deleteVehicle(vehicleData);
        assertTrue(result);
    }

    @Test
    public void get_sync_vehicle_data() throws Exception
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add("LAF12345678910");
        context.checking(new Expectations(){
            {
                exactly(2).of(databaseService).executeQuery("SELECT * FROM VEHICLE WHERE VIN = ?", arguments);
                will(returnValue(new ArrayList<ResultRowMap>()));
            }
        });
        vehicleCacheHelper.getSyncVehicleData(new VehicleData("LAF12345678910"));
    }

    @Test
    public void add_vehicle_data() throws Exception
    {
        final Date updateDate = new Date();
        final Date createDate = new Date();
        final List<Object> arguments = new ArrayList<>();
        arguments.add("LAF12345678910");
        arguments.add("ABC");
        arguments.add("FF1");
        arguments.add(true);
        arguments.add(updateDate);
        arguments.add(createDate);
        context.checking(new Expectations(){
            {
                oneOf(databaseService).executeSql("INSERT INTO VEHICLE VALUES(?,?,?,?,?,?)", arguments);
                will(returnValue(true));
            }
        });
        final boolean result = vehicleCacheHelper.createNewVehicleData(new VehicleData("LAF12345678910", "ABC", "FF1",
                                                                                       true, updateDate, createDate));
       assertTrue(result);
    }
}
