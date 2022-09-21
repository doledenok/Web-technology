package ru.doledenok.webtech.DAO.impl;
/*
import org.hibernate.Session;
import org.hibernate.query.Query;
*/
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.PaymentDAO;
import ru.doledenok.webtech.models.Payment;
import ru.doledenok.webtech.models.PosHistory;

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
public class PaymentDAOImpl extends GenericDAOImpl<Payment, Long> implements PaymentDAO {

    public PaymentDAOImpl(){
        super(Payment.class);
    }

    @Override
    public List<Payment> getAllPaymentsByEmpId(Long empId) {
        //FIXME: Is it really works?
        try (Session session = sessionFactory.openSession()) {
            Query<Payment> query = session.createQuery("FROM Payment WHERE emp_id = :gotEmpId", Payment.class)
                    .setParameter("gotEmpId", empId);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}
