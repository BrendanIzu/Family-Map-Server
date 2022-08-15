package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dataAccess.DataAccessException;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("LOGIN");
        try {
            if(exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();
                InputStream reqBody = exchange.getRequestBody();

                LoginRequest request = gson.fromJson(readString(reqBody), LoginRequest.class);
                LoginResult result = new LoginService().Login(request);

                if(!result.success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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
