package fr.triobin.workshop.httpserver.handler;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;

import fr.triobin.workshop.Memory;

import com.sun.net.httpserver.HttpExchange;

public class ValidateUserAuthentification implements HttpHandler {
    @Override
    public void handle(HttpExchange echange) {
        try {// Récupérer les paramètres de la requête
            String query = echange.getRequestURI().getQuery();
            Map<String, String> queryParams = parseQueryParams(query);

            // Exemple : Afficher les paramètres dans la console
            queryParams.forEach((key, value) -> System.out.println(key + " = " + value));

            Memory.currentWorkshop.getOperators()
                    .forEach(operator -> System.out.println(operator.getCode() + " " + operator.getPassword()));

            String reponse = "Authentification";
            echange.sendResponseHeaders(200, reponse.length());
            OutputStream os = echange.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Méthode utilitaire pour parser les paramètres de la requête
    private Map<String, String> parseQueryParams(String query) throws UnsupportedEncodingException {
        Map<String, String> queryParams = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }
}
