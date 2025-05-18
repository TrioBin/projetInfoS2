package fr.triobin.workshop.httpserver;

import com.sun.net.httpserver.HttpServer;

import fr.triobin.workshop.FileManager;
import fr.triobin.workshop.Memory;
import fr.triobin.workshop.httpserver.handler.FinishTask;
import fr.triobin.workshop.httpserver.handler.GetNextTask;
import fr.triobin.workshop.httpserver.handler.GetUser;
import fr.triobin.workshop.httpserver.handler.GetUserStatus;
import fr.triobin.workshop.httpserver.handler.ValidateUserAuthentification;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class HTTPServer {

    public static void main(String[] args) {
        Memory.workshops = FileManager.loadFile();
        Memory.saveFile();
        Memory.currentWorkshop = Memory.workshops.get(0);
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
            System.out.println("Serveur HTTP démarré sur http://localhost:8000");
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
                        new FileReader("src/main/java/fr/triobin/workshop/httpserver/index.html"))) {
                    String ligne;
                    while ((ligne = br.readLine()) != null) {
                        contenuHTML.append(ligne).append("\n");
                    }
                }

                // Envoyer le contenu HTML en réponse
                String reponse = contenuHTML.toString();
                echange.sendResponseHeaders(200, reponse.getBytes().length);
                OutputStream os = echange.getResponseBody();
                os.write(reponse.getBytes());
                os.close();

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
