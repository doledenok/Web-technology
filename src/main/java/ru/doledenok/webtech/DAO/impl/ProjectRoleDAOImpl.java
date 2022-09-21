package ru.doledenok.webtech.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.ProjectRoleDAO;
import ru.doledenok.webtech.models.ProjectRole;

import java.util.List;

@Repository
public class ProjectRoleDAOImpl extends GenericDAOImpl<ProjectRole, Long> implements ProjectRoleDAO {

    public ProjectRoleDAOImpl(){
        super(ProjectRole.class);
    }

    @Override
    public List<ProjectRole> getProjectRolesByProjectId(Long projId) {
        //FIXME: Is it really works?
        try (Session session = sessionFactory.openSession()) {
            Query<ProjectRole> query = session.createQuery("from ProjectRole where proj.id = :gotProjId", ProjectRole.class)
                    .setParameter("gotProjId", projId);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}
