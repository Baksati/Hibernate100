package org.example.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(org.example.model.Developer.class)
                    .addAnnotatedClass(org.example.model.Skill.class)
                    .addAnnotatedClass(org.example.model.Specialty.class)
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();

        } catch (Exception ex) {
            System.err.println("Ошибка при создании SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
