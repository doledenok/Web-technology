package ru.doledenok.webtech.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.EmpRoleDAO;
import ru.doledenok.webtech.DAO.ProjectRoleDAO;
import ru.doledenok.webtech.models.EmpRole;
import ru.doledenok.webtech.models.ProjectRole;

import java.util.List;

@Repository
public class EmpRoleDAOImpl extends GenericDAOImpl<EmpRole, Long> implements EmpRoleDAO {

    public EmpRoleDAOImpl(){
        super(EmpRole.class);
    }

    @Override
    public List<EmpRole> getEmployeeRolesByProjectId(Long empId) {
        //FIXME: Is it really works?
        try (Session session = sessionFactory.openSession()) {
            Query<EmpRole> query = session.createQuery("FROM EmpRole WHERE proj_id = :gotEmpId", EmpRole.class)
                    .setParameter("gotEmpId", empId);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}
