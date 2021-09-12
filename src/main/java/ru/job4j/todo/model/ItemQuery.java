package ru.job4j.todo.model;

public class ItemQuery {
    private final boolean isShowsDone;

    public ItemQuery(boolean isShowsDone) {
        this.isShowsDone = isShowsDone;
    }

    public boolean isShowsDone() {
        return isShowsDone;
    }
}
