package fr.triobin.workshop;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HTTPServer {

    public static void main(String[] args) {
        try {
            HttpServer serveur = HttpServer.create(new InetSocketAddress(8000), 0);
            serveur.createContext("/", new MonHandler());
            serveur.setExecutor(null); // Default executor
            serveur.start();
            System.out.println("Serveur HTTP démarré sur http://localhost:8000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MonHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange echange) {
            try {
                String reponse = "Bonjour depuis le serveur Java !";
                echange.sendResponseHeaders(200, reponse.length());
                OutputStream os = echange.getResponseBody();
                os.write(reponse.getBytes());
                os.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}