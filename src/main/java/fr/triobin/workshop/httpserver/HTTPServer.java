package fr.triobin.workshop.httpserver;

import com.sun.net.httpserver.HttpServer;

import fr.triobin.workshop.httpserver.handler.FinishTask;
import fr.triobin.workshop.httpserver.handler.GetNextTask;
import fr.triobin.workshop.httpserver.handler.GetUser;
import fr.triobin.workshop.httpserver.handler.GetUserStatus;
import fr.triobin.workshop.httpserver.handler.ValidateUserAuthentification;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HTTPServer {

    public static HttpServer serveur;

    public static void start() {
        try {
            HttpServer serveur = HttpServer.create(new InetSocketAddress(8000), 0);
            serveur.createContext("/", new MonHandler());
            serveur.createContext("/userAuthentification", new ValidateUserAuthentification());
            serveur.createContext("/userStatus", new GetUserStatus());
            serveur.createContext("/getNextTask", new GetNextTask());
            serveur.createContext("/user", new GetUser());
            serveur.createContext("/finishTask", new FinishTask());
            serveur.setExecutor(null); // Default executor
            serveur.start();
            HTTPServer.serveur = serveur;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            HTTPServer.serveur.stop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MonHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange echange) {
            try {
                // Lire le fichier HTML
                StringBuilder contenuHTML = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                getClass().getClassLoader()
                                        .getResourceAsStream("fr/triobin/workshop/index.html"),
                                "UTF-8"))) {
                    String ligne;
                    while ((ligne = br.readLine()) != null) {
                        contenuHTML.append(ligne).append("\n");
                    }
                }

                // Envoyer le contenu HTML en réponse
                String reponse = contenuHTML.toString();
                byte[] reponseBytes = reponse.getBytes(StandardCharsets.UTF_8);
                echange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                echange.sendResponseHeaders(200, reponseBytes.length);
                try (OutputStream os = echange.getResponseBody()) {
                    os.write(reponseBytes);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                String erreur = "Erreur lors de la lecture du fichier HTML.";
                try {
                    echange.sendResponseHeaders(500, erreur.length());
                    echange.getResponseBody().write(erreur.getBytes());
                    echange.getResponseBody().close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
