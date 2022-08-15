package dataAccess;

import java.sql.*;

public class Database {
    public Database() {}

    /**
     * connection object
     */
    public Connection conn;

    /**
     * Sets connection
     *
     * @param conn Connection object
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * used to open a connection
     * @return Connection conn
     * @throws DataAccessException if SQLException is encountered
     */
    public Connection openConnection() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:family_map.sqlite";
            // Open a database connection to the file given in the path
            setConn(DriverManager.getConnection(CONNECTION_URL));

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }
        return getConnection();
    }

    /**
     * get Connection conn if not null
     * @return Connection conn
     * @throws DataAccessException if SQLException is encountered
     */
    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    /**
     * close Connection to database and commit changes if they exist
     *
     * @param commit true if we want to commit changes to database
     * @throws DataAccessException if SQLException is encountered
     */
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {

                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException {
        try(Statement stmt = conn.createStatement()) {
            // CREATE TABLE User
            stmt.executeUpdate("DROP TABLE if EXISTS User");
            stmt.executeUpdate("CREATE TABLE User " +
                    "(username VARCHAR(255) not NULL," +
                    "password VARCHAR(255) not NULL," +
                    "email_address VARCHAR(255) not NULL," +
                    "first_name VARCHAR(255) not NULL," +
                    "last_name VARCHAR(255) not NULL," +
                    "gender VARCHAR(255) not NULL," +
                    "person_id VARCHAR(255) not NULL," +
                    "PRIMARY KEY (username))");
            stmt.executeUpdate("DROP TABLE if EXISTS Person");
            stmt.executeUpdate("CREATE TABLE Person " +
                    "(person_id VARCHAR(255) not NULL," +
                    "username VARCHAR(255) not NULL," +
                    "first_name VARCHAR(255) not NULL," +
                    "last_name VARCHAR(255) not NULL," +
                    "gender VARCHAR(255) not NULL," +
                    "father_id VARCHAR(255)," +
                    "mother_id VARCHAR(255)," +
                    "spouse_id VARCHAR(255)," +
                    "PRIMARY KEY (person_id))");
            stmt.executeUpdate("DROP TABLE if EXISTS Event");
            stmt.executeUpdate("CREATE TABLE Event " +
                    "(event_id VARCHAR(255) not NULL," +
                    "username VARCHAR(255) not NULL," +
                    "person_id VARCHAR(255) not NULL," +
                    "latitude FLOAT not NULL," +
                    "longitude FLOAT not NULL," +
                    "country VARCHAR(255) not NULL," +
                    "city VARCHAR(255) not NULL," +
                    "event VARCHAR(255) not NULL," +
                    "year INTEGER not NULL," +
                    "PRIMARY KEY (event_id))");
            stmt.executeUpdate("DROP table if EXISTS AuthToken");
            stmt.executeUpdate("CREATE TABLE AuthToken" +
                    "(auth_token VARCHAR(255) not NULL," +
                    "username VARCHAR(255) not NULL," +
                    "PRIMARY KEY (auth_token))");
        } catch(SQLException ex) {
            throw new DataAccessException();
        }
    }

    public void clearAllTables() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            stmt.executeUpdate("DELETE FROM User");
            stmt.executeUpdate("DELETE FROM Person");
            stmt.executeUpdate("DELETE FROM Event");
            stmt.executeUpdate("DELETE FROM AuthToken");
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }
}

