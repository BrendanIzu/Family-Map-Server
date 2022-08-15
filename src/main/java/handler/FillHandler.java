package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dataAccess.DataAccessException;
import result.FillResult;
import service.FillService;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        System.out.println("FILL");
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                String url_path = exchange.getRequestURI().toString();
                Gson gson = new Gson();

                String[]params = url_path.split("/");
                String firstParam = params[2];
                String secondParam = "";

                if(params.length == 3) {
                    secondParam = "4";
                } else {
                    secondParam = params[3];
                }

                FillResult result = new FillService().fill(firstParam, secondParam);
                if(result.success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                writeString(gson.toJson(result), exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
        } catch (IOException | DataAccessException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}