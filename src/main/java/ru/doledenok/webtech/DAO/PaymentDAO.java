package ru.doledenok.webtech.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.doledenok.webtech.models.Employee;
import ru.doledenok.webtech.models.Payment;

import java.util.List;

public interface PaymentDAO extends GenericDAO<Payment, Long> {

    List<Payment> getAllPaymentsByEmpId(Long empId);
}
