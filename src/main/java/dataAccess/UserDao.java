package dataAccess;

import model.Event;
import model.Person;
import model.User;

import java.sql.*;

public class UserDao extends Database {
    public UserDao(){}

    /**
     * creates new UserDao object
     *
     * @param conn Connection to database
     */
    public UserDao(Connection conn) {
        setConn(conn);
    }

    /**
     * clears User table
     * @throws DataAccessException if SQLException is encountered
     */
    public void clearUserTable() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM User";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

    /**
     * INSERTS new User object
     *
     * @param user User object
     */
    public void insertUser(User user) throws DataAccessException {
        String SQL_INSERT = "INSERT INTO User (username, password, email_address, first_name, last_name, gender, " +
                "person_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, user.getUser_name());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirst_name());
            stmt.setString(5, user.getLast_name());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPerson_id());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println("Failed to insert User " + user.getUser_name());
            throw new DataAccessException();
        }
    }

    /**
     * find a User associated to a username
     *
     * @param username username we are trying to locate the User of
     * @return User object associated with a username, null if there is none that exist
     */
    public User findByUsername(String username) throws DataAccessException {
        String SQL_SELECT = "SELECT * FROM User where username=?";
        try(PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()){
                String password = rs.getString("password");
                String email = rs.getString("email_address");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String gender = rs.getString("gender");
                String person_id = rs.getString("person_id");
                return new User(username, password, email, first_name, last_name, gender, person_id);
            } catch(NullPointerException ex) {
                throw new DataAccessException();
            }
        } catch(SQLException ex) {
            throw new DataAccessException();
        }
    }
}
