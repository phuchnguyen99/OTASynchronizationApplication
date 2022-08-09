import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ota.sync.database.cache.VehicleCacheHelper;
import ota.sync.database.exception.DatabaseException;
import ota.sync.serialization.SerializerDeserializerFactory;
import ota.sync.serialization.utils.JsonSerializerDeserializer;
import ota.sync.serialization.utils.SerializerDeserializer;
import ota.sync.vehicle.VehicleData;
import ota.sync.vehicle.VehicleService;
import ota.sync.vehicle.dft.DefaultVehicleService;
import ota.sync.vehicle.exception.VehicleServiceException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

public class DefaultVehicleServiceShould
{
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private final VehicleCacheHelper vehicleCacheHelper = context.mock(VehicleCacheHelper.class);
    private final SerializerDeserializerFactory serializerDeserializerFactory =
        context.mock(SerializerDeserializerFactory.class);
    private SerializerDeserializer jsonSerializerDeserializer = context.mock(SerializerDeserializer.class);
    public VehicleService vehicleService;

    @Before
    public void setUp()
    {
        context.checking(new Expectations(){
            {
                oneOf(serializerDeserializerFactory).getJsonSerializerDeserializer();
                will(returnValue(jsonSerializerDeserializer));
            }
        });
        vehicleService =
            new DefaultVehicleService(vehicleCacheHelper, serializerDeserializerFactory);
    }

    @Test
    public void throws_exception_if_vin_does_not_start_with_LAF() throws Exception
    {
        exception.expect(VehicleServiceException.class);
        exception.expectMessage("Vin is invalid.");
        vehicleService.getVehicleData("PHU123456789102");

    }

    @Test
    public void throws_exception_if_vin_is_not_15_character_length() throws Exception
    {
        exception.expect(VehicleServiceException.class);
        exception.expectMessage("Vin length must be 15 characters.");
        vehicleService.getVehicleData("LAF12345678910");
    }

    @Test
    public void create_vehicle_data() throws Exception
    {
        final List<VehicleData> results = new ArrayList<>();
        results.add(new VehicleData("LAF123456789102"));
        context.checking(new Expectations(){
            {
                oneOf(vehicleCacheHelper).createNewVehicleData(new VehicleData("LAF123456789102"));
                will(returnValue(true));
            }
        });
        assertTrue(vehicleService.addVehicleData(new VehicleData("LAF123456789102")));
    }

    @Test
    public void retrieve_vin() throws Exception
    {
        final VehicleData result = new VehicleData("LAF123456789102", "ABC", "FF1",
                                                   true, new Date(), new Date());
        final List<VehicleData> resultList = new ArrayList<>();
        resultList.add(result);
        context.checking(new Expectations(){
            {
                oneOf(vehicleCacheHelper).getVehicle(with(any(VehicleData.class)));
                will(returnValue(resultList));;
            }
        });
       final List<VehicleData> vehicleDataList = vehicleService.getVehicleData("LAF123456789101");
       assertThat(vehicleDataList.size(), equalTo(1));
    }

    @Test
    public void failed_to_retrieve_vehicle_data_throw_exception() throws Exception
    {
        final VehicleData result = new VehicleData("LAF123456789102", "ABC", "FF1",
                                                   true, new Date(), new Date());
        final List<VehicleData> resultList = new ArrayList<>();
        resultList.add(result);
        context.checking(new Expectations(){
            {
                oneOf(vehicleCacheHelper).getVehicle(with(any(VehicleData.class)));
                will(throwException(new DatabaseException("Unable to retrieve data.")));
            }
        });
        exception.expect(VehicleServiceException.class);
        vehicleService.getVehicleData("LAF123456789101");
    }

    @Test
    public void create_vehicle_data_throw_exception() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(vehicleCacheHelper).createNewVehicleData(with(any(VehicleData.class)));
                will(throwException(new DatabaseException("Unable to create data")));
            }
        });
        exception.expect(VehicleServiceException.class);
        vehicleService.addVehicleData(new VehicleData("LAF123456789101"));
    }

    @Test
    public void modify_vehicle_data() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(vehicleCacheHelper).modifyVehicle(with(any(VehicleData.class)));
                will(returnValue(true));
            }
        });
        final boolean result = vehicleService.modifyVehicleData(new VehicleData("LAF123456789101"));
        assertThat(result, equalTo(true));
    }

    @Test
    public void delete_vehicle_data() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(vehicleCacheHelper).deleteVehicle(with(any(VehicleData.class)));
                will(returnValue(true));
            }
        });
        final boolean result = vehicleService.deleteVehicleData(new VehicleData("LAF123456789101"));
        assertThat(result, equalTo(true));
    }

    @Test
    public void refresh_vehicle_data() throws Exception
    {
        final VehicleData result = new VehicleData("LAF123456789102", "ABC", "FF1",
                                                   true, new Date(), new Date());
        final List<VehicleData> resultList = new ArrayList<>();
        resultList.add(result);
        context.checking(new Expectations(){
            {
                oneOf(vehicleCacheHelper).getSyncVehicleData(with(any(VehicleData.class)));
                will(returnValue(resultList));
            }
        });
        final List<VehicleData> vehicleDataList = vehicleService.refreshVehicleData("LAF123456789101");
        assertThat(vehicleDataList.size(), equalTo(1));
    }

    @Test
    public void export_vehicle_data_list_to_file() throws Exception
    {
        final List<VehicleData> vehicleDataList = new ArrayList<>();
        vehicleDataList.add(new VehicleData("LAF123456789101", "ABC", "FFF", true, new Date(), new Date()));
        context.checking(new Expectations(){
            {
                oneOf(jsonSerializerDeserializer).writeValue(with(any(File.class)), with(any(List.class)));
            }
        });
        final File exportedFile = vehicleService.exportVehicleData(vehicleDataList);
        assertThat(exportedFile, notNullValue());
    }

    @Test
    public void export_vehicle_data_throw_exception() throws Exception
    {
        final List<VehicleData> vehicleDataList = new ArrayList<>();
        vehicleDataList.add(new VehicleData("LAF123456789101", "ABC", "FFF", true, new Date(), new Date()));
        context.checking(new Expectations(){
            {
                oneOf(jsonSerializerDeserializer).writeValue(with(any(File.class)), with(any(List.class)));
                will(throwException(new IOException("Unable to export file.")));
            }
        });
        exception.expect(VehicleServiceException.class);
        exception.expectMessage("Unable to export file");
        final File exportedFile = vehicleService.exportVehicleData(vehicleDataList);
    }
}
