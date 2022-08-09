package ota.sync.database.cache;

import ota.sync.database.DatabaseService;
import ota.sync.database.DefaultDatabaseService;
import ota.sync.database.OtaDatabaseService;
import ota.sync.database.ResultRowMap;
import ota.sync.database.exception.DatabaseException;
import ota.sync.vehicle.VehicleData;

import java.util.ArrayList;
import java.util.List;

public class DefaultVehicleCacheHelper implements VehicleCacheHelper
{
    private final static String SELECT_VEHICLES = "SELECT * FROM VEHICLE WHERE VIN = ?";
    private final static String UPDATE_VEHICLE = "UPDATE VEHICLE SET MODULE_NAME = ?, SOFTWARE_NUMBER = ? , STATUS= ?, UPDATE_TIME=? WHERE VIN=? AND CREATION_TIME = ?";
    private final static String INSERT_VEHICLE = "INSERT INTO VEHICLE VALUES(?,?,?,?,?,?)";
    private final static String INSERT_VEHICLE_TO_OTA = "INSERT INTO VEHICLE VALUES(?,?,?,?,?,?,?)";
    private final static String DELETE_VEHICLE = "DELETE FROM VEHICLE WHERE VIN = ? AND CREATION_TIME = ?";
    private DatabaseService databaseService;
    private DatabaseService otaDatabaseService;

    public DefaultVehicleCacheHelper(final DatabaseService defaultDatabaseService, final DatabaseService otaDatabaseService)
    {
        this.databaseService = defaultDatabaseService;
        this.otaDatabaseService = otaDatabaseService;
    }

    @Override
    public List<VehicleData> getVehicle(final VehicleData vehicleData) throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(vehicleData.getVin());
        final List<ResultRowMap> resultRowMaps = databaseService.executeQuery(SELECT_VEHICLES, arguments);
        return convertRowMapsToVehicleDataList(resultRowMaps);
    }

    @Override
    public boolean modifyVehicle(final VehicleData vehicleData) throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(vehicleData.getModuleName());
        arguments.add(vehicleData.getSoftwareNumber());
        arguments.add(vehicleData.getStatus());
        arguments.add(vehicleData.getTimestamp());
        arguments.add(vehicleData.getVin());
        arguments.add(vehicleData.getCreationTime());
        final Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    otaDatabaseService.executeSql(UPDATE_VEHICLE, arguments);
                }
                catch(final DatabaseException e)
                {
                    //ignore exception.
                }
            }
        });
        t.start();
        return databaseService.executeSql(UPDATE_VEHICLE, arguments);
    }

    @Override
    public boolean createNewVehicleData(final VehicleData vehicleData) throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(vehicleData.getVin());
        arguments.add(vehicleData.getModuleName());
        arguments.add(vehicleData.getSoftwareNumber());
        arguments.add(vehicleData.getStatus());
        arguments.add(vehicleData.getTimestamp());
        arguments.add(vehicleData.getCreationTime());
        final boolean result = databaseService.executeSql(INSERT_VEHICLE, arguments);
        final Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    arguments.add("DEALERSHIP");
                    otaDatabaseService.executeSql(INSERT_VEHICLE_TO_OTA, arguments);
                }
                catch(final DatabaseException e)
                {
                    //ignore exception.
                }
            }
        });
        t.start();
        return result;
    }

    @Override
    public boolean deleteVehicle(final VehicleData vehicleData)
        throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(vehicleData.getVin());
        arguments.add(vehicleData.getCreationTime());
        return databaseService.executeSql(DELETE_VEHICLE, arguments);
    }

    @Override
    public List<VehicleData> getSyncVehicleData(final VehicleData vehicleData) throws DatabaseException
    {
        final List<VehicleData> vehicleDataList = new ArrayList<>();
        final List<VehicleData> vehicleDataFromOta = getVehicleDataFromOtaDatabase(vehicleData);
        vehicleDataList.addAll(vehicleDataFromOta);
        vehicleDataList.addAll(getVehicle(vehicleData));
        final Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(final VehicleData otaVehicleData : vehicleDataFromOta)
                {
                    try
                    {
                        createNewVehicleData(otaVehicleData);
                    }
                    catch(final DatabaseException e)
                    {
                        //ignore exception.
                    }
                }
            }
        });
        t.start();
        return vehicleDataList;
    }


    private List<VehicleData> getVehicleDataFromOtaDatabase(final VehicleData vehicleData) throws DatabaseException
    {
        final List<Object> arguments = new ArrayList<>();
        arguments.add(vehicleData.getVin());
        final List<ResultRowMap> resultRowMaps = otaDatabaseService.executeQuery(SELECT_VEHICLES, arguments);
        return convertRowMapsToVehicleDataList(resultRowMaps);
    }

    private List<VehicleData> convertRowMapsToVehicleDataList(final List<ResultRowMap> resultRowMaps) throws DatabaseException
    {
        final List<VehicleData> vehicleDataList = new ArrayList<>();
        for(final ResultRowMap resultRowMap : resultRowMaps)
        {
            final VehicleData result = new VehicleData(resultRowMap.getString("VIN"),
                                                       resultRowMap.getString("MODULE_NAME"),
                                                       resultRowMap.getString("SOFTWARE_NUMBER"),
                                                       resultRowMap.getBoolean("STATUS"),
                                                       resultRowMap.getDate("UPDATE_TIME"),
                                                       resultRowMap.getDate("CREATION_TIME"));
            vehicleDataList.add(result);
        }
        return vehicleDataList;
    }
}
