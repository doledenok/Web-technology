package ru.doledenok.webtech.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.doledenok.webtech.DAO.EmployeeDAO;
import ru.doledenok.webtech.DAO.PosHistoryDAO;
import ru.doledenok.webtech.DAO.PositionDAO;
import ru.doledenok.webtech.DAO.impl.EmployeeDAOImpl;
import ru.doledenok.webtech.DAO.impl.PosHistoryDAOImpl;
import ru.doledenok.webtech.DAO.impl.PositionDAOImpl;
import ru.doledenok.webtech.models.Employee;
import ru.doledenok.webtech.models.Position;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    @Autowired
    private final PosHistoryDAO posHistoryDAO = new PosHistoryDAOImpl();
    @Autowired
    private final PositionDAO positionDAO = new PositionDAOImpl();


    @GetMapping("/employees")
    public String employees(@RequestParam(name = "employeePosition", required = false) String positionName,
                            @RequestParam(name = "employeeName", required = false) String name,
                            @RequestParam(name = "employeeEducation", required = false) String education,
                            @RequestParam(name = "employeeAddress", required = false) String address,
                            @RequestParam(name = "employeeWorkExperience", required = false) String workExperience,
                            @RequestParam(name = "employeeStatus", required = false) String status,
                            @RequestParam(name = "employeeDateOfBirth", required = false) Date dateOfBirth,
                            Model model) {
        EmployeeDAO.Filter filter = EmployeeDAO.getFilterBuilder()
                .name(name)
                .address(address)
                .experience(workExperience)
                .education(education)
                .status(status)
                .build();
        List<Employee> employees = employeeDAO.getByFilter(filter);
        model.addAttribute("employees", employees);
        model.addAttribute("employeeService", employeeDAO);
        model.addAttribute("positions", positionDAO.getAllPositionNames());
        model.addAttribute("employeeStatuses", Arrays.asList("Работает", "Не работает"));
        return "employees";
    }

    @GetMapping("/employee")
    public String employee(@RequestParam(name = "employeeId") Long employeeId, Model model) {
        Employee employee = employeeDAO.getById(employeeId);

        if (employee == null) {
            model.addAttribute("error_msg", "В базе нет служащего с ID = " + employeeId);
            return "errorPage";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("employeeService", employeeDAO);
        model.addAttribute("posHistory", posHistoryDAO);
        return "employee";
    }

    @GetMapping("/editEmployee")
    public String editEmployee(@RequestParam(name = "employeeId", required = false) Long employeeId, Model model) {
        if (employeeId == null) {
            model.addAttribute("employee", new Employee());
            model.addAttribute("employeeService", employeeDAO);
            return "editEmployee";
        }

        Employee employee = employeeDAO.getById(employeeId);

        if (employee == null) {
            model.addAttribute("error_msg", "В базе нет служащего с ID = " + employeeId);
            return "errorPage";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("employeeService", employeeDAO);
        return "editEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@RequestParam(name = "employeeId", required = false) Long employeeId,
                                   @RequestParam(name = "employeePosition", required = false) String positionName,
                                   @RequestParam(name = "employeeName", required = false) String name,
                                   @RequestParam(name = "employeeEducation", required = false) String education,
                                   @RequestParam(name = "employeeAddress", required = false) String address,
                                   @RequestParam(name = "employeeWorkExperience", required = false) String workExperience,
                                   @RequestParam(name = "employeeStatus", required = false) String status,
                                   @RequestParam(name = "employeeDateOfBirth", required = false) Date dateOfBirth,
                                 Model model) {
        Employee employee;
        Position position = null;
        if (positionName != null) {
            position = positionDAO.getPositionByName(positionName);
            if (position == null) {
                model.addAttribute("error_msg", "В базе нет должности " + positionName);
                return "errorPage";
            }
        }
        if (employeeId == null) {
            employee = new Employee((Long) (Number) (employeeDAO.getAll().size() + 1L), position, name, education, address, workExperience, status, dateOfBirth);
            employeeDAO.save(employee);
        }
        else {
            employee = employeeDAO.getById(employeeId);
            if (employee == null) {
                model.addAttribute("error_msg", "В базе нет служащего с ID = " + employeeId);
                return "errorPage";
            }
            employee.setPosition(position);
            employee.setName(name);
            employee.setEducation(education);
            employee.setAddress(address);
            employee.setWorkExperience(workExperience);
            employee.setStatus(status);
            employee.setDateOfBirth(dateOfBirth);
            employeeDAO.update(employee);
        }
        return String.format("redirect:/employee?employeeId=%d", employee.getId());
    }

    @PostMapping("/removeEmployee")
    public String removeEmployee(@RequestParam(name = "employeeId") Long employeeId) {
        employeeDAO.deleteById(employeeId);
        return "redirect:/employees";
    }
}