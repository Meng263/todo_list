package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.ItemQuery;

import java.util.List;
import java.util.function.Function;

public class ItemRepository implements EntityRepository<Item> {
    private ItemRepository() {
    }

    private static class ItemRepositoryHolder {
        public static final ItemRepository instance = new ItemRepository();
    }

    public static ItemRepository getInstance() {
        return ItemRepositoryHolder.instance;
    }

    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    @Override
    public Item save(Item item) {
        if (item.getId() == 0) {
            return create(item);
        } else {
            return update(item);
        }
    }

    private Item create(Item item) {
        executeOnSession(session -> session.save(item));
        return item;
    }

    private Item update(Item item) {
        return executeOnSession(session -> {
                    session.update(item);
                    return session.get(Item.class, item.getId());
                }
        );
    }

    @Override
    public void delete(long id) {
        executeOnSession(session -> {
                    Item item = new Item();
                    item.setId(id);
                    session.delete(item);
                    return item;
                }
        );
    }

    @Override
    public List<Item> getAll(ItemQuery query) {
        return executeOnSession(session ->
                session.createQuery("from ru.job4j.todo.model.Item where done = :done or done = false")
                        .setParameter("done", query.isShowsDone())
                        .list()
        );
    }

    @Override
    public Item findById(long id) {
        return executeOnSession(session -> session.get(Item.class, id));
    }

    private <T> T executeOnSession(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
