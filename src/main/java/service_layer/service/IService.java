package main.java.service_layer.service;

import main.java.data_access_layer.dto.DTO;

import java.util.List;

public interface IService<T extends DTO, K> {
    List<T> findAll();

    T findOneById(K id);

    T save(T entity);

    T update(T entity);

    Boolean removeById(K id);
}
