package myTests;

import dataAccess.DataAccessException;
import dataAccess.EventDao;
import model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class EventDaoTest {
    private final Event person = null;
    private final EventDao edao = new EventDao();
    private final Event[] EMPTY_ARRAY = {};
    Event brendan_birth = new Event("1", "bubs4444", "brendan", 21.3069,
            157.8583, "United States", "Honolulu", "birth", 2000);
    Event brendan_baptism = new Event("2", "bubs4444", "brendan", 31.1171,
            97.7278, "United States", "Harker Heights", "baptism", 2008);
    Event brendan_mission = new Event("3", "bubs4444", "brendan", 13.1391,
            123.7438, "Philippines", "Legazpi", "mission", 2008);
    Event nullday = new Event(null, null, null, 0.0, 0.0,
            null, null, null, 0);
    @Test
    @DisplayName("Testing Insert Function")
    public void testINSERT() throws DataAccessException {
        edao.openConnection();
        edao.insertEvent(brendan_birth);
        edao.insertEvent(brendan_baptism);
        edao.insertEvent(brendan_mission);

        // POSITIVE TEST
        Assertions.assertNotNull(edao.findById("1"));
        Assertions.assertNotNull(edao.findById("2"));
        Assertions.assertNotNull(edao.findById("3"));

        // NEGATIVE TEST
        Assertions.assertThrows(DataAccessException.class, ()-> edao.insertEvent(nullday));

        edao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Clear Function")
    public void testCLEAR() throws DataAccessException {
        edao.openConnection();
        edao.insertEvent(brendan_birth);
        edao.insertEvent(brendan_baptism);
        edao.insertEvent(brendan_mission);

        Assertions.assertNotNull(edao.findById("1"));
        Assertions.assertNotNull(edao.findById("2"));
        Assertions.assertNotNull(edao.findById("3"));

        edao.clearEventTable();
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("1"));
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("2"));
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("3"));

        edao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Find Function")
    public void testFIND() throws DataAccessException {
        edao.openConnection();
        edao.insertEvent(brendan_birth);
        edao.insertEvent(brendan_baptism);
        edao.insertEvent(brendan_mission);

        Assertions.assertEquals(edao.findById("1").getEvent_id(), brendan_birth.getEvent_id());
        Assertions.assertEquals(edao.findById("2").getEvent_id(), brendan_baptism.getEvent_id());
        Assertions.assertEquals(edao.findById("3").getEvent_id(), brendan_mission.getEvent_id());
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("4"));
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("5"));
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("6"));

        edao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Clear From Function")
    public void testCLEARFROM() throws DataAccessException {
        edao.openConnection();
        edao.insertEvent(brendan_birth);
        edao.insertEvent(brendan_baptism);
        edao.insertEvent(brendan_mission);

        Assertions.assertNotNull(edao.findById("1"));
        Assertions.assertNotNull(edao.findById("2"));
        Assertions.assertNotNull(edao.findById("3"));

        edao.clearUserEvents("bubs4444");
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("1"));
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("2"));
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("3"));

        edao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Get All Function")
    public void testGETALL() throws DataAccessException, SQLException {
        edao.openConnection();
        edao.insertEvent(brendan_birth);
        edao.insertEvent(brendan_baptism);
        edao.insertEvent(brendan_mission);

        Assertions.assertNotNull(edao.getALL("bubs4444"));
        Assertions.assertEquals(EMPTY_ARRAY.length, edao.getALL("natali3k").toArray().length);

        edao.closeConnection(false);
    }
}
