package handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.Objects;

public class FileHandler implements Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if(exchange.getRequestMethod().equalsIgnoreCase("get")) {
                String url_path = exchange.getRequestURI().toString();
                if(Objects.equals(url_path, "/") || url_path == null) {
                    url_path = "/index.html";
                }
                String file_path = "/web" + url_path;
                File start = new File(System.getProperty("user.dir"));
                File file = new File(start,file_path);
                if(file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    file = new File(start,"/web/HTML/404.html");
                }
                Files.copy(file.toPath(),exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
        } catch(IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
