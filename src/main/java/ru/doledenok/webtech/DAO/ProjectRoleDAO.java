package ru.doledenok.webtech.DAO;

import ru.doledenok.webtech.models.PosHistory;
import ru.doledenok.webtech.models.ProjectRole;

import java.util.List;

public interface ProjectRoleDAO extends GenericDAO<ProjectRole, Long> {

    List<ProjectRole> getProjectRolesByProjectId(Long empId);

}
