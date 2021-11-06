package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Map;

public class UserRepository extends UpsertRepository<User> {
    private UserRepository() {
    }

    private static class UserRepositoryHolder {
        public static final UserRepository instance = new UserRepository();
    }

    public static UserRepository getInstance() {
        return UserRepositoryHolder.instance;
    }

    @Override
    protected User update(User entity) {
        return executeOnSession(session -> {
                    session.update(entity);
                    return session.get(User.class, entity.getId());
                }
        );
    }

    @Override
    public void delete(long id) {
        executeOnSession(session -> {
                    User user = new User();
                    user.setId(id);
                    session.delete(user);
                    return user;
                }
        );
    }

    @Override
    public List<User> getAll(Map<String, Object> query) {
        return executeOnSession(session ->
                session.createQuery("from ru.job4j.todo.model.User")
                        .list()
        );
    }

    public User findUserByEmail(String email) {
        return executeOnSession(session ->
                session.createQuery("from ru.job4j.todo.model.User where email=:email", User.class)
                        .setParameter("email", email)
                        .list().stream()
                        .findFirst()
                        .orElse(null)
        );
    }

    @Override
    public User findById(long id) {
        return executeOnSession(session -> session.get(User.class, id));
    }
}
