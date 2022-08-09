package ota.sync.vehicle;

import ota.sync.vehicle.exception.VehicleServiceException;

import java.io.File;
import java.util.List;

/**
 * VehicleService interface handles functionalities related to
 * vehicle data and vin.
 */
public interface VehicleService
{
    /**
     * get vehicle data.
     * @param vin vin.
     * @return vehicle data list.
     * @throws VehicleServiceException if operation fails.
     */
    List<VehicleData> getVehicleData(String vin) throws VehicleServiceException;

    /**
     * refresh vehicle data.
     * @param vin vin.
     * @return vehicle data list.
     * @throws VehicleServiceException if operation fails.
     */
    List<VehicleData> refreshVehicleData(String vin) throws VehicleServiceException;

    /**
     * add vehicle data.
     * @param vehicleData vehicle data.
     * @return true if succeeds.
     * @throws VehicleServiceException if operation fails.
     */
    boolean addVehicleData(VehicleData vehicleData) throws VehicleServiceException;

    /**
     * modify vehicle data.
     * @param vehicleData vehicle data.
     * @return true if succeeds.
     * @throws VehicleServiceException if operation fails.
     */
    boolean modifyVehicleData(VehicleData vehicleData) throws VehicleServiceException;

    /**
     * export vehicle data.
     * @param vehicleDataList vehicle data list.
     * @return true if succeeds.
     * @throws VehicleServiceException if operation fails.
     */
    File exportVehicleData(List<VehicleData> vehicleDataList) throws VehicleServiceException;

    /**
     * delete vehicle data.
     * @param vehicleData vehicle data.
     * @return true if succeeds.
     * @throws VehicleServiceException if operation fails.
     */
    boolean deleteVehicleData(VehicleData vehicleData) throws VehicleServiceException;
}
