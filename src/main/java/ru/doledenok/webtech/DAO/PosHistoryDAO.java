package ru.doledenok.webtech.DAO;

import ru.doledenok.webtech.models.PosHistory;

import java.util.List;

public interface PosHistoryDAO extends GenericDAO<PosHistory, Long> {

    List<PosHistory> getPosHistoryByEmployeeId(Long empId);

}
