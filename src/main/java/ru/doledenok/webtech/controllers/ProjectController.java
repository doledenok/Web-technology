package ru.doledenok.webtech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.doledenok.webtech.DAO.impl.ProjectDAOImpl;
import ru.doledenok.webtech.DAO.impl.ProjectRoleDAOImpl;
import ru.doledenok.webtech.models.Project;


import java.sql.Date;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private final ProjectRoleDAOImpl projectRoleDAO = new ProjectRoleDAOImpl();

    @Autowired
    private final ProjectDAOImpl projectDAO = new ProjectDAOImpl();

    @GetMapping("/projects")
    public String projectsListPage(Model model) {
        List<Project> projects = (List<Project>) projectDAO.getAll();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/project")
    public String projectPage(@RequestParam(name = "projectId") Long projectId, Model model) {
        Project project = projectDAO.getById(projectId);

        if (project == null) {
            model.addAttribute("error_msg", "В базе нет проекта с ID = " + projectId);
            return "errorPage";
        }

        model.addAttribute("project", project);
        model.addAttribute("projectService", projectDAO);
        model.addAttribute("projectRoleService", projectRoleDAO);
        return "project";
    }

    @PostMapping("/saveProject")
    public String saveProjectPage(@RequestParam(name = "projectId", required = false) Long projectId,
                                  @RequestParam(name = "projectName") String projectName,
                                  @RequestParam(name = "projectDescription", required = false) String description,
                                  @RequestParam(name = "projectStartDate", required = false) Date startDate,
                                  @RequestParam(name = "projectEndDate", required = false) Date endDate,
                                  Model model) {

        Project project;
        if (projectId == null) {
            // создаём новый проект
            project = new Project((Long) (Number) (projectDAO.getAll().size() + 1L), projectName, description, startDate, endDate);
            projectDAO.save(project);
        }
        else {
            project = projectDAO.getById(projectId);
            if (project == null) {
                model.addAttribute("error_msg", "В базе нет проекта с ID = " + projectId);
                return "errorPage";
            }
            project.setName(projectName);
            project.setDescription(description);
            project.setStart(startDate);
            project.setEnd(endDate);
            projectDAO.update(project);
        }
        return String.format("redirect:/project?projectId=%d", project.getId());
    }

    @GetMapping("/editProject")
    public String editProjectPage(@RequestParam(name = "projectId", required = false) Long projectId, Model model) {
        if (projectId == null) {
            model.addAttribute("project", new Project());
            return "editProject";
        }

        Project project = projectDAO.getById(projectId);

        if (project == null) {
            model.addAttribute("error_msg", "В базе нет проекта с ID = " + projectId);
            return "errorPage";
        }

        model.addAttribute("project", project);
        return "editProject";
    }

    @PostMapping("/removeProject")
    public String removeProjectPage(@RequestParam(name = "projectId") Long projectId) {
        projectDAO.deleteById(projectId);
        return "redirect:/projects";
    }
}
