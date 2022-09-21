package ru.doledenok.webtech.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.doledenok.webtech.models.*;

import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class DAOTest {

    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private PositionDAO positionDAO;
    @Autowired
    private PosHistoryDAO posHistoryDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private ProjectRoleDAO projectRoleDAO;
    @Autowired
    private EmpRoleDAO empRoleDAO;
    @Autowired
    private PolicyDAO policyDAO;
    @Autowired
    private PaymentDAO paymentDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testEmptyEmployeeFilter() {
        EmployeeDAO.Filter filter = EmployeeDAO.getFilterBuilder().build();
        Collection<Employee> res = employeeDAO.getByFilter(filter);

        Set<String> namesExpected = new HashSet<>();
        namesExpected.add("Иванов Иван Иванович");
        namesExpected.add("Сергеев Сергей Сергеевич");
        namesExpected.add("Максимов Максим Максимович");

        assertEquals(namesExpected, res.stream().map(Employee::getName).collect(Collectors.toSet()));
    }

    @Test
    void testAllEmployeeFilter() {
        EmployeeDAO.Filter filter = EmployeeDAO.getFilterBuilder()
                .name("Максим")
                .address("Долгопрудный")
                .education("ФИВТ МФТИ")
                .status("в штате")
                .experience("2 года").build();
        Collection<Employee> res = employeeDAO.getByFilter(filter);

        Set<String> namesExpected = new HashSet<>();
        namesExpected.add("Максимов Максим Максимович");

        assertEquals(namesExpected, res.stream().map(Employee::getName).collect(Collectors.toSet()));
    }

    @Test
    void testGetKnownEducation() {
        Collection<String> res = employeeDAO.getKnownEducation();

        assertThat(res)
                .containsExactlyInAnyOrder("ВМК МГУ", "ФИВТ МФТИ");
    }

    @Test
    void testGetProjectsByEmpId() {
        Collection<Project> res = employeeDAO.getProjectsByEmpId(1L);

        Set<String> namesExpected = new HashSet<>();
        namesExpected.add("Проект1");
        namesExpected.add("Проект3");

        assertEquals(namesExpected, res.stream().map(Project::getName).collect(Collectors.toSet()));
    }

    @Test
    void testGetEmployeeRolesByProjectId() {
        Collection<EmpRole> res = empRoleDAO.getEmployeeRolesByProjectId(1L);

        Set<Long> idExpected = new HashSet<>();
        idExpected.add(1L);

        assertEquals(idExpected, res.stream().map(EmpRole::getId).collect(Collectors.toSet()));
    }

    @Test
    void testGetProjectRolesByProjectId() {
        Collection<ProjectRole> res = projectRoleDAO.getProjectRolesByProjectId(1L);

        Set<String> namesExpected = new HashSet<>();
        namesExpected.add("руководитель");
        namesExpected.add("тестировщик");
        namesExpected.add("разработчик");

        assertEquals(namesExpected, res.stream().map(ProjectRole::getName).collect(Collectors.toSet()));
    }

    @Test
    void testEmptyProjectFilter() {
        ProjectDAO.Filter filter = ProjectDAO.getFilterBuilder().build();
        Collection<Project> res = projectDAO.getByFilter(filter);

        Set<String> namesExpected = new HashSet<>();
        namesExpected.add("Проект1");
        namesExpected.add("Проект2");
        namesExpected.add("Проект3");
        namesExpected.add("Проект4");

        assertEquals(namesExpected, res.stream().map(Project::getName).collect(Collectors.toSet()));
    }

    @Test
    void testAllProjectFilter() {
        ProjectDAO.Filter filter = ProjectDAO.getFilterBuilder()
                .name("Проект1")
                .description("Описание проекта1").build();
        Collection<Project> res = projectDAO.getByFilter(filter);

        Set<String> namesExpected = new HashSet<>();
        namesExpected.add("Проект1");

        assertEquals(namesExpected, res.stream().map(Project::getName).collect(Collectors.toSet()));
    }

    @Test
    void testGetPositionByName() {
        Position res = positionDAO.getPositionByName("Лаборант");
        assertEquals("Лаборант", res.getName());
    }

    @Test
    void testGetAllPositionNames() {
        Collection<String> res = positionDAO.getAllPositionNames();

        assertThat(res)
                .containsExactlyInAnyOrder("Лаборант", "Старший лаборант", "Научный сотрудник", "Старший научный сотрудник", "Директор");
    }

    @Test
    void testAllPolicyFilter() {
        PolicyDAO.Filter filter = PolicyDAO.getFilterBuilder()
                .regularity("каждый месяц")
                .type("зарплата")
                .description("зарплата лаборанта").build();
        Collection<Policy> res = policyDAO.getByFilter(filter);

        Set<Long> idExpected = new HashSet<>();
        idExpected.add(1L);

        assertEquals(idExpected, res.stream().map(Policy::getId).collect(Collectors.toSet()));
    }

    @Test
    void testGetAllPaymentsByEmpId() {
        Collection<Payment> res = paymentDAO.getAllPaymentsByEmpId(1L);

        Set<Long> idExpected = new HashSet<>();
        idExpected.add(1L);

        assertEquals(idExpected, res.stream().map(Payment::getId).collect(Collectors.toSet()));
    }

    @Test
    void testGetPosHistoryByEmployeeId() {
        Collection<PosHistory> res = posHistoryDAO.getPosHistoryByEmployeeId(1L);

        Set<Long> idExpected = new HashSet<>();
        idExpected.add(1L);

        assertEquals(idExpected, res.stream().map(PosHistory::getId).collect(Collectors.toSet()));
    }

    @Test
    void testNullGetPosHistoryByEmployeeId() {
        Collection<PosHistory> res = posHistoryDAO.getPosHistoryByEmployeeId(10L);
        assertNull(res);
    }

    @Test
    void testUpdate() {
        Employee employee = employeeDAO.getById(3L);
        employee.status = "уволен";
        employeeDAO.update(employee);
        assertEquals("уволен", employeeDAO.getById(3L).status);
    }

    @Test
    void testDeleteById() {
        Long size = (Long) ((Number) (employeeDAO.getAll().size() + 1L));
        size = size - 1;
        employeeDAO.deleteById(size);
        assertEquals(size-1, employeeDAO.getAll().size());
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

        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project(
                1L,
                "Проект1",
                "Описание проекта1",
                Date.valueOf("2016-06-22"),
                Date.valueOf("2021-06-22")
        ));
        projectList.add(new Project(
                2L,
                "Проект2",
                "Описание проекта2",
                Date.valueOf("2020-01-10"),
                null
        ));
        projectList.add(new Project(
                3L,
                "Проект3",
                "Описание проекта3",
                Date.valueOf("2020-03-03"),
                Date.valueOf("2021-01-10")
        ));
        projectList.add(new Project(
                2L,
                "Проект4",
                "Описание проекта4",
                Date.valueOf("2021-12-10"),
                null
        ));
        projectDAO.saveCollection(projectList);

        List<ProjectRole> rolesList = new ArrayList<>();
        rolesList.add(new ProjectRole(
                null,
                projectList.get(0),
                "руководитель",
                1L,
                1L,
                "",
                "описание роли1 в проекте1"
        ));
        rolesList.add(new ProjectRole(
                null,
                projectList.get(0),
                "тестировщик",
                1L,
                1L,
                "",
                "описание роли2 в проекте1"
        ));
        rolesList.add(new ProjectRole(
                null,
                projectList.get(0),
                "разработчик",
                2L,
                1L,
                "",
                "описание роли3 в проекте1"
        ));
        rolesList.add(new ProjectRole(
                null,
                projectList.get(1),
                "руководитель",
                1L,
                1L,
                "",
                "описание роли1 в проекте2"
                ));
        rolesList.add(new ProjectRole(
                null,
                projectList.get(2),
                "руководитель",
                1L,
                1L,
                "",
                "описание роли1 в проекте3"
                ));
        rolesList.add(new ProjectRole(
                null,
                projectList.get(2),
                "тестировщик",
                1L,
                0L,
                "",
                "описание роли2 в проекте3"
        ));
        rolesList.add(new ProjectRole(
                null,
                projectList.get(2),
                "разработчик",
                5L,
                3L,
                "",
                "описание роли3 в проекте3"
        ));
        rolesList.add(new ProjectRole(
                null,
                projectList.get(2),
                "менеджер проекта",
                1L,
                1L,
                "",
                "описание роли4 в проекте3"
        ));
        projectRoleDAO.saveCollection(rolesList);

        List<Employee> employeesList = new ArrayList<>();
        employeesList.add(new Employee(
                null,
                positionsList.get(0),
                "Иванов Иван Иванович",
                "ВМК МГУ",
                "Москва",
                "1 год",
                "в штате",
                Date.valueOf("2000-06-22")
        ));
        employeesList.add(new Employee(
                null,
                positionsList.get(0),
                "Сергеев Сергей Сергеевич",
                "ВМК МГУ",
                "Москва",
                "5 лет",
                "в штате",
                Date.valueOf("1995-01-15")
        ));
        employeesList.add(new Employee(
                null,
                positionsList.get(0),
                "Максимов Максим Максимович",
                "ФИВТ МФТИ",
                "Долгопрудный",
                "2 года",
                "в штате",
                Date.valueOf("1999-09-10")
        ));
        employeeDAO.saveCollection(employeesList);

        List<PosHistory> posHistoryList = new ArrayList<>();
        posHistoryList.add(new PosHistory(
                null,
                positionsList.get(0),
                employeesList.get(0),
                Date.valueOf("2020-03-10"),
                null
        ));
        posHistoryList.add(new PosHistory(
                null,
                positionsList.get(1),
                employeesList.get(1),
                Date.valueOf("2019-03-10"),
                null
        ));
        posHistoryDAO.saveCollection(posHistoryList);

        List<EmpRole> empRoleList = new ArrayList<>();
        empRoleList.add(new EmpRole(
                null,
                employeesList.get(0),
                rolesList.get(0),
                Date.valueOf("2018-01-10"),
                null
        ));
        empRoleList.add(new EmpRole(
                null,
                employeesList.get(0),
                rolesList.get(4),
                Date.valueOf("2019-01-10"),
                Date.valueOf("2022-01-10")
        ));
        empRoleList.add(new EmpRole(
                null,
                employeesList.get(0),
                rolesList.get(7),
                Date.valueOf("2020-01-10"),
                null
        ));
        empRoleList.add(new EmpRole(
                null,
                employeesList.get(1),
                rolesList.get(2),
                Date.valueOf("2021-01-10"),
                null
        ));
        empRoleList.add(new EmpRole(
                null,
                employeesList.get(1),
                rolesList.get(7),
                Date.valueOf("2019-01-10"),
                Date.valueOf("2020-01-10")
        ));
        empRoleList.add(new EmpRole(
                null,
                employeesList.get(1),
                rolesList.get(3),
                Date.valueOf("2020-01-10"),
                null
        ));
        empRoleDAO.saveCollection(empRoleList);

        List<Policy> policyList = new ArrayList<>();
        policyList.add(new Policy(
                null,
                positionsList.get(0),
                null,
                15000L,
                "каждый месяц",
                "зарплата",
                "зарплата лаборанта"
        ));
        policyList.add(new Policy(
                null,
                positionsList.get(1),
                null,
                12000L,
                "каждый месяц",
                "зарплата",
                "зарплата старшего лаборанта"
        ));
        policyDAO.saveCollection(policyList);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment(
                null,
                employeesList.get(0),
                policyList.get(0),
                Date.valueOf("2022-03-10")
        ));
        paymentList.add(new Payment(
                null,
                employeesList.get(1),
                policyList.get(1),
                Date.valueOf("2022-03-10")
        ));
        paymentDAO.saveCollection(paymentList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("truncate position restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate employee restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate project restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate project_roles restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate employee_role restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate payment_policy restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate payment restart identity cascade;").executeUpdate();
            //session.createSQLQuery("ALTER SEQUENCE person_person_id_seq RESTART WITH 1;").executeUpdate();
            //session.createSQLQuery("ALTER SEQUENCE relation_relation_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
