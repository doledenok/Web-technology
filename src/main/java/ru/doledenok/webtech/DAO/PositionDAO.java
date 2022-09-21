package ru.doledenok.webtech.DAO;
/*
import lombok.Builder;
import lombok.Getter;
 */
import ru.doledenok.webtech.models.Position;

import java.util.List;

public interface PositionDAO extends GenericDAO<Position, Long> {
    Position getPositionByName(String positionName);

    List<String> getAllPositionNames();
}
