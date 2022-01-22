package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParkingSpotDAOTest {

    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static final ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
    private static DataBasePrepareService dataBasePrepareService;


    @BeforeAll
    private static void setUp() {
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void should_return_parking_number_1_when_get_next_available_spot() {
        Assertions.assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }

    @Test
    public void should_update_next_available_parking_spot_to_false_when_update_next_available_parking_spot () {
        int nextBikeAvailableSpot = parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
        ParkingSpot expectedParkingSpot = new ParkingSpot(nextBikeAvailableSpot, ParkingType.BIKE, false);
        parkingSpotDAO.updateParking(expectedParkingSpot);
        Assertions.assertNotEquals(nextBikeAvailableSpot, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
    }

    @Test
    public void should_return_false_when_update_non_existing_parking_spot() {
        ParkingSpot expectedParkingSpot = new ParkingSpot(1000, ParkingType.BIKE, false);
        Assertions.assertFalse(parkingSpotDAO.updateParking(expectedParkingSpot));
    }
}