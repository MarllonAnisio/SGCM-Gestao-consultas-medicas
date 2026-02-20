package org.ifpb.controller;

import com.sun.net.httpserver.HttpExchange;
import org.ifpb.controller.handler.AbstractHandler;
import org.ifpb.dto.paciente.PacienteRequestDTO;
import org.ifpb.service.PacienteService;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteNaoEncontradoException;

import java.io.IOException;

public class PacienteHandlerController extends AbstractHandler {

    private final PacienteService pacienteService = new PacienteService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        try {
            if ("GET".equals(method)) {
                Long id = extractIdFromPath(exchange);
                if (id != null) {
                    sendResponse(exchange, pacienteService.buscarPorId(id), 200);
                } else {
                    sendResponse(exchange, pacienteService.listarAtivos(), 200);
                }
            }
            else if ("POST".equals(method)) {
                PacienteRequestDTO dto = parseBody(exchange, PacienteRequestDTO.class);
                sendResponse(exchange, pacienteService.save(dto), 201);
            }
            else if ("PUT".equals(method)) {
                Long id = extractIdFromPath(exchange);
                PacienteRequestDTO dto = parseBody(exchange, PacienteRequestDTO.class);
                sendResponse(exchange, pacienteService.atualizar(id, dto), 200);
            }
            else if ("DELETE".equals(method)) {
                Long id = extractIdFromPath(exchange);
                pacienteService.inativar(id);
                sendResponse(exchange, "{\"mensagem\": \"Paciente inativado com sucesso.\"}", 200);
            }
            else {
                sendError(exchange, "Método não permitido", 405);
            }
        } catch (PacienteNaoEncontradoException e) {
            sendError(exchange, e.getMessage(), 404);
        } catch (RuntimeException e) {
            // Regras de negócio como CPF duplicado
            sendError(exchange, e.getMessage(), 400);
        } catch (Exception e) {
            sendError(exchange, "Erro interno no servidor", 500);
        }
    }
}