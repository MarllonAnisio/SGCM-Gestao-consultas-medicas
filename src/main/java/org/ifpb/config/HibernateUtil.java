package org.ifpb.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("sgcm-pu");

    public static  EntityManagerFactory getFactory() {
        return factory;
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
    public static void closeFactory() {
        factory.close();
    }
}
