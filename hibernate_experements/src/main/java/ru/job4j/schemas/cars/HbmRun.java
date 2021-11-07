package ru.job4j.schemas.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.schemas.cars.model.BrandCar;
import ru.job4j.schemas.cars.model.ModelCar;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            BrandCar bmw = BrandCar.of("bmw");

            session.save(bmw);

            List<ModelCar> models = Stream
                    .of("120", "320", "540", "X5", "740")
                    .map(ModelCar::of)
                    .collect(Collectors.toList());

            models.forEach(bmw::addModel);
            models.forEach(session::save);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
