package org.ifpb.controller;

import com.sun.net.httpserver.HttpExchange;
import org.ifpb.controller.handler.AbstractHandler;
import org.ifpb.dto.medico.MedicoRequestDTO;
import org.ifpb.service.MedicoService;
import org.ifpb.service.service_exceptions.medico_service_exception.MedicoExisteException;
import org.ifpb.service.service_exceptions.medico_service_exception.MedicoNaoEncontradoException;

import java.io.IOException;

public class MedicoHandlerController extends AbstractHandler {

    private final MedicoService medicoService = new MedicoService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        try {
            if ("GET".equals(method)) {
                Long id = extractIdFromPath(exchange);
                if (id != null) {
                    sendResponse(exchange, medicoService.buscarPorId(id), 200);
                } else {
                    sendResponse(exchange, medicoService.listarAtivos(), 200);
                }
            }
            else if ("POST".equals(method)) {
                MedicoRequestDTO dto = parseBody(exchange, MedicoRequestDTO.class);
                sendResponse(exchange, medicoService.save(dto), 201); // 201 Created
            }
            else if ("PUT".equals(method)) {
                Long id = extractIdFromPath(exchange);
                MedicoRequestDTO dto = parseBody(exchange, MedicoRequestDTO.class);
                sendResponse(exchange, medicoService.atualizar(id, dto), 200);
            }
            else if ("DELETE".equals(method)) {
                Long id = extractIdFromPath(exchange);
                medicoService.inativar(id);
                sendResponse(exchange, "{\"mensagem\": \"Médico inativado com sucesso.\"}", 200);
            }
            else {
                sendError(exchange, "Método não permitido", 405);
            }
        } catch (MedicoNaoEncontradoException e) {
            sendError(exchange, e.getMessage(), 404);
        } catch (MedicoExisteException | IllegalArgumentException e) {
            sendError(exchange, e.getMessage(), 400); // 400 Bad Request
        } catch (Exception e) {
            sendError(exchange, "Erro interno no servidor: " + e.getMessage(), 500);
        }
    }
}
