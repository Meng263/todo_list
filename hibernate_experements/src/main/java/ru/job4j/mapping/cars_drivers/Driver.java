package ru.job4j.mapping.cars_drivers;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(schema = "cars", name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "drivers")
    private Set<Car> cars = new HashSet<>();
}
