package org.ifpb.app;

import com.sun.net.httpserver.HttpServer;
import org.ifpb.config.HibernateUtil;
import org.ifpb.controller.ConsultaHandlerController;
import org.ifpb.controller.MedicoHandlerController;
import org.ifpb.controller.PacienteHandlerController;
import org.ifpb.controller.handler.AbstractHandler;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        try {

            String envPort = System.getenv("SERVER_PORT");
            int port = (envPort != null && !envPort.isEmpty()) ? Integer.parseInt(envPort) : 8080;

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            AbstractHandler medicoHandler = new MedicoHandlerController();
            AbstractHandler pacienteHandler = new PacienteHandlerController();
            AbstractHandler consultaHandler = new ConsultaHandlerController();

            server.createContext("/api/medicos", medicoHandler);
            server.createContext("/api/pacientes", pacienteHandler);
            server.createContext("/api/consultas", consultaHandler);

            server.setExecutor(Executors.newFixedThreadPool(10));


            server.start();

            System.out.println("=========================================================");
            System.out.println("🚀 [SGCM] Servidor HTTP nativo iniciado com sucesso!");
            System.out.println("📡 Porta ativa: " + port);
            System.out.println("🔗 Rotas mapeadas:");
            System.out.println("   GET/POST/PUT/DELETE -> http://localhost:" + port + "/api/medicos");
            System.out.println("   GET/POST/PUT/DELETE -> http://localhost:" + port + "/api/pacientes");
            System.out.println("   GET/POST/DELETE     -> http://localhost:" + port + "/api/consultas");
            System.out.println("=========================================================");


            System.out.println("⏳ Conectando ao Banco de Dados (Hibernate)...");
            HibernateUtil.getFactory();
            System.out.println("✅ Banco de dados conectado e mapeado!");

        } catch (IOException e) {
            System.err.println("❌ Falha crítica ao iniciar o servidor HTTP: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}