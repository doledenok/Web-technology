package ru.doledenok.webtech.DAO;

import ru.doledenok.webtech.models.GenericEntity;


import java.util.Collection;

public interface GenericDAO<T extends GenericEntity<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void deleteById(ID id);

    void update(T entity);
}
