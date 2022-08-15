
package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
          System.out.println("REGISTER");
          try {
               if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                    Gson gson = new Gson();
                    InputStream reqBody = exchange.getRequestBody();
                    RegisterRequest request = gson.fromJson(readString(reqBody), RegisterRequest.class);
                    RegisterResult result = new RegisterService().Register(request);

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