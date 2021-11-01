package ru.job4j.todo.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
    private HibernateUtility() {
    }

    public static SessionFactory factory;

    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            factory = new Configuration().configure("hibernate.cfg.xml").
                    buildSessionFactory();
        }
        return factory;
    }
}
