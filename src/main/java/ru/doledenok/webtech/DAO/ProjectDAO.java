package ru.doledenok.webtech.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.doledenok.webtech.models.Project;

import java.util.List;

public interface ProjectDAO extends GenericDAO<Project, Long> {

    List<Project> getByFilter(ProjectDAO.Filter filter);

    @Builder
    @Getter
    class Filter {
        private String name;
        private String startDate;
        private String endDate;
        private String description;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
