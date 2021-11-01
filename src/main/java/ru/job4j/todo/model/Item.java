package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item implements WithId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private boolean done;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String decs) {
        this.description = decs;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date time) {
        this.created = time;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && done == item.done && Objects.equals(description, item.description) && Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done);
    }
}