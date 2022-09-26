package ru.doledenok.webtech.web;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.doledenok.webtech.DAO.*;
import ru.doledenok.webtech.models.*;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class WebTest {
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
    private final String rootTitle = "Главная страница";
    private final String employeesTitle = "Служащие";
    private final String policiesTitle = "Платёжные политики";
    private final String projectsTitle = "Список проектов";
    private final String projectTitle = "Информация о проекте";
    private final String editEmployeeTitle = "Редактировать информацию о служащем";
    private final String editPolicyTitle = "Редактировать платёжную политику";
    private final String editProjectTitle = "Редактировать проект";
    private final String employeeTitle = "Информация о служащем";
    private final String policyTitle = "Информация о политике выплат";

    @Test
    void MainPage() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        assertEquals(rootTitle, driver.getTitle());
        driver.quit();
    }

    @Test
    void HeaderTest() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");

        WebElement employeesButton = driver.findElement(By.id("employeesListLink"));
        employeesButton.click();
        wait(driver);
        assertEquals(employeesTitle, driver.getTitle());

        WebElement projectsButton = driver.findElement(By.id("projectsListLink"));
        projectsButton.click();
        wait(driver);
        assertEquals(projectsTitle, driver.getTitle());

        WebElement policiesButton = driver.findElement(By.id("policiesListLink"));
        policiesButton.click();
        wait(driver);
        assertEquals(policiesTitle, driver.getTitle());

        WebElement rootButton = driver.findElement(By.id("rootLink"));
        rootButton.click();
        wait(driver);
        assertEquals(rootTitle, driver.getTitle());

        driver.quit();
    }

    @Test
    void addEmployee() {
        ChromeDriver driver = new ChromeDriver();
        //driver.manage().window().setSize(new Dimension(1024,1024));
        driver.get("http://localhost:8080/");
        assertEquals(rootTitle, driver.getTitle());
        WebElement addEmployee = driver.findElement(By.id("addEmployeeButton"));
        addEmployee.click();
        wait(driver);
        assertEquals(editEmployeeTitle, driver.getTitle());

        driver.findElement(By.id("employeeName")).sendKeys("Тестовый Тест Тестович");
        driver.findElement(By.id("employeePosition")).sendKeys("Директор");
        driver.findElement(By.id("employeeEducation")).sendKeys("ФКН ВШЭ");
        driver.findElement(By.id("employeeAddress")).sendKeys("Москва");
        driver.findElement(By.id("employeeWorkExperience")).sendKeys("20 лет");
        driver.findElement(By.id("employeeStatus")).sendKeys("Работает");
        driver.findElement(By.id("employeeDateOfBirth")).sendKeys("1970-10-30");
        driver.findElement(By.id("submitButton")).click();
        wait(driver);

        assertEquals(employeeTitle, driver.getTitle());
        WebElement employeeInfo = driver.findElement(By.id("employeeInfo"));
        List<WebElement> cells = employeeInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Текущая должность: Директор");
        assertEquals(cells.get(1).getText(), "Образование: ФКН ВШЭ");
        assertEquals(cells.get(2).getText(), "Адрес: Москва");
        assertEquals(cells.get(3).getText(), "Стаж: 20 лет");
        assertEquals(cells.get(4).getText(), "Статус: Работает");
        assertEquals(cells.get(5).getText(), "Дата рождения: 1970-10-30");
        assertEquals(cells.get(6).getText(), "История должностей:  нет");
        assertEquals(cells.get(7).getText(), "История проектов:  нет");
        assertEquals(cells.get(8).getText(), "История выплат:  нет");

        driver.quit();
    }

    @Test
    void addProject() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        assertEquals(rootTitle, driver.getTitle());
        WebElement addProject = driver.findElement(By.id("addProjectButton"));
        addProject.click();
        wait(driver);
        assertEquals(editProjectTitle, driver.getTitle());

        driver.findElement(By.id("projectName")).sendKeys("Тестовый проект");
        driver.findElement(By.id("projectDescription")).sendKeys("Описание тестового проекта");
        driver.findElement(By.id("projectStartDate")).sendKeys("2022-09-22");
        driver.findElement(By.id("projectEndDate")).sendKeys("2023-09-22");
        driver.findElement(By.id("submitButton")).click();
        wait(driver);

        assertEquals(projectTitle, driver.getTitle());
        WebElement projectInfo = driver.findElement(By.id("projectInfo"));
        List<WebElement> cells = projectInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Название: Тестовый проект");
        assertEquals(cells.get(1).getText(), "Дата начала: 2022-09-22");
        assertEquals(cells.get(2).getText(), "Дата окончания: 2023-09-22");
        assertEquals(cells.get(3).getText(), "Описание: Описание тестового проекта");
        assertEquals(cells.get(4).getText(), "Роли проекта:  нет");

        driver.quit();
    }

    @Test
    void addPolicy() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        assertEquals(rootTitle, driver.getTitle());
        WebElement addPolicy = driver.findElement(By.id("addPolicyButton"));
        addPolicy.click();
        wait(driver);
        assertEquals(editPolicyTitle, driver.getTitle());

        driver.findElement(By.id("policyPositionName")).sendKeys("Директор");
        driver.findElement(By.id("policySum")).sendKeys("500000");
        driver.findElement(By.id("policyRegularity")).sendKeys("каждый месяц");
        driver.findElement(By.id("submitButton")).click();
        wait(driver);

        assertEquals(policyTitle, driver.getTitle());
        WebElement policyInfo = driver.findElement(By.id("policyInfo"));
        List<WebElement> cells = policyInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Должность: Директор");
        assertEquals(cells.get(1).getText(), "Сумма: 500000");
        assertEquals(cells.get(2).getText(), "Регулярность: каждый месяц");
        assertEquals(cells.get(3).getText(), "Тип:");
        assertEquals(cells.get(4).getText(), "Описание:");

        driver.quit();
    }

    @Test
    void getEmployeeProjects() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/employee?employeeId=1");
        assertEquals(employeeTitle, driver.getTitle());

        WebElement employeeInfo = driver.findElement(By.id("employeeInfo"));
        List<WebElement> cells = employeeInfo.findElements(By.tagName("p"));
        cells.get(7).findElement(By.tagName("a")).click();

        assertEquals(projectTitle, driver.getTitle());
        WebElement projectInfo = driver.findElement(By.id("projectInfo"));
        cells = projectInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Название: Проект1");
    }

    private void wait(ChromeDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(1)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    @BeforeEach
    void fillDatabase() {
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

    @AfterEach
    void cleanDatabase() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("truncate position restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate employee restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate project restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate project_roles restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate employee_role restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate payment_policy restart identity cascade;").executeUpdate();
            session.createSQLQuery("truncate payment restart identity cascade;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
