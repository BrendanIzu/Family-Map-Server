package dataAccess;

import java.sql.*;
import java.util.ArrayList;

import model.Event;
import model.Person;

import javax.xml.crypto.Data;

public class PersonDao extends Database {
    public PersonDao(){}

    /**
     * creates new PersonDao object
     *
     * @param conn Connection to database
     */
    public PersonDao(Connection conn) {
        setConn(conn);
    }

    /**
     * clears Person table
     * @throws DataAccessException if SQLException is encountered
     */
    public void clearPersonTable() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

    /**
     * INSERTS new Person object
     *
     * @param person Person object
     */
    public void insertPerson(Person person) throws DataAccessException {
        String SQL_INSERT = "INSERT INTO Person (person_id, username, first_name, last_name, gender, father_id, mother_id, spouse_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, person.getPerson_id());
            stmt.setString(2, person.getUser_name());
            stmt.setString(3, person.getFirst_name());
            stmt.setString(4, person.getLast_name());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFather_id());
            stmt.setString(7, person.getMother_id());
            stmt.setString(8, person.getSpouse_id());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println("Failed to insert Person " + person.getPerson_id());
            throw new DataAccessException();
        }
    }

    /**
     * find a Person associated to an id
     *
     * @param person_id id we are trying to locate the Person of
     * @return Person object associated with an id, null if there is none that exist
     */
    public Person findById(String person_id) throws DataAccessException{
        String SQL_SELECT = "SELECT * FROM Person where person_id=?";
        try(PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
            stmt.setString(1, person_id);
            try(ResultSet rs = stmt.executeQuery()) {
                String username = rs.getString("username");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String gender = rs.getString("gender");
                String father_id = rs.getString("father_id");
                String mother_id = rs.getString("mother_id");
                String spouse_id = rs.getString("spouse_id");
                return new Person(person_id, username, first_name, last_name, gender, father_id, mother_id, spouse_id);
            } catch(NullPointerException ex) {
                throw new DataAccessException();
            }
        } catch(SQLException ex) {
            throw new DataAccessException();
        }
    }

    public void clearUserPersons(String username) throws DataAccessException {
        String SQL_DELETE = "DELETE FROM Person WHERE username=?";
        try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("We deleted all of " + username + "\'s persons");
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

    public ArrayList<Person> getALL(String username) throws SQLException {
        String SQL_SELECT = "SELECT * from Person WHERE username=" +
                "(SELECT username FROM Person WHERE username=?)";
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
            stmt.setString(1, username);
            ArrayList<Person> family = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                family.add(new Person(rs.getString("person_id"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("father_id"),
                        rs.getString("mother_id"),
                        rs.getString("spouse_id")));
            }
            return family;
        } catch (SQLException ex) {
            System.out.println("Encountered SQLException when getting ALL");
            return null;
        }
    }
}
