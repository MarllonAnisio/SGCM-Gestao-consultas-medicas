package org.ifpb.app;

import com.sun.net.httpserver.HttpServer;
import org.ifpb.dao.ConsultaDAO;
import org.ifpb.dao.MedicoDAO;
import org.ifpb.dao.PacienteDAO;
import org.ifpb.dao.interfaces.IConsultaDAO;
import org.ifpb.dao.interfaces.IMedicoDAO;
import org.ifpb.dao.interfaces.IPacienteDAO;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


public class Main {

    public static void main(String[] args) throws IOException {


        System.out.println("🏗️ Construindo dependências...");

        IConsultaDAO consultaDAO = new ConsultaDAO();
        IMedicoDAO medicoDAO = new MedicoDAO();
        IPacienteDAO pacienteDAO = new PacienteDAO();

        int porta = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);


        server.setExecutor(Executors.newFixedThreadPool(10));

        try {
            // Pega uma conexão e fecha imediatamente. O ato de pegar faz a mágica.
            org.ifpb.config.HibernateUtil.getEntityManager().close();
            System.out.println("✅ Tabelas validadas com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar no banco: " + e.getMessage());
            e.printStackTrace(); // Importante para ver se é erro de senha/conexão
        }

        server.start();

        System.out.println("---------------------------------------------");
        System.out.println("🏥 SGCM Backend Java Puro rodando!");
        System.out.println("👉 API Disponível em: http://localhost:" + porta + "/api/consultas");
        System.out.println("⚙️ Modo: Arquitetura em Camadas (Controller > Service > DAO)");
        System.out.println("---------------------------------------------");
    }
}
