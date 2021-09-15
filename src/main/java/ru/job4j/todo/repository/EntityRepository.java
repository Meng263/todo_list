package ru.job4j.todo.repository;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.ItemQuery;

import java.util.List;

public interface EntityRepository<E> {
    E save(Item item);

    void delete(long id);

    List<E> getAll(ItemQuery query);

    E findById(long id);
}
