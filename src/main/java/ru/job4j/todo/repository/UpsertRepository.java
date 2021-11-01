package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.job4j.todo.hibernate.HibernateUtility;
import ru.job4j.todo.model.WithId;

import java.util.function.Function;

public abstract class UpsertRepository<E extends WithId> implements EntityRepository<E> {
    @Override
    public final E save(E entity) {
        if (entity.getId() == 0) {
            return create(entity);
        } else {
            return update(entity);
        }
    }

    protected abstract E update(E entity);

    protected abstract E create(E entity);

    protected final <T> T executeOnSession(final Function<Session, T> command) {
        final Session session = HibernateUtility.getSessionFactory().openSession();
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
