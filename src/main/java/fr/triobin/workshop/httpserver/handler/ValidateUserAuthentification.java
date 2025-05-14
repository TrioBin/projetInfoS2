package fr.triobin.workshop.httpserver.handler;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.general.Operator; // Updated to match the correct package for Operator

public class ValidateUserAuthentification implements HttpHandler {

    @Override
    public void handle(HttpExchange echange) {
        try {
            String query = echange.getRequestURI().getQuery();
            Map<String, String> queryParams = parseQueryParams(query);

            String username = queryParams.get("user");
            String password = queryParams.get("password");

            String reponse = valideoupas(username, password)
                    ? "Authentification reussie"
                    : "Authentification non reussie";

            echange.sendResponseHeaders(200, reponse.length());
            OutputStream os = echange.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Méthode pour parser les paramètres
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

    // Méthode principale d'authentification
    private Boolean valideoupas(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        if (Memory.currentWorkshop == null) {
            return false;
        }

        if (Memory.currentWorkshop.getOperators() == null) {
            return false;
        }

        for (Operator operator : Memory.currentWorkshop.getOperators()) {
            System.out.println("  - code = '" + operator.getCode() + "', password = '" + operator.getPassword() + "'");
            if (operator.getCode().trim().equals(username.trim()) &&
                operator.getPassword().trim().equals(password.trim())) {
                return true;
            }
        }

        return false;
    }
}
