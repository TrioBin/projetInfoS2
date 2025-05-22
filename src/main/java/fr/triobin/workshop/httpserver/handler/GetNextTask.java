package fr.triobin.workshop.httpserver.handler;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.general.Operator;
import fr.triobin.workshop.general.Task;
import fr.triobin.workshop.general.Operator.OperatorStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetNextTask implements HttpHandler {
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
                    // On récupère la prochaine tâche de l'opérateur
                    Task nextTask = Memory.currentWorkshop.getNextGoal(operator.getSkills());
                    if (nextTask != null) {
                        operator.setCurrentTask(nextTask);
                        operator.modify(OperatorStatus.TRAVAILLANT);
                        ObjectMapper objectMapper = new ObjectMapper();
                        // On renvoie la tâche au format JSON
                        reponse = objectMapper.writeValueAsString(nextTask);
                    } else {
                        reponse = "{\"message\": \"Aucune tâche disponible pour l'opérateur\"}";
                    }
                } else {
                    reponse = "{\"message\": \"Opérateur non trouvé\"}";
                }
            } else {
                reponse = "{\"message\": \"Authentification échouée\"}";
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
