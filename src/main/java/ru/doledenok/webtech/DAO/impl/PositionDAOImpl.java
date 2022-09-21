package ru.doledenok.webtech.DAO.impl;
/*
import org.hibernate.Session;
import org.hibernate.query.Query;
*/
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.PositionDAO;
import ru.doledenok.webtech.models.Employee;
import ru.doledenok.webtech.models.Position;

import java.util.List;

/*
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
*/
@Repository
public class PositionDAOImpl extends GenericDAOImpl<Position, Long> implements PositionDAO {
    public PositionDAOImpl(){
        super(Position.class);
    }

    @Override
    public Position getPositionByName(String positionName) {
        List<Position> candidates;
        try (Session session = sessionFactory.openSession()) {
            Query<Position> query = session.createQuery("from Position WHERE name LIKE :gotName", Position.class)
                    .setParameter("gotName", likeExpr(positionName));
            candidates = query.getResultList().size() == 0 ? null : query.getResultList();
        }
        return candidates == null ? null :
                candidates.size() == 1 ? candidates.get(0) : null;
    }

    @Override
    public  List<String> getAllPositionNames() {
        try (Session session = sessionFactory.openSession()) {
            Query<String> query = session.createQuery("select name from Position", String.class);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
