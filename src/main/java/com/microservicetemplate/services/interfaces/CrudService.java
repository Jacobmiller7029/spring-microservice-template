package com.microservicetemplate.services.interfaces;

import java.util.List;

public interface CrudService<T, U> {
    List<T> getListOfEntities(U queryParams);
    List<T> saveEntities(List<T> newEntity);
    T getEntityById(Long id);
    T updateEntity(T entityToUpdate);
    void deleteEntityById(Long id);
}
