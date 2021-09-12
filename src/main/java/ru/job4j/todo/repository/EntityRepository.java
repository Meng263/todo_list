package ru.job4j.todo.repository;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.ItemQuery;

import java.util.List;

public interface EntityRepository<ENTITY> {
    ENTITY save(Item item);

    void delete(long id);

    List<ENTITY> getAll(ItemQuery query);

    ENTITY findById(long id);
}
