package myTests;

import dataAccess.DataAccessException;
import dataAccess.PersonDao;
import model.Event;
import model.Person;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.sql.SQLException;

public class PersonDaoTest {
    private final Person person = null;
    private final PersonDao pdao = new PersonDao();
    private final Event[] EMPTY_ARRAY = {};

    Person brendan = new Person("1", "bubs4444", "Brendan", "Izu",
            "M", "Jerry", "Bernadette", "Natalie");
    Person jerry = new Person("2", "kiyoshige7", "Jerry", "Izu",
            "M", "Allen", "Tonya", "Bernadette");
    Person bernadette = new Person("3", "stubbern.97", "Bernadette", "Izu",
            "F", "Raul", "Norie", "Jerry");
    Person nolan = new Person(null, null, null, null, null,
            null, null, null);


    @Test
    @DisplayName("Positive Insert Test")
    public void testPositiveINSERT() throws DataAccessException {
        pdao.openConnection();
        pdao.insertPerson(brendan);
        pdao.insertPerson(jerry);
        pdao.insertPerson(bernadette);

        Assertions.assertNotNull(pdao.findById("1"));
        Assertions.assertNotNull(pdao.findById("2"));
        Assertions.assertNotNull(pdao.findById("3"));

        pdao.closeConnection(false);
    }

    @Test
    @DisplayName("Negative Insert Test")
    public void testNegativeINSERT() throws DataAccessException {
        pdao.openConnection();
        Assertions.assertThrows(DataAccessException.class, ()-> pdao.insertPerson(nolan));
        pdao.closeConnection(false);
    }

    @Test
    @DisplayName("Positive Find Test")
    public void testPositiveFIND() throws DataAccessException {
        pdao.openConnection();
        pdao.insertPerson(brendan);
        pdao.insertPerson(jerry);
        pdao.insertPerson(bernadette);

        Assertions.assertEquals(brendan.getPerson_id(), pdao.findById("1").getPerson_id());
        Assertions.assertEquals(jerry.getPerson_id(), pdao.findById("2").getPerson_id());
        Assertions.assertEquals(bernadette.getPerson_id(), pdao.findById("3").getPerson_id());

        pdao.closeConnection(false);
    }

    @Test
    @DisplayName("Negative Find Test")
    public void testNegativeFIND() throws DataAccessException {
        pdao.openConnection();
        pdao.insertPerson(brendan);
        pdao.insertPerson(jerry);

        Assertions.assertNotEquals(brendan.getPerson_id(), pdao.findById("2").getPerson_id());
        Assertions.assertNotEquals(jerry.getPerson_id(), pdao.findById("1").getPerson_id());

        Assertions.assertThrows(DataAccessException.class, ()-> pdao.findById("3"));

        pdao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Clear Function")
    public void testCLEAR() throws DataAccessException {
        pdao.openConnection();
        pdao.insertPerson(brendan);
        pdao.insertPerson(jerry);
        pdao.insertPerson(bernadette);

        Assertions.assertNotNull(pdao.findById("1"));
        Assertions.assertNotNull(pdao.findById("2"));
        Assertions.assertNotNull(pdao.findById("3"));

        pdao.clearPersonTable();

        Assertions.assertThrows(DataAccessException.class, ()-> pdao.findById("1"));
        Assertions.assertThrows(DataAccessException.class, ()-> pdao.findById("2"));
        Assertions.assertThrows(DataAccessException.class, ()-> pdao.findById("3"));

        pdao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Clear From Function")
    public void testCLEARFROM() throws DataAccessException {
        pdao.openConnection();
        pdao.insertPerson(brendan);
        pdao.insertPerson(jerry);
        pdao.insertPerson(bernadette);

        Assertions.assertNotNull(pdao.findById("1"));
        Assertions.assertNotNull(pdao.findById("2"));
        Assertions.assertNotNull(pdao.findById("3"));

        pdao.clearUserPersons("bubs4444");
        Assertions.assertThrows(DataAccessException.class, ()-> pdao.findById("1"));

        pdao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Get All Function")
    public void testGETALL() throws DataAccessException, SQLException {
        pdao.openConnection();
        pdao.insertPerson(brendan);
        pdao.insertPerson(jerry);
        pdao.insertPerson(bernadette);

        Assertions.assertNotNull(pdao.getALL("bubs4444"));
        Assertions.assertEquals(EMPTY_ARRAY.length, pdao.getALL("natali3k").toArray().length);

        pdao.closeConnection(false);
    }
}

