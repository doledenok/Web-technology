package ru.doledenok.webtech.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.ProjectDAO;
import ru.doledenok.webtech.models.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ProjectDAOImpl extends GenericDAOImpl<Project, Long> implements ProjectDAO {
    public ProjectDAOImpl(){
        super(Project.class);
    }

    @Override
    public List<Project> getByFilter(ProjectDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Project> criteriaQuery = builder.createQuery(Project.class);
            Root<Project> root = criteriaQuery.from(Project.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getName() != null)
                predicates.add(builder.like(root.get("name"), likeExpr(filter.getName())));
            // FIXME: поиск по датам должен работать скорее всего не так.
            if (filter.getStartDate() != null)
                predicates.add(builder.like(root.get("address"), likeExpr(filter.getStartDate())));
            if (filter.getEndDate() != null)
                predicates.add(builder.like(root.get("experience"), likeExpr(filter.getEndDate())));
            if (filter.getDescription() != null)
                predicates.add(builder.like(root.get("education"), likeExpr(filter.getDescription())));

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
