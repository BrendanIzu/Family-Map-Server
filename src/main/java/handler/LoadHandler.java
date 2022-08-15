package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataAccess.DataAccessException;
import request.LoadRequest;
import request.RegisterRequest;
import result.LoadResult;
import result.RegisterResult;
import service.LoadService;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("LOAD");
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();
                InputStream reqBody = exchange.getRequestBody();

                LoadRequest request = gson.fromJson(readString(reqBody), LoadRequest.class);
                LoadResult result = new LoadService().load(request);

                if(result.success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                writeString(gson.toJson(result), exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
        } catch (DataAccessException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
