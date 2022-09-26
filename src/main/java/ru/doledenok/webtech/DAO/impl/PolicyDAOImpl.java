package ru.doledenok.webtech.DAO.impl;
/*
import org.hibernate.Session;
import org.hibernate.query.Query;
*/
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.PolicyDAO;
import ru.doledenok.webtech.models.Policy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PolicyDAOImpl extends GenericDAOImpl<Policy, Long> implements PolicyDAO {
    public PolicyDAOImpl(){
        super(Policy.class);
    }

    @Override
    public List<Policy> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Policy> criteriaQuery = builder.createQuery(Policy.class);
            Root<Policy> root = criteriaQuery.from(Policy.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getRegularity() != null)
                predicates.add(builder.like(root.get("regularity"), likeExpr(filter.getRegularity())));
            if (filter.getType() != null)
                predicates.add(builder.like(root.get("type"), likeExpr(filter.getType())));
            if (filter.getDescription() != null)
                predicates.add(builder.like(root.get("description"), likeExpr(filter.getDescription())));

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
