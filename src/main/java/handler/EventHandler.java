package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataAccess.DataAccessException;
import result.EventResult;
import service.EventService;

import java.io.*;
import java.net.HttpURLConnection;

public class EventHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("EVENT");
        try {
            if(exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                Gson gson = new Gson();
                String[] params = exchange.getRequestURI().toString().split("/");

                if(reqHeaders.containsKey("Authorization")) {
                    String authtoken = reqHeaders.getFirst("Authorization");
                    EventResult result = new EventService().event(authtoken, params[2]);

                    if(!result.success) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }

                    writeString(gson.toJson(result), exchange.getResponseBody());
                    exchange.getResponseBody().close();
                }
            }
        } catch (DataAccessException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
