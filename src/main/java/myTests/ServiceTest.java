package myTests;

import dataAccess.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;
import request.LoadRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.*;
import service.*;

import java.sql.SQLException;

public class ServiceTest {
    private final Event[] EMPTY_ARRAY = {};
    Event brendan_birth = new Event("1", "bubs4444", "brendan", 21.3069,
            157.8583, "United States", "Honolulu", "birth", 2000);
    Event brendan_baptism = new Event("2", "bubs4444", "brendan", 31.1171,
            97.7278, "United States", "Harker Heights", "baptism", 2008);
    Event brendan_mission = new Event("3", "bubs4444", "brendan", 13.1391,
            123.7438, "Philippines", "Legazpi", "mission", 2008);
    Event nullday = new Event(null, null, null, 0.0, 0.0,
            null, null, null, 0);

    Person brendan = new Person("1", "bubs4444", "Brendan", "Izu",
            "M", "2", "3", "Natalie");
    Person jerry = new Person("2", "bubs4444", "Jerry", "Izu",
            "M", "Allen", "Tonya", "3");
    Person bernadette = new Person("3", "bubs4444", "Bernadette", "Izu",
            "F", "Raul", "Norie", "2");
    Person nolan = new Person(null, null, null, null, null,
            null, null, null);

    User brendan_user = new User("bubs4444", "12345", "izububs4444@gmail.com", "Brendan",
            "Izu", "M", "1");
    User jerry_user = new User("kiyoshige", "12345", "jerry.izu@gmail.com", "Jerry",
            "Izu", "M", "2");
    User bernadette_user = new User("stubbern97", "12345", "stubberen.izu97@gmail.com", "Bernadette",
            "Izu", "F", "3");
    User nolan_user = new User(null, null, null, null, null, null, null);

    AuthToken brendan_auth = new AuthToken("1", "bubs4444");
    AuthToken jerry_auth = new AuthToken("2", "kiyoshige7");
    AuthToken bernadette_auth = new AuthToken("3", "stubbern97");
    AuthToken nullday_auth = new AuthToken(null, null);

    @BeforeEach
    public void populateDatabase() {
        try {
            Database db = new Database();
            UserDao udao = new UserDao(db.getConnection());
            PersonDao pdao = new PersonDao(db.getConnection());
            EventDao edao = new EventDao(db.getConnection());
            AuthTokenDao adao = new AuthTokenDao(db.getConnection());

            pdao.insertPerson(brendan);
            pdao.insertPerson(jerry);
            pdao.insertPerson(bernadette);
            udao.insertUser(brendan_user);
            udao.insertUser(jerry_user);
            udao.insertUser(bernadette_user);
            edao.insertEvent(brendan_birth);
            edao.insertEvent(brendan_baptism);
            edao.insertEvent(brendan_mission);
            adao.insertAuthToken(brendan_auth);
            adao.insertAuthToken(jerry_auth);
            adao.insertAuthToken(bernadette_auth);
            db.closeConnection(true);
        } catch (DataAccessException ex) {
            System.out.println("Error occurred while populating database");
        }
    }

    @AfterEach
    public void clearDatabase() {
        try {
            new ClearService().Clear();
        } catch( DataAccessException ex) {
            System.out.println("Error occurred while clearing the database");
        }
    }

    /**
     * NOTE: I only included a positive test for clear service. The only instance in which
     * clear service would fail would be if there was a database already open at the time
     * of its call. Clear service opens its own database connection based on parameters NOT
     * changeable by the user and closes its connection at the point of its return. It will
     * always be true even if it clears an already empty database.
    **/
    @Test
    @DisplayName("Clear Service Positive")
    public void CLEAR() throws DataAccessException {
        // initialize service and give a valid call
        ClearResult result = new ClearService().Clear();

        // open local DAO connections
        Database db = new Database();
        UserDao udao = new UserDao(db.getConnection());
        PersonDao pdao = new PersonDao(db.getConnection());
        EventDao edao = new EventDao(db.getConnection());
        AuthTokenDao adao = new AuthTokenDao(db.getConnection());

        // test to see that service returned a good result
        Assertions.assertTrue(result.success);
        Assertions.assertThrows(DataAccessException.class, ()-> udao.findByUsername("bubs4444"));
        Assertions.assertThrows(DataAccessException.class, ()-> pdao.findById("1"));
        Assertions.assertThrows(DataAccessException.class, ()-> edao.findById("1"));
        Assertions.assertThrows(DataAccessException.class, ()-> adao.findByAuthtoken("1"));

        // close local DAO connections
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Event Positive Test")
    public void EventPositive() throws DataAccessException {
        // initialize service and give a valid call
        EventResult result = new EventService().event("1","1");

        // test to see that service returned a good result
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.success);
        Assertions.assertEquals(brendan_birth.getEvent_id(), result.getEventID());
    }

    @Test
    @DisplayName("Event Negative Test")
    public void EventNegative() throws DataAccessException {
        // initialize service and give an invalid call
        EventResult resultBad = new EventService().event("2","1");

        // test to see that service returned a bad result
        Assertions.assertNotNull(resultBad);
        Assertions.assertFalse(resultBad.success);
        Assertions.assertNotEquals(brendan_birth.getEvent_id(), resultBad.getEventID());
    }

    @Test
    @DisplayName("Events Positive Test")
    public void EventsPositive() {
        // initialize service and give a valid call
        EventsResult result = new EventsService().history("1");

        // test to see that service returned a good result
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.success);
        Assertions.assertEquals(3, result.data.length);
    }

    @Test
    @DisplayName("Events Negative Test")
    public void EventsNegative() {
        // initialize service and give an invalid call
        EventsResult result = new EventsService().history("2");

        // test to see that service returned a bad result
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.success);
        Assertions.assertNull(result.data);
    }

    @Test
    @DisplayName("Fill Positive Test")
    public void FillPositive() throws DataAccessException, SQLException {
        // initialize service and give a valid call
        FillResult result = new FillService().fill("bubs4444", "4");

        // open local DAO connections
        Database db = new Database();
        PersonDao pdao = new PersonDao(db.getConnection());
        EventDao edao = new EventDao(db.getConnection());

        // test to see that service returned a good result
        Assertions.assertTrue(result.success);
        Assertions.assertNotEquals(edao.getALL("bubs4444").toArray(), EMPTY_ARRAY);
        Assertions.assertNotEquals(pdao.getALL("bubs4444").toArray(), EMPTY_ARRAY);

        // close local DAO connections
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Fill Negative Test")
    public void FillNegative() throws DataAccessException {
        // initialize service and give an invalid call
        FillResult result = new FillService().fill("natali3k", "4");

        // test to see that service returned a bad result
        Assertions.assertFalse(result.success);
    }

    @Test
    @DisplayName("Load Positive Test")
    public void LoadPositive() throws DataAccessException {
        // remove the auto-added users, persons, and events so that we can add them manually via load
        clearDatabase();

        // create a valid request object
        LoadRequest request = new LoadRequest();
        request.users.add(brendan_user);
        request.users.add(jerry_user);
        request.users.add(bernadette_user);
        request.persons.add(brendan);
        request.persons.add(jerry);
        request.persons.add(bernadette);
        request.events.add(brendan_birth);
        request.events.add(brendan_baptism);
        request.events.add(brendan_mission);

        // open local DAO connections
        Database db = new Database();
        UserDao udao = new UserDao(db.getConnection());
        PersonDao pdao = new PersonDao(db.getConnection());
        EventDao edao = new EventDao(db.getConnection());

        // initialize service and give a valid call
        LoadResult result = new LoadService().load(request);

        // test to see that service returned a good result
        Assertions.assertTrue(result.success);
        Assertions.assertEquals(brendan_user.getUser_name(), udao.findByUsername("bubs4444").getUser_name());
        Assertions.assertEquals(brendan.getPerson_id(), pdao.findById("1").getPerson_id());
        Assertions.assertEquals(brendan_birth.getEvent_id(), edao.findById("1").getEvent_id());

        // close local DAO connections
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Load Negative Test")
    public void LoadNegative() throws DataAccessException {
        // remove the auto-added users, persons, and events so that we can add them manually via load
        clearDatabase();

        // create an invalid request object
        LoadRequest request = new LoadRequest();
        request.users.add(nolan_user);
        request.persons.add(nolan);
        request.events.add(nullday);

        // initialize service and give an invalid call
        LoadResult result = new LoadService().load(request);

        // test to see that service returned a bad result
        Assertions.assertFalse(result.success);
    }

    @Test
    @DisplayName("Login Positive Test")
    public void LoginPositive() throws DataAccessException {
        // create a valid request object
        LoginRequest request = new LoginRequest();
        request.username = "bubs4444";
        request.password = "12345";

        // initialize service and give a valid call
        LoginResult result = new LoginService().Login(request);

        // test to see that service returned a good result
        Assertions.assertTrue(result.success);
        Assertions.assertEquals(result.getUsername(), "bubs4444");
    }

    @Test
    @DisplayName("Login Negative Test")
    public void LoginNegative() throws DataAccessException {
        // create an invalid request objects
        LoginRequest request = new LoginRequest();
        request.username = "natali3k";
        request.password = "password";
        LoginRequest badPass = new LoginRequest();
        badPass.username = "bubs4444";
        badPass.password = "password";

        // initialize service and give an invalid call
        LoginResult result = new LoginService().Login(request);
        LoginResult resultBadPass = new LoginService().Login(badPass);

        // test to see that service returned a bad result
        Assertions.assertFalse(result.success);
        Assertions.assertFalse(resultBadPass.success);
    }

    @Test
    @DisplayName("Person Positive Test")
    public void PersonPositive() throws DataAccessException {
        // initialize service and give a valid call
        PersonResult result = new PersonService().person("1","1");

        // test to see that service returned a good result
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.success);
        Assertions.assertEquals(brendan.getPerson_id(), result.getPersonID());
    }

    @Test
    @DisplayName("Person Negative Test")
    public void PersonNegative() throws DataAccessException {
        // initialize service and give an invalid call
        PersonResult result = new PersonService().person("2", "1");

        // test to see that service returned a bad result
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.success);
    }

    @Test
    @DisplayName("Persons Positive Test")
    public void PersonsPositive() {
        // initialize service and give a valid call
        PersonsResult result = new PersonsService().family("1");

        // test to see that service returned a good result
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.success);
        Assertions.assertEquals(3, result.data.length);
    }

    @Test
    @DisplayName("Persons Negative Test")
    public void PersonsNegative() {
        // initialize service and give an invalid call
        EventsResult result = new EventsService().history("2");

        // test to see that service returned a bad result
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.success);
        Assertions.assertNull(result.data);
    }

    @Test
    @DisplayName("Register Negative Test")
    public void RegisterPositive() throws DataAccessException, SQLException {
        // create a valid request object
        RegisterRequest request = new RegisterRequest();
        request.username = "natali3k";
        request.password = "password";
        request.email = "nataliek.green@gmail.com";
        request.firstName = "natalie";
        request.lastName = "green";
        request.gender = "f";

        // initialize service and give a valid call
        RegisterResult result = new RegisterService().Register(request);

        // open local DAO connections
        Database db = new Database();
        UserDao udao = new UserDao(db.getConnection());
        PersonDao pdao = new PersonDao(db.getConnection());
        EventDao edao = new EventDao(db.getConnection());
        AuthTokenDao adao = new AuthTokenDao(db.getConnection());

        // test to see that service returned a good result
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.success);
        Assertions.assertEquals(udao.findByUsername("natali3k").getUser_name(), "natali3k");
        Assertions.assertNotEquals(edao.getALL("natali3k").toArray().length, EMPTY_ARRAY.length);
        Assertions.assertNotEquals(pdao.getALL("natali3k").toArray().length, EMPTY_ARRAY.length);

        // close local DAO connections
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Register Negative Test")
    public void RegisterNegative() throws DataAccessException {
        // create an invalid request object
        RegisterRequest request = new RegisterRequest();
        request.username = "bubs4444";
        request.password = "password";
        request.email = "izububs4444@gmail.com";
        request.firstName = "brendan";
        request.lastName = "izu";
        request.gender = "m";

        // initialize service and give an invalid call
        RegisterResult result = new RegisterService().Register(request);

        // test to see that service returned a good result
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.success);
    }
}
