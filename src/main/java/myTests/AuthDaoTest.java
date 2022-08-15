package myTests;

import dataAccess.*;
import model.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthDaoTest {
    private final AuthToken auth = null;
    private final AuthTokenDao adao = new AuthTokenDao();
    AuthToken Jailyn = new AuthToken("1", "jailyn");
    AuthToken Brendan = new AuthToken("2", "brendan");
    AuthToken Julia = new AuthToken("3", "julia");
    AuthToken nullday = new AuthToken(null, null);
    @Test
    @DisplayName("Testing Insert Function")
    public void testINSERT() throws DataAccessException {
        adao.openConnection();
        adao.insertAuthToken(Jailyn);
        adao.insertAuthToken(Brendan);
        adao.insertAuthToken(Julia);
        // POSITIVE TEST
        Assertions.assertNotNull(adao.findByAuthtoken("1"));
        Assertions.assertNotNull(adao.findByAuthtoken("2"));
        Assertions.assertNotNull(adao.findByAuthtoken("3"));

        // NEGATIVE TEST
        Assertions.assertThrows(DataAccessException.class, ()-> adao.insertAuthToken(nullday));

        adao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Clear Function")
    public void testCLEAR() throws DataAccessException {
        adao.openConnection();
        adao.insertAuthToken(Jailyn);
        adao.insertAuthToken(Brendan);
        adao.insertAuthToken(Julia);

        Assertions.assertNotNull(adao.findByAuthtoken("1"));
        Assertions.assertNotNull(adao.findByAuthtoken("2"));
        Assertions.assertNotNull(adao.findByAuthtoken("3"));

        adao.clearAuthTokenTable();
        Assertions.assertThrows(DataAccessException.class, ()-> adao.findByAuthtoken("1"));
        Assertions.assertThrows(DataAccessException.class, ()-> adao.findByAuthtoken("2"));
        Assertions.assertThrows(DataAccessException.class, ()-> adao.findByAuthtoken("3"));

        adao.closeConnection(false);
    }

    @Test
    @DisplayName("Testing Find Function")
    public void testFIND() throws DataAccessException {
        adao.openConnection();
        adao.insertAuthToken(Jailyn);
        adao.insertAuthToken(Brendan);
        adao.insertAuthToken(Julia);

        Assertions.assertEquals(adao.findByAuthtoken("1").getAuthToken(), Jailyn.getAuthToken());
        Assertions.assertEquals(adao.findByAuthtoken("2").getAuthToken(), Brendan.getAuthToken());
        Assertions.assertEquals(adao.findByAuthtoken("3").getAuthToken(), Julia.getAuthToken());
        Assertions.assertThrows(DataAccessException.class, ()-> adao.findByAuthtoken("4"));

        adao.closeConnection(false);
    }
}