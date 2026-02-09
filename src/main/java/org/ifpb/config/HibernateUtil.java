package org.ifpb.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("sgcm-pu");

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
