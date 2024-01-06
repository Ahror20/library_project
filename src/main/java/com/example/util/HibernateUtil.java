package com.example.util;

import com.example.entity.BookEntity;
import com.example.entity.StudentBookEntity;
import com.example.entity.StudentEntity;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/library_db");
            configuration.setProperty("hibernate.connection.username", "library_user");
            configuration.setProperty("hibernate.connection.password", "123456");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");



            // Add mappings for your entity classes
            configuration.addAnnotatedClass(BookEntity.class);
            configuration.addAnnotatedClass(StudentEntity.class);
            configuration.addAnnotatedClass(StudentBookEntity.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }



}
