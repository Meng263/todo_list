package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

public class CategoryRepository extends UpsertRepository<Category> {
    private CategoryRepository() {
    }

    private static class CategoryRepositoryHolder {
        public static final CategoryRepository instance = new CategoryRepository();
    }

    public static CategoryRepository getInstance() {
        return CategoryRepositoryHolder.instance;
    }

    @Override
    public void delete(long id) {
        executeOnSession(session -> {
                    Category category = new Category();
                    category.setId(id);
                    session.delete(category);
                    return category;
                }
        );
    }

    @Override
    public Category findById(long id) {
        return executeOnSession(session -> session.get(Category.class, id));
    }

    @Override
    public List<Category> getAll(Map<String, Object> options) {
        return executeOnSession(session ->
                session.createQuery("from ru.job4j.todo.model.Category")
                        .list()
        );
    }

    @Override
    protected Category update(Category entity) {
        return executeOnSession(session -> {
                    session.update(entity);
                    return session.get(Category.class, entity.getId());
                }
        );
    }
}
