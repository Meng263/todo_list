package ru.job4j.todo.migration;

import org.flywaydb.core.Flyway;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.job4j.todo.repository.ItemRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlywayMigration {
    private static final FlywayMigration instance = new FlywayMigration();

    private FlywayMigration() {
    }

    public static FlywayMigration getInstance() {
        return instance;
    }

    public void migrate() {
        try {
            Map<String, String> props = parseProperties();

            Flyway flyway = Flyway.configure()
                    .dataSource(
                            props.get("hibernate.connection.url"),
                            props.get("hibernate.connection.username"),
                            props.get("hibernate.connection.password"))
                    .schemas("todo")
                    .locations("classpath:/db/migration")
                    .load();
            flyway.migrate();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseProperties() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        File xml = new File(ItemRepository.class.getResource("/hibernate.cfg.xml").getFile());
        NodeList nodeList = documentBuilder.parse(xml).getElementsByTagName("property");
        Map<String, String> props = IntStream
                .range(0, nodeList.getLength() - 1)
                .boxed()
                .collect(
                        Collectors.toMap(integer -> nodeList.item(integer).getAttributes().item(0).getTextContent(),
                                integer -> nodeList.item(integer).getTextContent())
                );
        return props;
    }
}
