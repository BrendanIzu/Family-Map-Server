import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handler.*;

/*
	This example demonstrates the basic structure of the Family Map Server
	(although it is for a fictitious "Ticket to Ride" game, not Family Map).
	The example is greatly simplified to help you more easily understand the
	basic elements of the server.

	The Server class is the "main" class for the server (i.e., it contains the
		"main" method for the server program).
	When the server runs, all command-line arguments are passed in to Server.main.
	For this server, the only command-line argument is the port number on which
		the server should accept incoming client connections.
*/
public class Server {

    // The maximum number of waiting incoming connections to queue.
    // While this value is necessary, for our purposes it is unimportant.
    // Take CS 460 for a deeper understanding of what it means.
    private static final int MAX_WAITING_CONNECTIONS = 12;

    // Java provides an HttpServer class that can be used to embed
    // an HTTP server in any Java program.
    // Using the HttpServer class, you can easily make a Java
    // program that can receive incoming HTTP requests, and respond
    // with appropriate HTTP responses.
    // HttpServer is the class that actually implements the HTTP network
    // protocol (be glad you don't have to).
    // The "server" field contains the HttpServer instance for this program,
    // which is initialized in the "run" method below.
    private HttpServer server;

    // This method initializes and runs the server.
    // The "portNumber" parameter specifies the port number on which the
    // server should accept incoming client connections.
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);
        System.out.println("Creating contexts");

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill/", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person/", new PersonHandler());
        server.createContext("/person", new PersonsHandler());
        server.createContext("/event/", new EventHandler());
        server.createContext("/event", new EventsHandler());

        server.createContext("/", new FileHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    // "main" method for the server program
    // "args" should contain one command-line argument, which is the port number
    // on which the server should accept incoming client connections.
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
