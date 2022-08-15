package myTests;

import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private final User user = null;
    private final UserDao udao = new UserDao();

    User brendan = new User("bubs4444", "12345", "izububs4444@gmail.com", "Brendan",
            "Izu", "M", "brendan");
    User jerry = new User("kiyoshige", "12345", "jerry.izu@gmail.com", "Jerry",
            "Izu", "M", "jerry");
    User bernadette = new User("stubbern97", "12345", "stubberen.izu97@gmail.com", "Bernadette",
            "Izu", "F", "bern");
    User nolan = new User(null, null, null, null, null, null, null);

    @Test
    @DisplayName("Positive Insert Test")
    public void testPositiveINSERT() throws DataAccessException {
        udao.openConnection();
        udao.insertUser(brendan);
        udao.insertUser(jerry);
        udao.insertUser(bernadette);

        Assertions.assertNotNull(udao.findByUsername("bubs4444"));
        Assertions.assertNotNull(udao.findByUsername("kiyoshige"));
        Assertions.assertNotNull(udao.findByUsername("stubbern97"));

        udao.closeConnection(false);
    }

    @Test
    @DisplayName("Negative Insert Test")
    public void testNegativeINSERT() throws DataAccessException {
        udao.openConnection();
        Assertions.assertThrows(DataAccessException.class, ()-> udao.insertUser(nolan));
        udao.closeConnection(false);
    }

    @Test
    @DisplayName("Positive Find Test")
    public void testPositiveFIND() throws DataAccessException {
        udao.openConnection();
        udao.insertUser(brendan);
        udao.insertUser(jerry);
        udao.insertUser(bernadette);

        Assertions.assertEquals(brendan.getPerson_id(), udao.findByUsername("bubs4444").getPerson_id());
        Assertions.assertEquals(jerry.getPerson_id(), udao.findByUsername("kiyoshige").getPerson_id());
        Assertions.assertEquals(bernadette.getPerson_id(), udao.findByUsername("stubbern97").getPerson_id());

        udao.closeConnection(false);
    }

    @Test
    @DisplayName("Negative Find Test")
    public void testNegativeFIND() throws DataAccessException {
        udao.openConnection();
        udao.insertUser(brendan);
        udao.insertUser(jerry);

        Assertions.assertNotEquals(brendan.getPerson_id(), udao.findByUsername("kiyoshige").getPerson_id());
        Assertions.assertNotEquals(jerry.getPerson_id(), udao.findByUsername("bubs4444").getPerson_id());

        Assertions.assertThrows(DataAccessException.class, ()-> udao.findByUsername("stubbern97"));

        udao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Clear Function")
    public void testCLEAR() throws DataAccessException {
        udao.openConnection();
        udao.insertUser(brendan);
        udao.insertUser(jerry);
        udao.insertUser(bernadette);

        Assertions.assertNotNull(udao.findByUsername("bubs4444"));
        Assertions.assertNotNull(udao.findByUsername("kiyoshige"));
        Assertions.assertNotNull(udao.findByUsername("stubbern97"));

        udao.clearUserTable();

        Assertions.assertThrows(DataAccessException.class, ()-> udao.findByUsername("bubs4444"));
        Assertions.assertThrows(DataAccessException.class, ()-> udao.findByUsername("kiyoshige"));
        Assertions.assertThrows(DataAccessException.class, ()-> udao.findByUsername("stubbern97"));

        udao.closeConnection(false);
    }
}
