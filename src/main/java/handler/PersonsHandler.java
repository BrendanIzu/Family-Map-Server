package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import result.PersonsResult;
import service.PersonsService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonsHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("FAMILY");
        if(exchange.getRequestMethod().equalsIgnoreCase("get")) {
            Headers reqHeaders = exchange.getRequestHeaders();
            Gson gson = new Gson();

            if(reqHeaders.containsKey("Authorization")) {
                String authtoken = reqHeaders.getFirst("Authorization");
                PersonsResult result = new PersonsService().family(authtoken);

                if(result.success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                writeString(gson.toJson(result), exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
        }
    }
}
