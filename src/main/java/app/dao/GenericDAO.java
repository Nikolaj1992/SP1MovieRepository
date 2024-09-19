package app.dao;

import java.util.List;

public interface GenericDAO<T, D> {

    T create(T entity);

    int delete(D id);

    T find(D id);

    T update(T entity, D id);

    List<T> findAll();

}
