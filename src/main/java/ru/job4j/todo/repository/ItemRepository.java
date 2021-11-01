package ru.job4j.todo.repository;

import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Map;

public class ItemRepository extends UpsertRepository<Item> {
    private ItemRepository() {
    }

    private static class ItemRepositoryHolder {
        public static final ItemRepository instance = new ItemRepository();
    }

    public static ItemRepository getInstance() {
        return ItemRepositoryHolder.instance;
    }

    @Override
    protected final Item update(Item item) {
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
    public List<Item> getAll(Map<String, Object> options) {
        return executeOnSession(session ->
                session.createQuery("from ru.job4j.todo.model.Item where done = :done or done = false")
                        .setParameter("done", options.get("is_shows_done"))
                        .list()
        );
    }

    @Override
    public Item findById(long id) {
        return executeOnSession(session -> session.get(Item.class, id));
    }
}
