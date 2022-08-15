package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataAccess.DataAccessException;
import result.ClearResult;
import service.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("CLEAR");
        try {
            if(exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();
                ClearResult result = new ClearService().Clear();

                if(result.success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                writeString(gson.toJson(result), exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
        } catch(IOException | DataAccessException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
