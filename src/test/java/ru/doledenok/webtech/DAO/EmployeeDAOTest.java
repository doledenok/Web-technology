package ru.doledenok.webtech.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.doledenok.webtech.models.Employee;
import ru.doledenok.webtech.models.Position;

import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class EmployeeDAOTest {

    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private PositionDAO positionDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testEmptyFilter() {
        EmployeeDAO.Filter filter = EmployeeDAO.getFilterBuilder().build();
        Collection<Employee> res = employeeDAO.getByFilter(filter);

        Set<String> namesExpected = new HashSet<>();
        namesExpected.add("Иванов Иван Иванович");
        namesExpected.add("Сергеев Сергей Сергеевич");

        assertEquals(namesExpected, res.stream().map(Employee::getName).collect(Collectors.toSet()));
    }

    @BeforeEach
    void beforeEach() {
        List<Position> positionsList = new ArrayList<>();
        positionsList.add(new Position(
                null,
                "Лаборант",
                "Описание должности \"лаборант\""
        ));
        positionsList.add(new Position(
                null,
                "Старший лаборант",
                "Описание должности \"старший лаборант\""
        ));
        positionsList.add(new Position(
                null,
                "Научный сотрудник",
                "Описание должности \"научный сотрудник\""
        ));
        positionsList.add(new Position(
                null,
                "Старший научный сотрудник",
                "Описание должности \"старший научный сотрудник\""
        ));
        positionsList.add(new Position(
                null,
                "Директор",
                "Описание должности \"директор\""
        ));
        positionDAO.saveCollection(positionsList);

        List<Employee> employeesList = new ArrayList<>();
        employeesList.add(new Employee(
                null,
                positionsList.get(0),
                "Иванов Иван Иванович",
                "ВМК МГУ",
                "Караганда",
                "5 лет",
                "в штате",
                Date.valueOf("2000-06-22")
        ));
        employeesList.add(new Employee(
                null,
                positionsList.get(0),
                "Сергеев Сергей Сергеевич",
                "ВМК МГУ",
                "Караганда",
                "5 лет",
                "в штате",
                Date.valueOf("2000-06-22")
        ));
        employeeDAO.saveCollection(employeesList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE position RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE employee RESTART IDENTITY CASCADE;").executeUpdate();
            //session.createSQLQuery("ALTER SEQUENCE person_person_id_seq RESTART WITH 1;").executeUpdate();
            //session.createSQLQuery("ALTER SEQUENCE relation_relation_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
