package ru.job4j.schemas.cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "models")
public class ModelCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    public static ModelCar of(String name) {
        ModelCar role = new ModelCar();
        role.name = name;
        return role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelCar modelCar = (ModelCar) o;
        return id == modelCar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
