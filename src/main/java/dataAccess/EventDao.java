package dataAccess;

import java.sql.*;
import java.util.ArrayList;
import model.Event;

public class EventDao extends Database {
    public EventDao() {}

    /**
     * create new EventDao object
     *
     * @param conn Connection to databse
     */
    public EventDao(Connection conn) {
        setConn(conn);
    }

    /**
     * clears Event table
     *
     * @throws DataAccessException if SQLException is encountered
     */
    public void clearEventTable() throws DataAccessException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Event");
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing table \"Event\"");
        }
    }

    /**
     * INSERTS new event object into SQL database
     *
     * @param event Event object
     */
    public void insertEvent(Event event) throws DataAccessException {
        String SQL_INSERT = "INSERT INTO Event (event_id, username, person_id, latitude, longitude, country, city, event, " +
                "year) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, event.getEvent_id());
            stmt.setString(2, event.getUser_name());
            stmt.setString(3, event.getPerson_id());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEvent_type());
            stmt.setInt(9, event.getYear());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed to insert Event " + event.getEvent_id());
            throw new DataAccessException();
        }
    }

    /**
     * find an Event associated to an id
     *
     * @param event_id id we are trying to locate the event of
     * @return Event object associated with an id, null if there is none that exist
     */
    public Event findById(String event_id) throws DataAccessException {
        String SQL_SELECT = "SELECT * FROM Event WHERE event_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
            stmt.setString(1, event_id);
            try (ResultSet rs = stmt.executeQuery()) {
                String username = rs.getString("username");
                String person_id = rs.getString("person_id");
                Double latitude = rs.getDouble("latitude");
                Double longitude = rs.getDouble("longitude");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String event = rs.getString("event");
                int year = rs.getInt("year");
                return new Event(event_id, username, person_id, latitude, longitude, country, city, event, year);
            } catch (NullPointerException ex) {
                throw new DataAccessException();
            }
        } catch (SQLException ex) {
            throw new DataAccessException();
        }
    }

    public void clearUserEvents(String username) throws DataAccessException {
        String SQL_DELETE = "DELETE FROM Event WHERE username=?";
        try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("We deleted all of " + username + "\'s events");
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

    public ArrayList<Event> getALL(String username) throws SQLException {
        String SQL_SELECT = "SELECT * from Event WHERE username=" +
                "(SELECT username FROM Event WHERE username=?)";
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
            stmt.setString(1, username);
            ArrayList<Event> history = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                history.add(new Event(rs.getString("event_id"),
                        rs.getString("username"),
                        rs.getString("person_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("event"),
                        rs.getInt("year")));
            }
            return history;
        } catch (SQLException ex) {
            System.out.println("Encountered SQLException when getting ALL");
            return null;
        }
    }
}
