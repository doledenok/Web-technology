package ru.doledenok.webtech.DAO;
/*
import lombok.Builder;
import lombok.Getter;
 */
import lombok.Builder;
import lombok.Getter;
import ru.doledenok.webtech.models.Policy;
import ru.doledenok.webtech.models.Project;

import java.util.List;

public interface PolicyDAO extends GenericDAO<Policy, Long> {

    List<Policy> getByFilter(PolicyDAO.Filter filter);

    @Builder
    @Getter
    class Filter {
        private String regularity;
        private String type;
        private String description;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
