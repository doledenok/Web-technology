package ru.doledenok.webtech.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.doledenok.webtech.models.Employee;
import ru.doledenok.webtech.models.Payment;
import ru.doledenok.webtech.models.Position;
import ru.doledenok.webtech.models.Project;

import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class TestDAOTest {

    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testEmptyFilter() {
        }

    @BeforeEach
    void beforeEach() {
        List<Project> projectsList = new ArrayList<>();
        projectsList.add(new Project(
                5L,
                "Лаборант",
                "Описание должности \"лаборант\"",
                Date.valueOf("2000-06-22"),
                Date.valueOf("2000-06-22")
        ));
        projectDAO.saveCollection(projectsList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE project RESTART IDENTITY CASCADE;").executeUpdate();
            //session.createSQLQuery("TRUNCATE employee RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE project_proj_id_seq RESTART WITH 1;").executeUpdate();
            //session.createSQLQuery("ALTER SEQUENCE relation_relation_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
