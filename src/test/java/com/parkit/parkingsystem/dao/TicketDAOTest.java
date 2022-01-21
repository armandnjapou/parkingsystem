package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.Date;

class TicketDAOTest {
    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private static final Ticket ticket = new Ticket();
    private static final ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
    private static final String vehicleRegNumber = "CF564JY";

    @BeforeAll
    private static void setUp() {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
        ticket.setId(1);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber(vehicleRegNumber);
        ticket.setPrice(0);
        ticket.setInTime(new Date());
        ticket.setOutTime(null);
    }

    @BeforeEach
    private void setUpPerTest() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void should_save_new_ticket_in_db_when_invoke_save_method() {
        Assertions.assertFalse(ticketDAO.saveTicket(ticket));
    }

    @Nested
    class TicketDaoNestedTest {
        @BeforeEach
        public void init() {
            ticketDAO.saveTicket(ticket);
        }
        @Test
        public void should_return_ticket_when_ticket_exists_in_db() {
            Ticket expectedTicket = ticketDAO.getTicket(vehicleRegNumber);
            Assertions.assertEquals(expectedTicket.getId(), ticket.getId());
        }

        @Test
        public void should_return_null_when_ticket_doesnt_exists_in_db() {
            Ticket expectedTicket = ticketDAO.getTicket("AG547KH");
            Assertions.assertNull(expectedTicket);
        }

        @Test
        public void should_change_ticket_price_to_15_and_outTime_to_now_when_update_ticket_price_to_15_and_outTime_to_now() {
            ticket.setPrice(15);
            ticket.setOutTime(new Date());
            Assertions.assertTrue(ticketDAO.updateTicket(ticket));
        }
    }
}