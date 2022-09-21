package ru.doledenok.webtech.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.doledenok.webtech.DAO.EmployeeDAO;
import ru.doledenok.webtech.models.Employee;
import ru.doledenok.webtech.models.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAOImpl extends GenericDAOImpl<Employee, Long> implements EmployeeDAO {

    public EmployeeDAOImpl(){
        super(Employee.class);
    }

    @Override
    public List<Employee> getAllEmployeesByName(String employeeName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Employee> query = session.createQuery("from Employee where name like :gotName", Employee.class)
                    .setParameter("gotName", likeExpr(employeeName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Employee getSingleEmployeeByName(String employeeName) {
        List<Employee> candidates = this.getAllEmployeesByName(employeeName);
        return candidates == null ? null :
                candidates.size() == 1 ? candidates.get(0) : null;
    }

    @Override
    public List<Project> getProjectsByEmpId(Long empId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery("from Project where id in (select proj from ProjectRole where id in (select proj_role from EmpRole where emp.id = :gotEmpId))", Project.class)
                    .setParameter("gotEmpId", empId);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<String> getKnownEducation(){
        try (Session session = sessionFactory.openSession()) {
            Query<String> query = session.createQuery("select distinct education from Employee", String.class);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Employee> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getName() != null)
                predicates.add(builder.like(root.get("name"), likeExpr(filter.getName())));
            if (filter.getAddress() != null)
                predicates.add(builder.like(root.get("address"), likeExpr(filter.getAddress())));
            if (filter.getExperience() != null)
                predicates.add(builder.like(root.get("experience"), likeExpr(filter.getExperience())));
            if (filter.getEducation() != null)
                predicates.add(builder.like(root.get("education"), likeExpr(filter.getEducation())));
            if (filter.getStatus() != null)
                predicates.add(builder.like(root.get("status"), likeExpr(filter.getStatus())));

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
