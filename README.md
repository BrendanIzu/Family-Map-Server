# Family-Map-Server

Back end portion of Family Map Project. This project was build in Intellij version 2021.2.1

# How to Run
1. Clone github repository
2. Create new module using 'Shared' folder
3. Create dependancy in main project folder to Shared module
4. Specify program arguments to be '8080' (or whatever is the desired port)
5. visit localhost:8080 to test out database calls and server functionality 

# About the Project

## Generator Classes
1. Generator - Generates new random IDs
2. PersonGenerator - Generates a new person object with random first and last name as well as two parent persons.
3. EventsGenerator - Generates events for a person object. Generates "couple events," which ensure that birth, marriage, and death events between spouses match up reasonably
4. HistoryGenerator - Builds family tree for a new person up to 4 generations deep
5. Locations, Names, Place - returns random location, name, or place

## Data Access Classes 
Used for handling communication from server to databse. 
1. Database - establishes connection between server and database, initialization of tables, and closing of connection
2. AuthTokenDao - Handles the creation, deletion, and fethcing of Authentication Tokens
3. EventDao - Handles the creation, deletion, and fetching of Events 
4. PersonDao - Handles the creation, deletion, and fetching of Persons
5. UserDao - Handles the creation, deletion, and fetching of Users and information associated with the User

## Handler Classes
Used for handling HTTP requests made to the server. All handler classes operate similarly, using an HttpExchange object to send and receive responses to the server. Handler classes utilize the functionality of Result and Service classes.

## Request and Result Classes
Package requests and result bodies which are then used by service classes.

## Service Classes
Interpret request class objects and return result class objects to handler class

## Web APIs
| URL  | Description | Request Body  | Response Body | Error Response |
| -----| ----------- | ------------- | ------------- | -------------- |
| `/user/register` | Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token | username, password, firstname, lastname, email, gender | authToken, username, personID | Description of the error | 
| `/user/login` | Logs in the user and returns an auth token | username, password | authToken, username, personID | Description of the error |
| `/clear` | Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data | | Success message | Description of the error |
| `/fill/[username]/{generations}` | Populates the server's database with generated data for the specified user name. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given user name, it is deleted. The optional “generations” parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events) | Success message | Description of the error |
| `/load` | Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database. | | users, person, and events arrays | Description of the error |
| `/person/[personID]` | Returns the single Person object with the specified ID | | descendant (name of user this Person is connected to), personID, firstname, lastname, gender, father, mother, spouse | Description of the error |
| `/person` | Returns ALL family members of the current user. The current user is determined from the provided auth token | | Array of Persons | Description of the error |
| `/event/[eventID]` | Returns the single Event object with the specified ID | | descendant (name of user this Person is connected to), eventID, personID, latitude, longitude, country, city, eventType, year | Description of the error |
| `/event` | Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token | | Array of Events | Description of the error |

