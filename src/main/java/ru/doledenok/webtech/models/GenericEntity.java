package ru.doledenok.webtech.models;

public interface GenericEntity<ID> {
    ID getId();
    void setId(ID id);
}