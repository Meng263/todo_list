package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.ItemQuery;

import java.util.List;
import java.util.stream.Collectors;

public class ItemRepository implements EntityRepository<Item> {
    private final SessionFactory sessionFactory = HibernateSupplier.getSessionFactory();

    @Override
    public Item save(Item item) {
        if (item.getId() == 0) {
            return create(item);
        } else {
            return update(item);
        }
    }

    private Item create(Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    private Item update(Item item) {
        Item result;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        result = session.get(Item.class, item.getId());
        session.close();
        return result;
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Item> getAll(ItemQuery query) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item where done = :done or done = false")
                .setParameter("done", query.isShowsDone())
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
