package org.ifpb.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    private static final EntityManagerFactory factory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {

            Map<String, String> properties = new HashMap<>();
            String dbUrl = System.getenv("DB_URL");

            if (dbUrl != null && !dbUrl.trim().isEmpty()) {

                properties.put("jakarta.persistence.jdbc.url", dbUrl);
                System.out.println("🔌 [Hibernate] Conectando ao banco via Docker (Variável de Ambiente).");
            } else {
                System.out.println("🔌 [Hibernate] Conectando ao banco via Localhost (Modo Dev).");
            }


            EntityManagerFactory emf = Persistence.createEntityManagerFactory("sgcm-pu", properties);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("🛑 [Sistema] Desligando... Fechando o EntityManagerFactory.");
                if (emf != null && emf.isOpen()) {
                    emf.close();
                }
            }));

            return emf;

        } catch (Exception e) {
            System.err.println("❌ Erro catastrófico ao iniciar o EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e); 
        }
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void closeFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}