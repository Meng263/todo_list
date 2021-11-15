package ru.job4j.schemas.cars.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brands")
public class BrandCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelCar> models = new ArrayList<>();

    public static BrandCar of(String name) {
        BrandCar role = new BrandCar();
        role.name = name;
        return role;
    }

    public boolean addModel(ModelCar model) {
        return models.add(model);
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

    public List<ModelCar> getModels() {
        return models;
    }

    public void setModels(List<ModelCar> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandCar brandCar = (BrandCar) o;
        return id == brandCar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
