package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import result.EventsResult;
import service.EventsService;
import java.io.IOException;
import java.net.HttpURLConnection;

public class EventsHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("HISTORY");
        if(exchange.getRequestMethod().equalsIgnoreCase("get")) {
            Headers reqHeaders = exchange.getRequestHeaders();
            Gson gson = new Gson();

            if(reqHeaders.containsKey("Authorization")) {
                String authtoken = reqHeaders.getFirst("Authorization");
                EventsResult result = new EventsService().history(authtoken);

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
