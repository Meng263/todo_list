package ru.job4j.todo.repository;

import ru.job4j.todo.model.WithId;

import java.util.List;
import java.util.Map;

public interface EntityRepository<E extends WithId> {
    E save(E entity);

    void delete(long id);

    List<E> getAll(Map<String, Object> options);

    List<E> getAll();

    E findById(long id);
}
