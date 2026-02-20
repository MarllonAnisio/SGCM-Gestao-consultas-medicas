package org.ifpb.controller.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class AbstractHandler implements HttpHandler {

    protected final Gson gson = new Gson();

    // Método mágico para enviar as respostas em JSON
    protected void sendResponse(HttpExchange exchange, Object responseObj, int statusCode) throws IOException {
        String jsonResponse = gson.toJson(responseObj);
        byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    // Método mágico para enviar mensagens de erro padronizadas
    protected void sendError(HttpExchange exchange, String mensagem, int statusCode) throws IOException {
        String jsonError = "{\"erro\": \"" + mensagem + "\"}";
        byte[] responseBytes = jsonError.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    // Lê o corpo da requisição (JSON do Front) e converte para o DTO
    protected <T> T parseBody(HttpExchange exchange, Class<T> clazz) {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        return gson.fromJson(reader, clazz);
    }

    // Pega o ID da URL (ex: /api/medicos/5 -> retorna 5)
    protected Long extractIdFromPath(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        try {
            return Long.parseLong(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}