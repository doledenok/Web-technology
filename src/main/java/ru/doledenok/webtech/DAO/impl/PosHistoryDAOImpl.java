package ru.doledenok.webtech.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.PosHistoryDAO;
import ru.doledenok.webtech.models.PosHistory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PosHistoryDAOImpl extends GenericDAOImpl<PosHistory, Long> implements PosHistoryDAO {

    public PosHistoryDAOImpl(){
        super(PosHistory.class);
    }

    @Override
    public List<PosHistory> getPosHistoryByEmployeeId(Long empId) {
        try (Session session = sessionFactory.openSession()) {
            Query<PosHistory> query = session.createQuery("from PosHistory where emp.id = :gotEmpId", PosHistory.class)
                    .setParameter("gotEmpId", empId);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}
