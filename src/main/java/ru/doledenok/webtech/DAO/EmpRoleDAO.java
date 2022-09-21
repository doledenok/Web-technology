package ru.doledenok.webtech.DAO;

import ru.doledenok.webtech.models.EmpRole;
import ru.doledenok.webtech.models.ProjectRole;

import java.util.List;

public interface EmpRoleDAO extends GenericDAO<EmpRole, Long> {

    List<EmpRole> getEmployeeRolesByProjectId(Long empId);

}
