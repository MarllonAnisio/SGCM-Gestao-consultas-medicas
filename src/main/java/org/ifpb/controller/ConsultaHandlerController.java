package org.ifpb.controller;

import com.sun.net.httpserver.HttpExchange;
import org.ifpb.controller.handler.AbstractHandler;
import org.ifpb.dto.consulta.ConsultaRequestDTO;
import org.ifpb.service.ConsultaService;
import org.ifpb.service.service_exceptions.consulta_service_exception.ConsultaJaCanceladaException;
import org.ifpb.service.service_exceptions.consulta_service_exception.ConsultaJaRealizadaException;
import org.ifpb.service.service_exceptions.consulta_service_exception.ConsultaNaoEncontradaException;
import org.ifpb.service.service_exceptions.medico_service_exception.MedicoNaoEncontradoException;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteNaoEncontradoException;

import java.io.IOException;

public class ConsultaHandlerController extends AbstractHandler {

    private final ConsultaService consultaService = new ConsultaService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        try {
            if ("GET".equals(method)) {
                Long id = extractIdFromPath(exchange);
                if (id != null) {
                    sendResponse(exchange, consultaService.buscarPorId(id), 200);
                } else {
                    sendResponse(exchange, consultaService.listarTodas(), 200);
                }
            }
            else if ("POST".equals(method)) {
                ConsultaRequestDTO dto = parseBody(exchange, ConsultaRequestDTO.class);
                sendResponse(exchange, consultaService.agendarConsulta(dto), 201);
            }
            else if ("DELETE".equals(method)) {
                Long id = extractIdFromPath(exchange);
                sendResponse(exchange, consultaService.cancelarConsulta(id), 200);
            }
            else {
                sendError(exchange, "Método não permitido", 405);
            }
        } catch (ConsultaNaoEncontradaException | MedicoNaoEncontradoException | PacienteNaoEncontradoException e) {
            sendError(exchange, e.getMessage(), 404);

            // 1. Bloco De erros de regra de negocios (Status 400)
        } catch (ConsultaJaCanceladaException | ConsultaJaRealizadaException e) {
            sendError(exchange, e.getMessage(), 400);

            // 2. rapaz esse bloco é separado para o Canhão de Segurança  (Status 500)
        } catch (RuntimeException e) {
            sendError(exchange, "Erro de negócio genérico: " + e.getMessage(), 400);

        } catch (Exception e) {
            sendError(exchange, "Erro interno no servidor", 500);
        }
    }
}
