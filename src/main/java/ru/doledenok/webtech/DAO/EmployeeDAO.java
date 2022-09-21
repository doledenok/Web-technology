package ru.doledenok.webtech.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.doledenok.webtech.models.Employee;
import ru.doledenok.webtech.models.Project;

import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee, Long> {

    List<Employee> getAllEmployeesByName(String Name);
    Employee getSingleEmployeeByName(String employeeName);
    List<Employee> getByFilter(Filter filter);

    List<Project> getProjectsByEmpId(Long empId);

    List<String> getKnownEducation();

    @Builder
    @Getter
    class Filter {
        private String name;
        private String address;
        private String experience;
        private String education;
        private String status;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
