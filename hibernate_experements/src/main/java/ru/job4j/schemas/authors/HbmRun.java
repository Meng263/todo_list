package ru.job4j.schemas.authors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.schemas.authors.model.Author;
import ru.job4j.schemas.authors.model.Book;


public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_books.cfg.xml").build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Author bauer = Author.of("Christian Bauer");
            Author king = Author.of("Gavin King");
            Author walls = Author.of("Craig Walls");

            Book jpa = Book.of("Java persistence API");
            Book hibernateInAction = Book.of("Hibernate in Action");
            Book springInAction = Book.of("Spring in Action");

            jpa.addAuthor(bauer);
            jpa.addAuthor(king);

            hibernateInAction.addAuthor(king);
            springInAction.addAuthor(walls);

            session.persist(jpa);
            session.persist(hibernateInAction);
            session.persist(springInAction);

            springInAction.removeAuthor(walls);
            session.delete(walls);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
