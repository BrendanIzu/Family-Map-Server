package myTests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

public class DatabaseTest {
    @Test
    @DisplayName("create a database")
    public void createAllTables() throws DataAccessException {
        Database db = new Database();
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
    }
}
