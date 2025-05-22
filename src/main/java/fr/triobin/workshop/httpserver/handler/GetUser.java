package fr.triobin.workshop.httpserver.handler;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.general.Operator;

public class GetUser implements HttpHandler {
    @Override
    public void handle(HttpExchange echange) {
        try {
            String query = echange.getRequestURI().getQuery();
            Map<String, String> queryParams = parseQueryParams(query);

            String username = queryParams.get("user");
            String password = queryParams.get("password");

            Boolean isAuthenticated = ValidateUserAuthentification.valideoupas(username, password);

            String reponse;

            if (isAuthenticated) {
                Operator operator = Memory.currentWorkshop.getOperator(username);
                if (operator != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                        // On renvoie la tâche au format JSON
                        reponse = objectMapper.writeValueAsString(operator);
                } else {
                    reponse = "Opérateur non trouvé";
                }
            } else {
                reponse = "Authentification échouée";
            }

            echange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            echange.sendResponseHeaders(200, reponse.getBytes().length);
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
}
