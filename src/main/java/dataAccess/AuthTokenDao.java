package dataAccess;

import java.sql.*;

import model.AuthToken;
import model.User;

import javax.xml.crypto.Data;

public class AuthTokenDao extends Database {
    public AuthTokenDao(){}

    /**
     * creates new AuthTokenDao object
     *
     * @param conn Connection to database
     */
    public AuthTokenDao(Connection conn) {
        setConn(conn);
    }

    /**
     * clears AuthToken table
     * @throws DataAccessException if SQLException is encountered
     */
    public void clearAuthTokenTable() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthToken";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }
    /**
     * INSERTS new AuthToken object
     *
     * @param auth AuthToken object
     */
    public void insertAuthToken(AuthToken auth) throws DataAccessException {
        String SQL_INSERT = "INSERT INTO AuthToken (auth_token, username) VALUES(?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, auth.getAuthToken());
            stmt.setString(2, auth.getUsername());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println("Failed to insert AuthToken " + auth.getAuthToken());
            throw new DataAccessException();
        }
    }

    public AuthToken findByAuthtoken(String auth_token) throws DataAccessException {
        String SQL_SELECT = "SELECT * FROM AuthToken WHERE auth_token=?";
        try(PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
            stmt.setString(1, auth_token);
            try(ResultSet rs = stmt.executeQuery()) {
                return new AuthToken(rs.getString("auth_token"), rs.getString("username"));
            } catch(NullPointerException ex) {
                throw new DataAccessException();
            }

        } catch(SQLException ex) {
            throw new DataAccessException();
        }
    }
}
